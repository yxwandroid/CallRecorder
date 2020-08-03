package com.aykuttasil.callrecorder

import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.aykuttasil.callrecorder.recorder.CallRecord
import com.orhanobut.logger.Logger
import java.io.File


class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var callRecord: CallRecord

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //callRecord = CallRecord.init(this);
//        callRecord = CallRecord.Builder(this)
//            .setLogEnable(true)
//            .setRecordFileName("CallRecorderTestFile")
//            .setRecordDirName("CallRecorderTest")
//            .setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
//            .setShowSeed(true)
//            .build()

        //callRecord.changeReceiver(new MyCallRecordReceiver(callRecord));

        //callRecord.enableSaveFile();

        /*
        callRecord = new CallRecord.Builder(this)
                .setRecordFileName("Record_" + new SimpleDateFormat("ddMMyyyyHHmmss", Locale.US).format(new Date()))
                .setRecordDirName("CallRecord")
                .setRecordDirPath(Environment.getExternalStorageDirectory().getPath())
                .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                .setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
                .setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
                .setShowSeed(true)
                .buildService();

        callRecord.startCallRecordService();
        */
//
//        callRecord = CallRecord.Builder(this)
//                .setRecordFileName("RecordFileName")
//                .setRecordDirName("RecordDirName")
//                .setRecordDirPath(Environment.getExternalStorageDirectory().getPath()) // optional & default value
//                .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // optional & default value
//                .setOutputFormat(MediaRecorder.OutputFormat.AMR_NB) // optional & default value
//                .setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION) // optional & default value
//                .setShowSeed(true) // optional & default value ->Ex: RecordFileName_incoming.amr || RecordFileName_outgoing.amr
//                .build()
//
//        callRecord.startCallRecordService()
//        callRecord.enableSaveFile();
//        callRecord.startCallReceiver()
        var amrPath = "/sdcard/audio"
        callRecord = CallRecord.Builder(this)
                .setLogEnable(true)
                .setRecordFileName("RecordFileName")
                .setRecordDirName("RecordDirName")
//                .setRecordDirPath(Environment.getDownloadCacheDirectory().path) // optional & default value
                .setRecordDirPath(amrPath) // optional & default value
                .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // optional & default value
                .setOutputFormat(MediaRecorder.OutputFormat.AMR_NB) // optional & default value
                .setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION) // optional & default value
                .setShowSeed(true) // optional & default value ->Ex: RecordFileName_incoming.amr || RecordFileName_outgoing.amr
                .build()


        callRecord.startCallReceiver()

    }

    fun getSDPath(): String? {
        var sdDir: File? = null
        val sdCardExist = (Environment.getExternalStorageState()
                == Environment.MEDIA_MOUNTED) //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory() //获取跟目录
        }
        return sdDir.toString()
    }



}
