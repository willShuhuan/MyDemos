package com.dsh.mydemos.camera;

/**
 * Created by Adam on 2018/3/2.
 */

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.IOException;
import java.util.List;

/**
 * Created by Adam on 2016/10/5.
 */
public class SurfacePreview extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = SurfacePreview.class.getSimpleName();
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private Camera.Parameters mParameters;
    private Context mContext;
    private boolean isSuccess;

    public SurfacePreview(Context context) {
        super(context);

        mContext = context;
        mHolder = getHolder();
        mHolder.setFormat(PixelFormat.TRANSPARENT);
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated() is called");
        try {
            mCamera = Camera.open(0);
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        Log.d(TAG, "surfaceChanged() is called");
        try {
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {

                    isSuccess = success;
                    initCamera();
                    mCamera.cancelAutoFocus();
                    mCamera.startPreview();

                }
            });
        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }


    private void initCamera() {
        mParameters = mCamera.getParameters();
        mParameters.setPictureFormat(ImageFormat.JPEG);
        List<Camera.Size> pictureSizes = mParameters.getSupportedPictureSizes();
        Camera.Size picSize = null;
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();
        //相机支持的拍摄图片尺寸，拍照图片尺寸小于等于屏幕尺寸，实现适配
        if (pictureSizes.size() > 1) {
            for (Camera.Size size2 : pictureSizes) {
                System.out.println("initCamera:" + size2.width + size2.height);
                if (size2.width * size2.height <= screenWidth * screenHeight) {
                    if (picSize == null) {
                        picSize = size2;
                    } else {
                        if ((size2.width * size2.height) > (picSize.width * picSize.height)) {
                            picSize = size2;
                        }
                    }
                }
            }
        } else {
            picSize = pictureSizes.get(0);
        }
        mParameters.setPictureSize(picSize.width, picSize.height);
        //设置尺寸以适配所有机型，图片尺寸小于等于屏幕尺寸，部分机型崩溃的问题得到了解决
        mParameters.setJpegQuality(99); // 设置照片质量
        List<String> aa = mParameters.getSupportedFocusModes();
        //设置对焦模式，低端机型可能不支持快速对焦，当然现在大部分机型支持，但是也得适配啊
        if (aa.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);//快速对焦
        } else {
            mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }

        try {
            mCamera.setParameters(mParameters);
            mCamera.startPreview();
        } catch (Exception e) {

        }


    }


    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        Log.d(TAG, "surfaceDestroyed() is called");
    }

    //拍照
    public void takePicture(Camera.PictureCallback imageCallback) {
        Log.d(TAG, "takePicture: " + isSuccess);
        if (isSuccess) {
            mCamera.takePicture(null, null, imageCallback);
        } else {//如果不成功则重试对焦，否则拍出的照片可能糊
            initCamera();
            mCamera.cancelAutoFocus();
            mCamera.takePicture(null, null, imageCallback);

        }

    }




}
