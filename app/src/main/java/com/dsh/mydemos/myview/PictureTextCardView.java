package com.dsh.mydemos.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.AttributeSet;
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

    private static final String TAG = "PictureTextCardView";

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
    private String imgGravity;

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
            imgGravity = ta.getString(R.styleable.PictureTextCardView_img_gravity);
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



    @Override
    public void setViewsLayout(int direction) {
        FrameLayout.LayoutParams lpImage = (FrameLayout.LayoutParams) mImageView.getLayoutParams();
        FrameLayout.LayoutParams lpTitle = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        FrameLayout.LayoutParams lpContents = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        FrameLayout.LayoutParams lpRemark = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        if (direction==POSITION_LEFT){
            int left = (int)imgWidth+(int)textMargin;
            lpImage.height= (int)imgHeight;
            lpImage.width = (int)imgWidth;
            lpImage.gravity = Gravity.LEFT;
            lpTitle.setMargins(left,(int)textMargin,(int)textMargin,0);
            mTvTitle.setLayoutParams(lpTitle);
            lpContents.gravity = Gravity.CENTER_VERTICAL;
            lpContents.setMargins(left,0,(int)textMargin,0);
            mTvContents.setLayoutParams(lpContents);
            lpRemark.gravity = Gravity.BOTTOM;
            lpRemark.setMargins(left,0,(int)textMargin,(int)textMargin);
            mTvRemark.setLayoutParams(lpRemark);
        }else if (direction==POSITION_TOP){//如果图片为顶部展示，默认只显示两行文本，title和contents，并且title为singleLine单行展示
            lpImage.height= (int)imgHeight;
            lpImage.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lpImage.gravity = Gravity.TOP;
            int top = (int)imgHeight+(int)textMargin;
            lpTitle.setMargins((int)textMargin,top,0,0);
            mTvTitle.setSingleLine(true);
            mTvTitle.setLayoutParams(lpTitle);
            lpContents.setMargins((int)textMargin,top+dip2px(18)+(int)textMargin,0,(int)textMargin);
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
            mTvTitle.setLayoutParams(lpTitle);
            lpContents.gravity = Gravity.CENTER_VERTICAL;
            lpContents.setMargins((int)textMargin,0,right,0);
            mTvContents.setLayoutParams(lpContents);
            lpRemark.gravity = Gravity.BOTTOM;
            lpRemark.setMargins((int)textMargin,0,right,(int)textMargin);
            mTvRemark.setLayoutParams(lpRemark);
        }
        mImageView.setLayoutParams(lpImage);
    }

    public int getImgPosition() {
        if (TextUtils.isEmpty(imgGravity)){
            return POSITION_RIGHT;
        }else {
            if (imgGravity.equals("left")){
                return POSITION_LEFT;
            }
            else if (imgGravity.equals("right")){
                return POSITION_RIGHT;
            }
            else if (imgGravity.equals("top")){
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

    @Override public void setImageView(Context context, String url) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.centerCropTransform())
                .into(mImageView);
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
