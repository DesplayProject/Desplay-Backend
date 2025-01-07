package com.deterior.global.exception

import javax.naming.AuthenticationException

class NoAuthorizationInTokenException(override val message: String?) : AuthenticationException() {
}