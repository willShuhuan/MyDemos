package com.dsh.mydemos.myview;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by dongshuhuan
 * createDate 2018/11/23
 * Email 785434788@qq.com
 * Description
 * Update Log
 */

public interface  ICardInterface{
    /**
     * 初始化布局，direction为图片位置：1左，2顶部，3右
     * @param direction
     */
    void setViewsLayout(int direction);
    TextView getTitle();
    TextView getContents();
    TextView getRemark();
    ImageView getImageView();
    void setTitleVisible(boolean visible);
    void setContentsVisible(boolean visible);
    void setRemarkVisible(boolean visible);
    void setImageVisible(boolean visible);
    void setTitleText(String str);
    void setContentsText(String str);
    void setRemarkText(String str);
    void setImageView(Context context, String str);

}
