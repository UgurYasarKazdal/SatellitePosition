package com.uyk.satellite.util

import android.annotation.SuppressLint
import java.util.stream.Stream

//get dynamic line number for debugging
@SuppressLint("NewApi")
fun getLineNumber(): Int {
    return StackWalker.getInstance(StackWalker.Option.SHOW_HIDDEN_FRAMES)
        .walk { s: Stream<StackWalker.StackFrame> ->
            s.skip(
                1
            ).findFirst()
        }.get().lineNumber
}