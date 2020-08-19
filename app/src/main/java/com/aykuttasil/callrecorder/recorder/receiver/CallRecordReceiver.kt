package com.aykuttasil.callrecorder.recorder.receiver

import android.content.Context
import android.media.MediaRecorder
import com.aykuttasil.callrecorder.recorder.CallRecord
import com.aykuttasil.callrecorder.recorder.RecordEntity
import com.orhanobut.logger.Logger
import java.io.File
import java.io.IOException
import java.util.Date
import kotlin.concurrent.thread

open class CallRecordReceiver(private var callRecord: CallRecord) : PhoneCallReceiver() {

    companion object {
        private val TAG = CallRecordReceiver::class.java.simpleName

        const val ACTION_IN = "android.intent.action.PHONE_STATE"
        const val ACTION_OUT = "android.intent.action.NEW_OUTGOING_CALL"
        const val EXTRA_PHONE_NUMBER = "android.intent.extra.PHONE_NUMBER"
        private var recorder: MediaRecorder? = null
    }

    private var audioFile: File? = null
    private var isRecordStarted = false
    private var recordEntity: RecordEntity? = null;


    override fun onIncomingCallReceived(context: Context, number: String?, start: Date) {
    }

    override fun onIncomingCallAnswered(context: Context, number: String?, start: Date) {
        startRecord(context, "incoming", number)
    }

    override fun onIncomingCallEnded(context: Context, number: String?, start: Date, end: Date) {
        stopRecord(context)
    }

    override fun onOutgoingCallStarted(context: Context, number: String?, start: Date) {
        startRecord(context, "outgoing", number)
    }

    override fun onOutgoingCallEnded(context: Context, number: String?, start: Date, end: Date) {
        stopRecord(context)
    }

    override fun onMissedCall(context: Context, number: String?, start: Date) {
    }

    // Derived classes could override these to respond to specific events of interest
    protected open fun onRecordingStarted(context: Context, callRecord: CallRecord, audioFile: File?) {}

    protected open fun onRecordingFinished(context: Context, callRecord: CallRecord, audioFile: File?) {}

    /**
     *seed  incoming
     */
    private fun startRecord(context: Context, seed: String, phoneNumber: String?) {
        try {

            if (isRecordStarted) {
                try {
                    recorder?.stop()  // stop the recording
                } catch (e: RuntimeException) {
                    // RuntimeException is thrown when stop() is called immediately after start().
                    // In this case the output file is not properly constructed ans should be deleted.
                    Logger.d("RuntimeException: stop() is called immediately after start()")
                    audioFile?.delete()
                }

                releaseMediaRecorder()
                isRecordStarted = false
            } else {
                if (prepareAudioRecorder(context, seed, phoneNumber)) {
                    recorder!!.start()
                    isRecordStarted = true
                    onRecordingStarted(context, callRecord, audioFile)
                    Logger.i("record start")
                    Logger.i("record audioFile %s", audioFile)
                } else {
                    releaseMediaRecorder()
                }
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            releaseMediaRecorder()
        } catch (e: RuntimeException) {
            e.printStackTrace()
            releaseMediaRecorder()
        } catch (e: Exception) {
            e.printStackTrace()
            releaseMediaRecorder()
        }
    }

    private fun stopRecord(context: Context) {
        try {
            if (recorder != null && isRecordStarted) {
                releaseMediaRecorder()
                isRecordStarted = false
                onRecordingFinished(context, callRecord, audioFile)

                var endRecordTime = System.currentTimeMillis()
                recordEntity?.endTime = endRecordTime;
                recordEntity?.createEndFileName()

                var outPutFile = recordEntity?.endFilePath;

                Logger.i("record stop 输出文件名称 $outPutFile")

                thread {
                    audioFile?.renameTo(File(outPutFile))
                }

                Logger.i("record stop 录制信息 ${recordEntity.toString()}")
            }
        } catch (e: Exception) {
            releaseMediaRecorder()
            e.printStackTrace()
            Logger.i("record stop e $e")
        }
    }

    private fun prepareAudioRecorder(
            context: Context, seed: String, phoneNumber: String?
    ): Boolean {
        try {
            var dirPath = "/sdcard/audio/RecordDirName/"
            val sampleDir = File(dirPath)

            if (!sampleDir.exists()) {
                sampleDir.mkdirs()
            }
            Logger.i("start record 存储路径:${dirPath}")
            val fileNameBuilder = StringBuilder()
            if (phoneNumber != null) {
                fileNameBuilder.append(phoneNumber)
                fileNameBuilder.append("_")
                fileNameBuilder.append("beginRecordTime_")
                if (seed.startsWith("incoming")) {
                    fileNameBuilder.append("0_")
                } else {
                    fileNameBuilder.append("1_")
                }

                fileNameBuilder.append("endRecordTime_")
                fileNameBuilder.append("callLogId")
            }else{
                fileNameBuilder.append("error")
            }

            val suffix = ".amr"

            fileNameBuilder.append(suffix)

            var fileName = fileNameBuilder.toString()
            Logger.i("start record 存储文件名:${fileName}")


            var filePath = "$dirPath$fileName"
            audioFile = File(filePath)


            var beginRecordTime = System.currentTimeMillis()
            recordEntity = RecordEntity();
            recordEntity?.phoneNumber = phoneNumber;
            recordEntity?.startTime = beginRecordTime;
            recordEntity?.seed = seed;
            recordEntity?.startFilePath = filePath

            Logger.i("start record 录音信息:${recordEntity.toString()}")

            recorder = MediaRecorder()
            recorder?.apply {
                setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
                setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(audioFile!!.absolutePath)
                setOnErrorListener { _, _, _ -> }
            }

            try {
                recorder?.prepare()
            } catch (e: IllegalStateException) {
                Logger.e(TAG, "IllegalStateException preparing MediaRecorder: " + e.message)
                releaseMediaRecorder()
                return false
            } catch (e: IOException) {
                Logger.e(TAG, "IOException preparing MediaRecorder: " + e.message)
                releaseMediaRecorder()
                return false
            }

            return true
        } catch (e: Exception) {
            Logger.e(e.toString())
            e.printStackTrace()
            return false
        }
    }

    private fun releaseMediaRecorder() {
        recorder?.apply {
            reset()
            release()
        }
        recorder = null
    }
}
