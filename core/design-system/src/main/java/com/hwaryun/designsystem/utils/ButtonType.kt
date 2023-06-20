package com.hwaryun.designsystem.utils

sealed class ButtonType {
    object Primary : ButtonType()
    object Outline : ButtonType()
    object Text : ButtonType()
}