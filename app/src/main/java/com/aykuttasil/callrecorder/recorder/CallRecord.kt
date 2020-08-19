package com.aykuttasil.callrecorder.recorder

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.aykuttasil.callrecorder.recorder.receiver.CallRecordReceiver
import com.orhanobut.logger.Logger

class CallRecord private constructor(private val mContext: Context) {
    private var mCallRecordReceiver: CallRecordReceiver? = null

    fun startCallReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(CallRecordReceiver.ACTION_IN)
        intentFilter.addAction(CallRecordReceiver.ACTION_OUT)

        if (mCallRecordReceiver == null) {
            mCallRecordReceiver = CallRecordReceiver(this)
        }
        mContext.registerReceiver(mCallRecordReceiver, intentFilter)
    }

    fun stopCallReceiver() {
        try {
            if (mCallRecordReceiver != null) {
                mContext.unregisterReceiver(mCallRecordReceiver)
            }
        } catch (e: Exception) {
            Logger.e(e.toString())
        }
    }


    class Builder(private val mContext: Context) {
        fun build(): CallRecord {
            return CallRecord(mContext)
        }

    }
    companion object {
        private val TAG = CallRecord::class.java.simpleName

    }
}
