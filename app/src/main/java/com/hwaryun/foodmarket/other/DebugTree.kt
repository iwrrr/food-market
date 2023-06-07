package com.hwaryun.foodmarket.other

import timber.log.Timber

class DebugTree : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String {
        return "${element.fileName}:${element.lineNumber} on ${element.methodName}"
    }
}