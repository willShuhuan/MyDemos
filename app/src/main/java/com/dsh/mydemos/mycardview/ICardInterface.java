package com.dsh.mydemos.mycardview;

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
    /* 获得控件 */
    TextView getTitle();
    TextView getContents();
    TextView getRemark();
    ImageView getImageView();

    /* 可见/不可见状态 */
    void setTitleVisible(boolean visible);
    void setContentsVisible(boolean visible);
    void setRemarkVisible(boolean visible);
    void setImageVisible(boolean visible);

    /* 设置控件填充内容 */
    void setImageView(Context context,String str);
    void setTitleText(String str);
    void setContentsText(String str);
    void setRemarkText(String str);
    void setTitle(String title,int textSize,int color);
    void setContent(String content,int textSize,int color);
    void setRemark(String remark,int textSize,int color);

}
