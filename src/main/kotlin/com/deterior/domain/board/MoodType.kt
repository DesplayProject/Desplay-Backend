package com.deterior.domain.board

enum class MoodType(
    val title: String,
) {
    OFFICE("사무용"),
    GAMING("게이밍"),
    NEAT("깔끔한"),
    FANCY("화려한"),
    CALM("차분한"),
    ;

    companion object {
        fun toEnum(title: String): MoodType? {
            return entries.find { it.title == title }
        }
    }
}