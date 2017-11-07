package com.jobinlawrance.pics.application

import android.util.Log
import timber.log.Timber

/**
 * Created by jobinlawrance on 5/9/17.
 */
class ReleaseTree : Timber.Tree() {

    private val MAX_LOG_LENGTH = 4000

    override fun isLoggable(tag: String?, priority: Int): Boolean =
            !(priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO)

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (isLoggable(tag, priority)) {
            if (message.length < MAX_LOG_LENGTH) {
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, message)
                } else {
                    Log.println(priority, tag, message)
                }
                return
            }

            // Split by line, then ensure each line can fit into Log's maximum length.
            var i = 0
            val length = message.length
            while (i < length) {
                var newline = message.indexOf('\n', i)
                newline = if (newline != -1) newline else length
                do {
                    val end = Math.min(newline, i + MAX_LOG_LENGTH)
                    val part = message.substring(i, end)
                    if (priority == Log.ASSERT) {
                        Log.wtf(tag, part)
                    } else {
                        Log.println(priority, tag, part)
                    }
                    i = end
                } while (i < newline)
                i++
            }
        }
    }
}