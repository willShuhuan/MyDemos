package com.dsh.mydemos.mycardview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dsh.mydemos.R;

/**
 * Created by dongshuhuan
 * createDate 2018/11/23
 * Email 785434788@qq.com
 * Description: PictureTextCardView 基于cardview封装的通用新闻标签布局
 * 包含四个部分：图片，标题，描述文字，补充文字
 * 图片可以在布局的左 上 右三个位置顶边展示
 * xml中须指定app:img_width="xxdp"和app:img_height="xxdp"，或者通过getImageView获取ImageView设置图片宽高，否则默认100dp宽度和150dp高度（图片top时宽度match_parent）
 * xml中，text_margin控制图片的外边距，cardCornerRadius控制圆角大小,img_gravity(left,right,top)控制图片位置
 */

public class PictureTextCardView extends CardView implements ICardInterface{

    public static int POSITION_LEFT = 1;
    public static int POSITION_TOP = 2;
    public static int POSITION_RIGHT = 3;

    private CardView mCardView;
    private TextView mTvTitle;
    private TextView mTvContents;
    private TextView mTvRemark;
    private ImageView mImageView;

    private float radius;
    private float imgWidth;
    private float imgHeight;
    private float textMargin;
    private int titleColor;
    private int contentColor;
    private int remarkColor;
    private int imgGravity;

    public PictureTextCardView(Context context) {
        this(context, null);
    }

