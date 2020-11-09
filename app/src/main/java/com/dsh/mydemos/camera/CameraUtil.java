package com.dsh.mydemos.camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Adam on 2018/3/15.
 */

public class CameraUtil {

    private static   String storagePath = "";

    /**初始化保存路径
     * @return
     */
    public static String initPath(){
        String path;
        if(storagePath.equals("")){
            storagePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/MyDemos/";
            File f = new File(storagePath);
            if(!f.exists()){
                f.mkdir();
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS",
                Locale.getDefault());
        path = storagePath + format.format(new Date()) + "_mycamera"  + ".jpg";
        return path;
    }


    /**
     * 保存Bitmap到sdcard
     */
    public static void saveBitmap(Bitmap bm,String path,Context context) {
        try {
            //保存图片
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    Locale.getDefault());
            Bitmap targetImg;
            targetImg = createBitmap(bm, null,format.format(new Date()) + "  by Adam");


            FileOutputStream fout = new FileOutputStream(path);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            targetImg.compress(Bitmap.CompressFormat.JPEG, 99, bos);//嘿嘿 99的效果还是很好的
            bos.flush();
            bos.close();

            if (bm != null) {
                bm.recycle();
                System.gc();
            }
            if (targetImg != null) {
                targetImg.recycle();
                System.gc();
            }
            //刷新系统相册，刷新系统相册的操作实际上就是发送了系统内置的广播
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(new File(path));
            intent.setData(uri);
            context.sendBroadcast(intent);
            Log.i("KKKKKKKKKK", "saveBitmap成功");
        } catch (IOException e) {
            Log.i("KKKKKKKKKK", "saveBitmap:失败");
            e.printStackTrace();
        }
    }

    //watermark:src 原图片 watermark 水印图片 title 水印文字
    @SuppressLint("ResourceAsColor")
    public static Bitmap createBitmap(Bitmap src, Bitmap waterMak,String title) {
        if (src == null) {
            return src;
        }
        // 获取原始图片与水印图片的宽与高
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(newBitmap);
        // 往位图中开始画入src原始图片
        mCanvas.drawBitmap(src, 0, 0, null);
        //图片水印
        if (waterMak!=null) {
            int ww = waterMak.getWidth();
            int wh = waterMak.getHeight();
            // 在src的右下角添加水印
            Paint paint = new Paint();
            paint.setAlpha(100);
            mCanvas.drawBitmap(waterMak, w - ww - 20, h - wh - 20, paint);
        }
        // 文字水印
        if (null != title) {
            Paint textPaint = new Paint();
            textPaint.setColor(Color.parseColor("#F7F7F7"));
            int s;
            if (w>h) {
                s = h / 20;
            }else{
                s = w / 20;
            }
            textPaint.setTextSize(s);//设置字体大小为当前图片宽度的1/20,一行最多能放20个字
            textPaint.setAlpha(128);
            String familyName = "宋体";
            Typeface typeface = Typeface.create(familyName,
                    Typeface.NORMAL);
            textPaint.setTypeface(typeface);
            textPaint.setTextAlign(Paint.Align.RIGHT);//RIGHT时向右对齐，下面代码为w-2s
			mCanvas.drawText(title, w-s, h - s, textPaint);//水印边距 左，下

        }
        //mCanvas.save(Canvas.ALL_SAVE_FLAG);
        mCanvas.save();
        mCanvas.restore();
        return newBitmap;
    }


}
