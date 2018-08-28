package com.dongsh.zxinglibrary.decode;

import android.graphics.Bitmap;
import com.google.zxing.Result;

/**
 * Created by yzq on 2017/10/18.
 *
 * 解析图片的回调
 */

public interface DecodeImgCallback {
    public void onImageDecodeSuccess(Result result, Bitmap scanBitmap);

    public void onImageDecodeFailed();
}
