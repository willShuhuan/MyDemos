package com.dsh.mydemos.EmptyLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dsh.mydemos.R;

/**
 * Created by dongshuhuan
 * createDate 2018/12/21
 * Email 785434788@qq.com
 * Description 默认展示空数据图片和一行文字，默认类型为空数据类型
 * Update Log
 */

public class CommonDataEmptyView extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "CommonDataEmptyView";
    ImageView imgErrorIcon;
    TextView tvErrorText;
    Button btnRetry;
    LinearLayout llEmpty;

    public static final int TYPE_NETWORK_ERROR = 1;//网络错误
    public static final int TYPE_DATA_EMPTY = 2;//空数据 默认类型
    public static final int TYPE_DATA_EMPTY_WITH_TIPSIMG = 3;//空数据并有指示性图片

    private Context mContext;
    onViewsClickListener mListener;

    public CommonDataEmptyView(Context context) {
        this(context,null);
    }

    public CommonDataEmptyView(Context context,AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.common_empty_layout, this, false);
        imgErrorIcon =  (ImageView) view.findViewById(R.id.img_error_icon);
        tvErrorText = (TextView) view.findViewById(R.id.tv_error_text);
        btnRetry = (Button) view.findViewById(R.id.btn_retry);
        llEmpty = (LinearLayout) view.findViewById(R.id.ll_empty);
        btnRetry.setOnClickListener(this);
        imgErrorIcon.setOnClickListener(this);

        addView(view);
    }

    @Override public void onClick(View view) {
        if (mListener == null){
            Log.d(TAG, "请注册点击事件setClickViewsListener()");
            return;
        }
        switch(view.getId()){
            case R.id.btn_retry:
                mListener.onRetryBtnClick();
                break;
            case R.id.tv_error_text:
                mListener.onErrorTextClick();
                break;
            case R.id.img_error_icon:
                mListener.onErrorImgClick();
                break;
             default:
                break;
        }

    }

    public void setType(int type){
        switch(type){
            case TYPE_NETWORK_ERROR:
                tvErrorText.setVisibility(GONE);
                btnRetry.setVisibility(VISIBLE);
                break;
            case TYPE_DATA_EMPTY_WITH_TIPSIMG:
                tvErrorText.setVisibility(GONE);
                break;
            case TYPE_DATA_EMPTY:
                //默认类型，do Nothing
                btnRetry.setVisibility(GONE);
                break;
             default:
                break;

        }
    }

    /*自定义设置 start -->*/
    public void setErrorImg(int drawble){
        imgErrorIcon.setImageResource(drawble);
    }

    public void setErrorImgVisible(boolean b){
        imgErrorIcon.setVisibility(b?VISIBLE:GONE);
    }


    public void setErrorText( String errorText){
        tvErrorText.setText(errorText);
    }

    public void setErrorTextColor(int resourceId) {
        tvErrorText.setTextColor(mContext.getResources().getColor(resourceId));
    }

    public void setErrorTextVisible(boolean b){
        tvErrorText.setVisibility(b?VISIBLE:GONE);
    }

    public void setBtnText(String text){
        btnRetry.setText(text);
    }

    public void setBtnVisible(boolean b){
        btnRetry.setVisibility(b?VISIBLE:GONE);
    }
    /*自定义设置 end <--*/


    /*如果errorLayout为顶部对齐有边距*/
    public void setMarginTop(int dp){
        llEmpty.setGravity(Gravity.CENTER_HORIZONTAL);
        LayoutParams params = (LayoutParams) llEmpty.getLayoutParams();
        params.topMargin = dip2px(dp);
        llEmpty.setLayoutParams(params);
    }

    /*如果errorLayout为底部对齐有边距*/
    public void setMarginBottom(int dp){
        llEmpty.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
        LayoutParams params = (LayoutParams) llEmpty.getLayoutParams();
        params.bottomMargin = dip2px(dp);
        llEmpty.setLayoutParams(params);
    }

    public void setCenter(){
        llEmpty.setGravity(Gravity.CENTER);

    }


    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /*点击监听*/
    public void setClickViewsListener(CommonDataEmptyView.onViewsClickListener listener) {
        mListener = listener;
    }

    /*提供三个view的点击事件回调*/
    public interface onViewsClickListener{
        void onRetryBtnClick();
        void onErrorTextClick();
        void onErrorImgClick();
    }




}