    public PictureTextCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PictureTextCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PictureTextCardView);
            radius = ta.getDimension(R.styleable.PictureTextCardView_cardCornerRadius, dip2px(5));
            imgHeight = ta.getDimension(R.styleable.PictureTextCardView_img_height, dip2px(150));
            imgWidth = ta.getDimension(R.styleable.PictureTextCardView_img_width, dip2px(100));
            textMargin = ta.getDimension(R.styleable.PictureTextCardView_text_marign, dip2px(10));
            titleColor = ta.getColor(R.styleable.PictureTextCardView_title_color, Color.parseColor("#000000"));
            contentColor = ta.getColor(R.styleable.PictureTextCardView_content_color,Color.parseColor("#666666"));
            remarkColor = ta.getColor(R.styleable.PictureTextCardView_remark_color,Color.parseColor("#999999"));
            imgGravity = ta.getInt(R.styleable.PictureTextCardView_img_gravity,0);
            ta.recycle();
        }

        init(context);

    }

    private void init(Context context) {
        inflate(context,R.layout.dsh_picturetext_cardview,this);
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        mCardView = (CardView) findViewById(R.id.cardview);
        mTvTitle = (TextView) findViewById(R.id.title);
        mTvContents = (TextView) findViewById(R.id.contents);
        mTvRemark = (TextView) findViewById(R.id.remark);
        mImageView = (ImageView) findViewById(R.id.imgview);
        mCardView.setRadius(radius);
        setViewsLayout(getImgPosition());

    }



    public void setViewsLayout(int direction) {
        //去除5.0以下cardView内间距
        if (Build.VERSION.SDK_INT < 21) {
            mCardView.setUseCompatPadding(false);
            mCardView.setPreventCornerOverlap(false);
        }
        FrameLayout.LayoutParams lpImage = (FrameLayout.LayoutParams) mImageView.getLayoutParams();
        FrameLayout.LayoutParams lpTitle = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        FrameLayout.LayoutParams lpContents = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        FrameLayout.LayoutParams lpRemark = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        if (direction==POSITION_LEFT){
            int left = (int)imgWidth+(int)textMargin;
            lpImage.height= (int)imgHeight;
            lpImage.width = (int)imgWidth;
            lpImage.gravity = Gravity.LEFT;
            lpTitle.setMargins(left,(int)textMargin,(int)textMargin,0);
            mTvTitle.setTextColor(titleColor);
            mTvTitle.setLayoutParams(lpTitle);
            lpContents.gravity = Gravity.CENTER_VERTICAL;
            lpContents.setMargins(left,0,(int)textMargin,0);
            mTvContents.setTextColor(contentColor);
            mTvContents.setLayoutParams(lpContents);
            lpRemark.gravity = Gravity.BOTTOM;
            lpRemark.setMargins(left,0,(int)textMargin,(int)textMargin);
            mTvRemark.setTextColor(remarkColor);
            mTvRemark.setLayoutParams(lpRemark);
        }else if (direction==POSITION_TOP){//如果图片为顶部展示，默认只显示两行文本，title和contents，并且title为singleLine单行展示
            lpImage.height= (int)imgHeight;
            lpImage.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lpImage.gravity = Gravity.TOP;
            int top = (int)imgHeight+(int)textMargin;
            lpTitle.setMargins((int)textMargin,top,0,0);
            mTvTitle.setSingleLine(true);
            mTvTitle.setTextColor(titleColor);
            mTvTitle.setLayoutParams(lpTitle);
            lpContents.setMargins((int)textMargin,top+dip2px(18)+(int)textMargin,0,(int)textMargin);
            mTvContents.setTextColor(contentColor);
            mTvContents.setLayoutParams(lpContents);
            //lpRemark.setMargins((int)textMargin,top+mTitle.getHeight()+mContents.getHeight()+(int)textMargin,0,(int)textMargin);
            //mTvRemark.setLayoutParams(lpRemark);
            mTvRemark.setVisibility(GONE);
        }else {//默认靠右顶边展示
            int right = (int)imgWidth+(int)textMargin;
            lpImage.height= (int)imgHeight;
            lpImage.width = (int)imgWidth;
            lpImage.gravity = Gravity.RIGHT;
            lpTitle.setMargins((int)textMargin,(int)textMargin,right,0);
            mTvTitle.setTextColor(titleColor);
            mTvTitle.setLayoutParams(lpTitle);
            lpContents.gravity = Gravity.CENTER_VERTICAL;
            lpContents.setMargins((int)textMargin,0,right,0);
            mTvContents.setTextColor(contentColor);
            mTvContents.setLayoutParams(lpContents);
            lpRemark.gravity = Gravity.BOTTOM;
            lpRemark.setMargins((int)textMargin,0,right,(int)textMargin);
            mTvRemark.setTextColor(remarkColor);
            mTvRemark.setLayoutParams(lpRemark);
        }
        mImageView.setLayoutParams(lpImage);
    }

    public int getImgPosition() {
        if (imgGravity==0){
            return POSITION_RIGHT;
        }else {
            if (imgGravity==1){
                return POSITION_LEFT;
            }
            else if (imgGravity==3){
                return POSITION_RIGHT;
            }
            else if (imgGravity==2){
                return POSITION_TOP;
            }
            else {
                return POSITION_RIGHT;
            }
        }
    }

    @Override
    public TextView getTitle() {
        return mTvTitle;
    }

    @Override
    public TextView getContents() {
        return mTvContents;
    }

    @Override
    public TextView getRemark() {
        return mTvRemark;
    }

    @Override
    public ImageView getImageView() {
        return mImageView;
    }

    @Override
    public void setTitleVisible(boolean visible) {
        mTvTitle.setVisibility(visible?VISIBLE:GONE);
    }

    @Override
    public void setContentsVisible(boolean visible) {
        mTvContents.setVisibility(visible?VISIBLE:GONE);
    }

    @Override
    public void setRemarkVisible(boolean visible) {
        mTvRemark.setVisibility(visible?VISIBLE:GONE);
    }

    @Override
    public void setImageVisible(boolean visible) {
        mImageView.setVisibility(visible?VISIBLE:GONE);
    }

    @Override public void setTitleText(String str) {
        mTvTitle.setText(str);
    }

    @Override public void setContentsText(String str) {
        mTvContents.setText(str);
    }

    @Override public void setRemarkText(String str) {
        mTvRemark.setText(str);
    }

    @Override public void setTitle(String title, int textSize, int color) {
        mTvTitle.setText(title);
        if (textSize!=0){
            mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
        }
        if (color!=0){
            mTvTitle.setTextColor(color);
        }
    }

    @Override public void setContent(String content, int textSize, int color) {
        mTvContents.setText(content);
        if (textSize!=0){
            mTvContents.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
        }
        if (color!=0){
            mTvContents.setTextColor(color);
        }
    }

    @Override public void setRemark(String remark, int textSize, int color) {
        mTvRemark.setText(remark);
        if (textSize!=0){
            mTvRemark.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
        }
        if (color!=0){
            mTvRemark.setTextColor(color);
        }

    }

    @Override public void setImageView(Context context, String url) {
        //圆角形变，除去左上左下两个角，设置右上右下两个角为圆角
        if (Build.VERSION.SDK_INT < 21) {
            CornerTransform transform = new CornerTransform(context, dip2px(10));
            if (imgGravity==POSITION_LEFT){
                transform.setExceptCorner(false, true, false, true);
            }else if (imgGravity==POSITION_RIGHT){
                transform.setExceptCorner(true, false, true, false);
            }else if (imgGravity==POSITION_TOP){
                transform.setExceptCorner(false, false, true, true);
            }else {
                transform.setExceptCorner(true, false, true, false);
            }
            Glide.with(this)
                    .load(url)
                    .apply(RequestOptions.centerCropTransform())//先centerCrop再设置图片圆角，否则会覆盖圆角效果
                    .apply(RequestOptions.bitmapTransform(transform))
                    .into(mImageView);
            return;
        }
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.centerCropTransform())
                .into(mImageView);
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * pix转dip
     */
    public  int pix2dip(int pix) {
        final float densityDpi = getResources().getDisplayMetrics().density;
        return (int) (pix / densityDpi + 0.5f);
    }



}
