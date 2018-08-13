package com.yzq.zxinglibrary.decode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.google.zxing.LuminanceSource;
import com.google.zxing.PlanarYUVLuminanceSource;

/**
 * Created by dongshuhuan
 * createDate 2018/8/13
 * Email 785434788@qq.com
 * Description
 * Change Log
 */

public class MyPlanarYUVLuminanceSource extends LuminanceSource {

    private static final int THUMBNAIL_SCALE_FACTOR = 2;
    private final byte[] yuvData;
    private final int dataWidth;
    private final int dataHeight;
    private final int left;
    private final int top;

    public MyPlanarYUVLuminanceSource(byte[] yuvData, int dataWidth, int dataHeight, int left, int top, int width, int height, boolean reverseHorizontal) {
        super(width, height);
        if(left + width <= dataWidth && top + height <= dataHeight) {
            this.yuvData = yuvData;
            this.dataWidth = dataWidth;
            this.dataHeight = dataHeight;
            this.left = left;
            this.top = top;
            if(reverseHorizontal) {
                this.reverseHorizontal(width, height);
            }

        } else {
            throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
        }
    }

    @Override public byte[] getRow(int y, byte[] row) {
        if(y >= 0 && y < this.getHeight()) {
            int width = this.getWidth();
            if(row == null || row.length < width) {
                row = new byte[width];
            }

            int offset = (y + this.top) * this.dataWidth + this.left;
            System.arraycopy(this.yuvData, offset, row, 0, width);
            return row;
        } else {
            throw new IllegalArgumentException("Requested row is outside the image: " + y);
        }
    }

    @Override public byte[] getMatrix() {
        int width = this.getWidth();
        int height = this.getHeight();
        if(width == this.dataWidth && height == this.dataHeight) {
            return this.yuvData;
        } else {
            int area;
            byte[] matrix = new byte[area = width * height];
            int inputOffset = this.top * this.dataWidth + this.left;
            if(width == this.dataWidth) {
                System.arraycopy(this.yuvData, inputOffset, matrix, 0, area);
                return matrix;
            } else {
                for(int y = 0; y < height; ++y) {
                    int outputOffset = y * width;
                    System.arraycopy(this.yuvData, inputOffset, matrix, outputOffset, width);
                    inputOffset += this.dataWidth;
                }

                return matrix;
            }
        }
    }

    @Override public boolean isCropSupported() {
        return true;
    }

    @Override public LuminanceSource crop(int left, int top, int width, int height) {
        return new PlanarYUVLuminanceSource(this.yuvData, this.dataWidth, this.dataHeight, this.left + left, this.top + top, width, height, false);
    }

    public int[] renderThumbnail() {
        int width = this.getWidth() / 2;
        int height = this.getHeight() / 2;
        int[] pixels = new int[width * height];
        byte[] yuv = this.yuvData;
        int inputOffset = this.top * this.dataWidth + this.left;

        for(int y = 0; y < height; ++y) {
            int outputOffset = y * width;

            for(int x = 0; x < width; ++x) {
                int grey = yuv[inputOffset + (x << 1)] & 255;
                pixels[outputOffset + x] = -16777216 | grey * 65793;
            }

            inputOffset += this.dataWidth << 1;
        }

        return pixels;
    }

    public int getThumbnailWidth() {
        return this.getWidth() / 2;
    }

    public int getThumbnailHeight() {
        return this.getHeight() / 2;
    }

    private void reverseHorizontal(int width, int height) {
        byte[] yuvData = this.yuvData;
        int y = 0;

        for(int rowStart = this.top * this.dataWidth + this.left; y < height; rowStart += this.dataWidth) {
            int middle = rowStart + width / 2;
            int x1 = rowStart;

            for(int x2 = rowStart + width - 1; x1 < middle; --x2) {
                byte temp = yuvData[x1];
                yuvData[x1] = yuvData[x2];
                yuvData[x2] = temp;
                ++x1;
            }

            ++y;
        }

    }

    public Bitmap renderCroppedGreyscaleBitmap() {
        int width = getWidth();
        int height = getHeight();
        int[] pixels = new int[width * height];
        byte[] yuv = yuvData;
        int inputOffset = top * dataWidth + left;

        for (int y = 0; y < height; y++) {
            int outputOffset = y * width;
            for (int x = 0; x < width; x++) {
                int grey = yuv[inputOffset + x] & 0xff;
                pixels[outputOffset + x] = 0xFF000000 | (grey * 0x00010101);
            }
            inputOffset += dataWidth;
        }
        Log.d("render", "renderCroppedGreyscaleBitmap: "+width+height);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        return bitmap;


    }

}
