package com.deterior.global.util

import org.slf4j.LoggerFactory

abstract class LoggerCreator {
    val log = LoggerFactory.getLogger(javaClass)
}