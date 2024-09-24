package com.deterior.sercurity.exception

import javax.naming.AuthenticationException

class NoAuthorizationInTokenException(override val message: String?) : AuthenticationException() {
}