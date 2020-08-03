package com.aykuttasil.callrecorder

import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.aykuttasil.callrecorder.recorder.CallRecord
import com.orhanobut.logger.Logger


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

        callRecord = CallRecord.Builder(this)
                .setLogEnable(true)
                .setRecordFileName("RecordFileName")
                .setRecordDirName("RecordDirName")
                .setRecordDirPath(Environment.getExternalStorageDirectory().path) // optional & default value
                .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // optional & default value
                .setOutputFormat(MediaRecorder.OutputFormat.AMR_NB) // optional & default value
                .setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION) // optional & default value
                .setShowSeed(true) // optional & default value ->Ex: RecordFileName_incoming.amr || RecordFileName_outgoing.amr
                .build()


        callRecord.startCallReceiver()
        callRecord.enableSaveFile();

    }

    fun StartCallRecordClick(view: View) {
        Logger.d(TAG, "StartCallRecordClick")

        callRecord.startCallReceiver()
        //callRecord.enableSaveFile();
        //callRecord.changeRecordDirName("NewDirName");
    }

    fun StopCallRecordClick(view: View) {
        Logger.i(TAG, "StopCallRecordClick")
        callRecord.stopCallReceiver()

        //callRecord.disableSaveFile();
        //callRecord.changeRecordFileName("NewFileName");
    }
}
