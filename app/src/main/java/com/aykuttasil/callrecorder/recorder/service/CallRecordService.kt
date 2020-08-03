package com.aykuttasil.callrecorder.recorder.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.annotation.Nullable
import com.aykuttasil.callrecorder.recorder.CallRecord
import com.aykuttasil.callrecorder.recorder.helper.PrefsHelper
import com.orhanobut.logger.Logger

open class CallRecordService : Service() {

    private lateinit var mCallRecord: CallRecord

    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Logger.i(TAG, "onCreate()")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Logger.i(TAG, "onStartCommand()")

        val fileName = PrefsHelper.readPrefString(this, CallRecord.PREF_FILE_NAME)
        val dirPath = PrefsHelper.readPrefString(this, CallRecord.PREF_DIR_PATH)
        val dirName = PrefsHelper.readPrefString(this, CallRecord.PREF_DIR_NAME)
        val showSeed = PrefsHelper.readPrefBool(this, CallRecord.PREF_SHOW_SEED)
        val showPhoneNumber = PrefsHelper.readPrefBool(this, CallRecord.PREF_SHOW_PHONE_NUMBER)
        val outputFormat = PrefsHelper.readPrefInt(this, CallRecord.PREF_OUTPUT_FORMAT)
        val audioSource = PrefsHelper.readPrefInt(this, CallRecord.PREF_AUDIO_SOURCE)
        val audioEncoder = PrefsHelper.readPrefInt(this, CallRecord.PREF_AUDIO_ENCODER)

        mCallRecord = CallRecord.Builder(this).setRecordFileName(fileName).setRecordDirName(dirName)
            .setRecordDirPath(dirPath).setAudioEncoder(audioEncoder).setAudioSource(audioSource)
            .setOutputFormat(outputFormat).setShowSeed(showSeed).setShowPhoneNumber(showPhoneNumber)
            .build()

        Logger.i(TAG, "mCallRecord.startCallReceiver()")
        mCallRecord.startCallReceiver()

        return Service.START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()
        mCallRecord.stopCallReceiver()
        Logger.i(TAG, "onDestroy()")
    }

    companion object {
        private val TAG = CallRecordService::class.java.simpleName
    }
}
