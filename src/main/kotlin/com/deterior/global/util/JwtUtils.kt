package com.deterior.global.util

import com.deterior.logger
import com.deterior.global.dto.JwtToken
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import org.springframework.web.util.ContentCachingRequestWrapper
import java.security.Key
import java.time.Instant
import java.util.*

@Component
class JwtUtils @Autowired constructor(
    private val applicationProperties: ApplicationProperties,
) {
    lateinit var key: Key
    val accessExpirationTime = applicationProperties.jwt.token.accessExpirationTime
    val refreshExpirationTime = applicationProperties.jwt.token.refreshExpirationTime
    val reissueTime = applicationProperties.jwt.token.reissueTime

    val log = logger()

    init {
        val secretKey: String = applicationProperties.jwt.secret
        val keyByte: ByteArray = Decoders.BASE64.decode(secretKey)
        key = Keys.hmacShaKeyFor(keyByte)
    }

    fun generateToken(authentication: Authentication): JwtToken {
        //jwt 쿠키에 저장되는 노출도가 높은 정보이므로 payload에는 민감한 개인정보대신 권한정보를 넣는다.
        val accessToken = createAccessToken(authentication.name, authentication.authorities)
        val refreshToken = createRefreshToken(authentication.name)
        return JwtToken(
            grantType = "Bearer",
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken: String = request.getHeader("Authorization")
        if(bearerToken.contains(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7)
        }
        return null
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)                  //파싱 및 검증
            return true
        } catch (exception: SignatureException) {
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

    fun createAccessToken(username: String, authorities: Collection<out GrantedAuthority>): String {
        val now: Long = Date.from(Instant.now()).time
        val expireTime = Date(now + accessExpirationTime)
        return Jwts.builder()
            .setSubject(username)
            .claim("auth", authorities)
            .setExpiration(expireTime)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun createRefreshToken(username: String): String {
        val now: Long = Date.from(Instant.now()).time
        val expireTime = Date(now + refreshExpirationTime)
        return Jwts.builder()
            .setSubject(username)
            .setExpiration(expireTime)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun parseClaims(token: String): Claims =
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (exception: ExpiredJwtException) {
            exception.claims
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
}