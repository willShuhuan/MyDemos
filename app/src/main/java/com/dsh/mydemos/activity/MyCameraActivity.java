package com.dsh.mydemos.activity;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dsh.mydemos.R;
import com.dsh.mydemos.base.BaseActivity;
import com.dsh.mydemos.base.DBaseActivity;
import com.dsh.mydemos.camera.CameraUtil;
import com.dsh.mydemos.camera.SurfacePreview;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Adam on 2018/3/15.
 */

public class MyCameraActivity extends DBaseActivity implements  Camera.PictureCallback{

    @BindView(R.id.camera_preview)
    FrameLayout mCameraPreview;
    @BindView(R.id.capture)
    ImageView mCapture;
    @BindView(R.id.capture_state)
    TextView mCaptureState;
    @BindView(R.id.pb1)
    ProgressBar mPb1;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.text)
    RelativeLayout mText;
    private SurfacePreview mCameraSurPreview;
    private static final String TAG = MyCameraActivity.class.getSimpleName();
    private int curRotation = 0;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
        setContentView(R.layout.activity_mycamera);
        ButterKnife.bind(this);

        initView();
        setListener();
        initData();


    }


    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        //save the picture to sdcard
        //这段代码可以优化BitmapFactory.decodeByteArray()方法造成的oom异常
        InputStream input = null;
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //ARGB_8888最清晰耗内存，565失真一般内存，4444失真严重一般内存不推荐
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        input = new ByteArrayInputStream(data);
        SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(
                input, null, options));
        bitmap = (Bitmap) softRef.get();

        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(curRotation);
        final Bitmap rotaBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, false);
        final String PATH = CameraUtil.initPath();

        CameraUtil.saveBitmap(rotaBitmap,PATH,mContext);

        if (rotaBitmap != null || bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
        mPb1.setVisibility(View.GONE);
        mCaptureState.setVisibility(View.GONE);

        Toast.makeText(this,
                "拍照成功，照片已保存在" + PATH, Toast.LENGTH_SHORT)
                .show();

        camera.startPreview();
        mCapture.setEnabled(true);

    }

    @Override
    public void initView() {
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        mCameraSurPreview = new SurfacePreview(this);
        preview.addView(mCameraSurPreview);
        mContext = MyCameraActivity.this;
        screenDirectionListener();
    }

    @Override
    public void setListener() {
        mBack.setOnClickListener(this);
        mCapture.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.capture:
                mCaptureState.setVisibility(View.VISIBLE);
                mPb1.setVisibility(View.VISIBLE);
                mCapture.setEnabled(false);
                mCameraSurPreview.takePicture(this);
                break;

            case R.id.back:
                finish();
                break;

            default:
                break;
        }
    }

    /**
     * @Description: 监听屏幕的旋转方向，防止保存图片的时候图片旋转
     */
    private void screenDirectionListener() {
        OrientationEventListener mOrEventListener = new OrientationEventListener(mContext) {
            @Override
            public void onOrientationChanged(int rotation) {
                if (((rotation >= 0) && (rotation <= 45)) || (rotation > 315)) {//纵屏向上
                    rotation = 90;
                } else if ((rotation > 45) && (rotation <= 135)) {//横屏向右
                    rotation = 180;
                } else if ((rotation > 135) && (rotation <= 225)) {//纵屏向下
                    rotation = 270;
                } else if ((rotation > 225) && (rotation <= 315)) { //横屏向左
                    rotation = 0;
                } else {
                    rotation = 0;
                }
                curRotation = rotation;
            }
        };
        mOrEventListener.enable();
        Log.d(TAG, "screenDirectionListener: " + curRotation);
    }

}
