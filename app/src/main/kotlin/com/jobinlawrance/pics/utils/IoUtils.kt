package com.jobinlawrance.pics.utils

import android.support.annotation.NonNull
import android.support.annotation.Nullable
import okio.Okio
import java.io.IOException
import java.io.InputStream


/**
 * Created by jobinlawrance on 29/10/17.
 */

/**
 * Reads InputStream and returns a String. It will close stream after usage.
 *
 * @param stream the stream to read
 * @return the string with file content or null
 */
@Nullable
fun inputStreamToString(@NonNull stream: InputStream): String? {
    try {
        Okio.buffer(Okio.source(stream)).use({ source -> return source.readUtf8() })
    } catch (exception: IOException) {
        //ignored exception
        return null
    }

}