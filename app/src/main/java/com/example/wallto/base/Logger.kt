/*
 * Created by Mark Abramenko on 10/30/19 8:57 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 10/30/19 8:57 PM
 */

package com.example.wallto.base

import android.util.Log
// пример логгера который можно дергать из любой части кода, даже из домена, который не связан с
// андроид фреймворком
object Logger {
    private var writer: LogWriter = LogWriterConsole()

    fun setWriter(writer: LogWriter) {
        this.writer = writer
    }
}

interface LogWriter {
    fun log(sender: Any, message: String)
}


class LogWriterConsole : LogWriter {
    override fun log(sender: Any, message: String) {
        println(sender.toString() + message)
    }
}

class LogWriterLog : LogWriter {
    override fun log(sender: Any, message: String) {
        Log.d(sender.toString(), message)
    }
}

class LogWriterProduction : LogWriter {
    override fun log(sender: Any, message: String) {
         // TODO добавить крашлитикс. 30 Окт 2019
    }
}
