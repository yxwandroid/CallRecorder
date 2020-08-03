package com.aykuttasil.callrecorder.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

@SuppressLint("StaticFieldLeak")
object ToastUtil {

    private lateinit var handler: Handler
    private lateinit var context: Context

    @JvmStatic
    fun init(handler: Handler, context: Context) {
        this.handler = handler
        this.context = context
    }

    private fun show(res: Int, msg: String) {
        val m = context.resources.getString(res, msg)
        val t = Toast.makeText(context, m, Toast.LENGTH_SHORT)
        t.show()
    }

    private fun show(msg: String) {
        val t = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        t.show()
    }

    @JvmStatic
    fun showInfo(res: Int, msg: String) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            show(res, msg)
        } else {
            handler.post { show(res, msg) }
        }
    }

    @JvmStatic
    fun showInfo(msg: String) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            show(msg)
        } else {
            handler.post { show(msg) }
        }
    }
}