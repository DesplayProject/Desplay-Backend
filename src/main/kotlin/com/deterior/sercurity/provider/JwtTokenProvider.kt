package com.deterior.sercurity.provider

import com.deterior.global.util.ApplicationProperties
import com.deterior.global.util.LoggerCreator
import com.deterior.sercurity.dto.JwtToken
import com.deterior.sercurity.exception.NoAuthorizationInTokenException
import com.deterior.sercurity.service.JwtUserDetailsService
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.security.Key
import java.time.Instant
import java.util.*

@Slf4j
@Component
class JwtTokenProvider @Autowired constructor(
    private val applicationProperties: ApplicationProperties,
    private val jwtUserDetailsService: JwtUserDetailsService
) {
    lateinit var key: Key
    val accessExpirationTime = applicationProperties.jwt.token.accessExpirationTime
    val refreshExpirationTime = applicationProperties.jwt.token.refreshExpirationTime
    val reissueTime = applicationProperties.jwt.token.reissueTime

    init {
        val secretKey: String = applicationProperties.jwt.secret
        val keyByte: ByteArray = Decoders.BASE64.decode(secretKey)
        key = Keys.hmacShaKeyFor(keyByte)
    }

    companion object : LoggerCreator()

    fun generateToken(authentication: Authentication): JwtToken {
        //jwt 쿠키에 저장되는 노출도가 높은 정보이므로 payload에는 민감한 개인정보대신 권한정보를 넣는다.
        val accessToken = createAccessToken(authentication)
        val refreshToken = createRefreshToken()
        return JwtToken(
            grantType = "Bearer",
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    private fun createAccessToken(authentication: Authentication): String {
        val authorities = authentication.authorities
            .map { it.toString() }
            .reduce{ result, new -> "$result,$new" }
        val now: Long = Date.from(Instant.now()).time
        val expireTime = Date(now + accessExpirationTime)
        return Jwts.builder()
            .setSubject(authentication.name)
            .claim("auth", authorities)
            .setExpiration(expireTime)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    private fun createRefreshToken(): String {
        val now: Long = Date.from(Instant.now()).time
        val expireTime = Date(now + refreshExpirationTime)
        return Jwts.builder()
            .setExpiration(expireTime)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun authenticate(accessToken: String): Authentication {
        val claims = parseClaims(accessToken)
        if(claims["auth"] == null) {
            throw NoAuthorizationInTokenException("권한 정보가 없는 토큰입니다.")
        }
        val authorities = claims["auth"]
            .toString().split(",")
            .map { SimpleGrantedAuthority(it) }
            .toList()
        val user = jwtUserDetailsService.loadUserByUsername(claims.subject)
        return UsernamePasswordAuthenticationToken(user, "", authorities)
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)                  //파싱 및 검증
            return true
        } catch (exception: SecurityException) {
            log.warn("Invalid JWT Token", exception)
        } catch (exception: ExpiredJwtException) {
            log.warn("Expired JWT Token", exception)
        } catch (exception: UnsupportedJwtException) {
            log.warn("Unsupported JWT Token", exception)
        } catch (exception: IllegalArgumentException) {
            log.warn("Empty JWT Token", exception)
        }
        return false
    }

    fun isReissueRefreshToken(token: String): Boolean {
        val jws = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
        val now: Long = Date.from(Instant.now()).time
        val expireTime = jws.body.expiration.time
        val refreshTime = Date(now + refreshExpirationTime).time
        if (refreshTime - expireTime > reissueTime) {
            return true
        }
        return false
    }

    private fun parseClaims(accessToken: String): Claims =
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .body
        } catch (exception: ExpiredJwtException) {
            exception.claims
        }
}