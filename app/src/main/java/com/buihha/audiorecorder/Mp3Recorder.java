package com.buihha.audiorecorder;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class Mp3Recorder {

    private static final String TAG = Mp3Recorder.class.getSimpleName();
    //根据资料假定的最大值。 实测时有时超过此值。
    private static final int MAX_VOLUME = 2000;
    private static final int DEFAULT_SAMPLING_RATE = 8000;//兼容性较好
    private static final int FRAME_COUNT = 160;
    /* Encoded bit rate. MP3 file will be encoded with bit rate 32kbps */
    private static final int BIT_RATE = 32;

    static {
        System.loadLibrary("mp3lame");
    }

    private int mVolume;
    private AudioRecord audioRecord = null;

    private int bufferSize;

    private File mp3File;

    private RingBuffer ringBuffer;

    private byte[] buffer;

    private FileOutputStream os = null;

    private DataEncodeThread encodeThread;

    private int samplingRate;

    private int channelConfig;

    private PCMFormat audioFormat;

    private boolean isRecording = false;
    private String filePath;
    private long startTime;
    private long stopTime;
    private long length;
    private RecorderListener listener;

    public Mp3Recorder(int samplingRate, int channelConfig,
            PCMFormat audioFormat, String filePath) {
        this.samplingRate = samplingRate;
        this.channelConfig = channelConfig;
        this.audioFormat = audioFormat;
        this.filePath = filePath;
    }

    /**
     * Default constructor. Setup recorder with default sampling rate 1 channel,
     * 16 bits pcm
     */
    public Mp3Recorder(String filePath) {
        this(DEFAULT_SAMPLING_RATE, AudioFormat.CHANNEL_IN_MONO,
                PCMFormat.PCM_16BIT, filePath);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Start recording. Create an encoding thread. Start record from this
     * thread.
     */
    public void startRecording() throws IOException {
        if (isRecording) return;
        Log.d(TAG, "Start recording");
        Log.d(TAG, "BufferSize = " + bufferSize);
        // Initialize audioRecord if it's null.
        if (audioRecord == null) {
            length = 0;
            stopTime = 0;
            startTime = 0;
            initAudioRecorder();
        }
        audioRecord.startRecording();
        startTime = new Date().getTime();
        //开始录制
        new Thread() {

            @Override
            public void run() {
                isRecording = true;
                while (isRecording) {
                    int bytes = audioRecord.read(buffer, 0,
                            bufferSize);//读取音频数据至buffer中(读取缓冲区(在过度运行尚未读取读取的数据)的数据至buffer)
                    if (bytes > 0) {
                        ringBuffer.write(buffer, bytes);//写入RingBuffer数据中
                        calculateRealVolume(buffer, bytes);
                    }
                }
            }
        }.start();
    }

    /**
     *
     * @throws IOException
     */
    public void stopRecording() throws IOException {
        Log.d(TAG, "stop recording");
        isRecording = false;
        calaTime();
        // release and finalize audioRecord
        try {
            //停止录制
            if (audioRecord != null) {
                audioRecord.stop();
                audioRecord.release();
                audioRecord = null;
            }
            // stop the encoding thread and try to wait
            // until the thread finishes its job(发送消息结束工作录制)
            Message msg = Message.obtain(encodeThread.getHandler(),
                    DataEncodeThread.PROCESS_STOP);
            msg.sendToTarget();

            Log.d(TAG, "waiting for encoding thread");
            encodeThread.join();//执行encodeThread线程


            if (listener != null) {
                listener.stop();
            }
            Log.d(TAG, "done encoding thread");
        } catch (InterruptedException e) {
            Log.d(TAG, "Faile to join encode thread");
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //计算音量大小
    private void calculateRealVolume(byte[] buffer, int readSize) {
        double sum = 0;
        for (int i = 0; i < readSize; i++) {
            sum += buffer[i] * buffer[i];
        }
        if (readSize > 0) {
            double amplitude = sum / readSize;
            mVolume = (int) Math.sqrt(amplitude);
        }
        if (mVolume >= MAX_VOLUME) {
            listener.Volume(MAX_VOLUME);
        }else {
            listener.Volume(mVolume);
        }
    }

    public int getVolume() {
        if (mVolume >= MAX_VOLUME) {
            return MAX_VOLUME;
        }
        return mVolume;
    }

    /**
     * Initialize audio recorder(初始化Audio Recorder)
     */
    private void initAudioRecorder() throws IOException {
        int bytesPerFrame = audioFormat.getBytesPerFrame();
        /* Get number of samples. Calculate the buffer size (round up to the
		   factor of given frame size) */
        int frameSize = AudioRecord.getMinBufferSize(samplingRate,
                channelConfig, audioFormat.getAudioFormat())
                / bytesPerFrame;  //返回字节为单位的创建AudioRecord的最小缓冲区大小
        if (frameSize % FRAME_COUNT != 0) {
            frameSize = frameSize + (FRAME_COUNT - frameSize % FRAME_COUNT);  //设置frameSize为160的整数倍
            Log.d(TAG, "Frame size: " + frameSize);
        }

        bufferSize = frameSize * bytesPerFrame;  //录制过程中写入的缓冲区的总大小

		/* Setup audio recorder */
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                samplingRate, channelConfig, audioFormat.getAudioFormat(),
                bufferSize); //初始化AudioRecord

        // Setup RingBuffer. Currently is 10 times size of hardware buffer(设置buffer为缓冲区的10倍大小)
        // Initialize buffer to hold data(初始化buffer接受数据)
        ringBuffer = new RingBuffer(10 * bufferSize);
        buffer = new byte[bufferSize];

        // Initialize lame buffer
        // mp3 sampling rate is the same as the recorded pcm sampling rate
        // The bit rate is 32kbps(初始化lame samplingRate采样率,1为声道数,BIT_RATE为比特率)
        SimpleLame.init(samplingRate, 1, samplingRate, BIT_RATE);

        mp3File = new File(filePath);
        if (!mp3File.getParentFile().exists()) {
            mp3File.mkdirs();
        }
        os = new FileOutputStream(mp3File);

        // Create and run thread used to encode data
        // The thread will(创建一个线程编码数据)
        encodeThread = new DataEncodeThread(ringBuffer, os, bufferSize);
        encodeThread.start();

        //设置audioRecord的监听并且设置监听
        audioRecord.setRecordPositionUpdateListener(encodeThread, encodeThread.getHandler());
        audioRecord.setPositionNotificationPeriod(FRAME_COUNT);
    }

    /**
     * pause recording
     */
    public void pasueRecording() {
        Log.d(TAG, "Pasue recording");
        if (audioRecord != null) {
            audioRecord.stop();
        }
        isRecording = false;
        calaTime();
    }

    private void calaTime() {
        stopTime = new Date().getTime();
        length += stopTime - startTime;
    }

    public RecorderListener getListener() {
        return listener;
    }

    public void setListener(RecorderListener listener) {
        this.listener = listener;
    }

    public boolean isRecording() {
        return isRecording;
    }

    public long getLength() {
        return length;
    }

    public interface RecorderListener {
        public void stop();

        public void Volume(int volume);

    }
}