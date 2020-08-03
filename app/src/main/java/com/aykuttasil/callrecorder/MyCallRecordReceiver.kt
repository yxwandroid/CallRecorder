package com.aykuttasil.callrecorder

import android.content.Context
import com.aykuttasil.callrecorder.recorder.CallRecord
import com.aykuttasil.callrecorder.recorder.receiver.CallRecordReceiver
import java.io.File


import java.util.Date

class MyCallRecordReceiver(callRecord: CallRecord) : CallRecordReceiver(callRecord) {


    override fun onIncomingCallReceived(context: Context, number: String?, start: Date) {
        super.onIncomingCallReceived(context, number, start)
    }


    override fun onRecordingStarted(context: Context, callRecord: CallRecord, audioFile: File?) {
        super.onRecordingStarted(context, callRecord, audioFile)
    }
    override fun onRecordingFinished(context: Context, callRecord: CallRecord, audioFile: File?) {
        super.onRecordingFinished(context, callRecord, audioFile)
    }
}
