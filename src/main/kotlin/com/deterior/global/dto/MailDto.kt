package com.deterior.global.dto

import com.deterior.global.service.MailType
import javax.sound.midi.Receiver

data class MailDto(
    val receiverMail: String,
    val type: MailType
)