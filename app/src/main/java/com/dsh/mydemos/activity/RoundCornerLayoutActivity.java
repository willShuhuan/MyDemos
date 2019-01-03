package com.dsh.mydemos.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dsh.mydemos.R;
import com.dsh.mydemos.mycardview.PictureTextCardView;
import com.dsh.mydemos.view.CornerTransform;

/**
 * Created by dongshuhuan
 * createDate 2018/11/21
 * Email 785434788@qq.com
 * Description
 * Update Log
 */

public class RoundCornerLayoutActivity extends AppCompatActivity {

    @BindView(R.id.cardview) CardView cardview;
    @BindView(R.id.cardview2) CardView cardview2;
    @BindView(R.id.cover) ImageView cover;
    String url =
            "https://img-xhpfm.zhongguowangshi.com/Column/201801/139f3aba094342b48f211ab40a3bd5fc.png";
    String url2 =
            "https://img-xhpfm.zhongguowangshi.com/News/201808/55b1bd7c41f34bc7ab81533daaf828f3.jpg";
    String url3 =
            "https://img-xhpfm.zhongguowangshi.com/News/201805/6baa3596a10049e0bb71933b68d57434.png";
    String url4 =
            "https://img-xhpfm.zhongguowangshi.com/News/201806/e160a76f4cef4a76b1b5064bed9a4760.jpg";
    @BindView(R.id.coverll) ImageView coverll;
    @BindView(R.id.coverrl) ImageView coverrl;
    @BindView(R.id.cover_rci) ImageView cover_rci;
    @BindView(R.id.coverrcl) ImageView coverrcl;
    @BindView(R.id.coverrci) ImageView coverrci;
    @BindView(R.id.coverraf) ImageView coverraf;
    @BindView(R.id.ptcardview_top) PictureTextCardView ptcardviewTop;
    @BindView(R.id.ptcardview_left) PictureTextCardView ptcardviewLeft;
    @BindView(R.id.ptcardview_right) PictureTextCardView ptcardviewRight;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shadow_layout);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        Glide.with(this)
                .load(url)
                .apply(RequestOptions.centerCropTransform())
                .into(coverll);
        Glide.with(this)
                .load(url2)
                .apply(RequestOptions.centerCropTransform())
                .into(coverrl);

        //1. 开源控件RCRelativelayout： 直接加载图片，无需处理图片圆角
        Glide.with(this)
                .load(url)
                .apply(RequestOptions.centerCropTransform())
                .into(coverrcl);

        //2. CardView+开源控件RCImageView： xml或代码控制圆角，直接加载图片
        Glide.with(this)
                .load(url4)
                .apply(RequestOptions.centerCropTransform())
                .into(coverrci);
        //4. RoundAngleFrameLayout： 直接加载图片
        Glide.with(this)
                .load(url3)
                .apply(RequestOptions.centerCropTransform())
                .into(coverraf);
        //5.自定义cardview
        initPTCardView();

        //3.1 CardView：   5.0以下处理cardview内边距，Glide配合形变transform 处理图片圆角
        if (Build.VERSION.SDK_INT < 21) {
            //去除5.0以下cardView内间距
            cardview.setUseCompatPadding(false);
            cardview.setPreventCornerOverlap(false);
            cardview2.setUseCompatPadding(false);
            cardview2.setPreventCornerOverlap(false);
            ////圆角形变，除去左上左下两个角，设置右上右下两个角为圆角
            //CornerTransform transform = new CornerTransform(this, dip2px(10));
            //transform.setExceptCorner(true, false, true, false);
            //Glide.with(this)
            //        //.load(R.mipmap.ic_android)
            //        .load(url3)
            //        //.apply(RequestOptions.centerCropTransform())//先centerCrop再设置图片圆角，否则会覆盖圆角效果
            //        .apply(RequestOptions.bitmapTransform(transform))
            //        .into(cover);

            cover.setVisibility(View.GONE);
            cover_rci.setVisibility(View.VISIBLE);
            Glide.with(this)
                    //.load(R.mipmap.icon_android_big)
                    .load(url3)
                    .into(cover_rci);

            return;
        }
        //3.2 CardView： 5.0以上直接加载图片即可，无需处理图片圆角
        Glide.with(this)
                .load(R.mipmap.ic_news)
                .apply(RequestOptions.fitCenterTransform())
                .into(cover);
    }



    private void initPTCardView() {
        //图片居左显示
        ptcardviewTop.setViewsLayout(PictureTextCardView.POSITION_TOP);
        ptcardviewTop.setTitleText("自定义cardview：PictureTextCardView");
        ptcardviewTop.setContentsText("图片顶部展示");
        Glide.with(this)
                .load(url3)
                .apply(RequestOptions.centerCropTransform())
                .into(ptcardviewTop.getImageView());

        //图片居左展示
        ptcardviewLeft.setViewsLayout(PictureTextCardView.POSITION_LEFT);
        ptcardviewLeft.setTitle("自定义cardview：PictureTextCardView",24,getResources().getColor(R.color.red));
        ptcardviewLeft.setContent("图片靠左展示",20,getResources().getColor(R.color.blue));
        ptcardviewLeft.setRemark("2018-11-23",16,getResources().getColor(R.color.gray));
        ptcardviewLeft.setImageView(this,url4);


        //所有属性都用xml指定，java代码填充内容
        ptcardviewRight.getTitle().setText("自定义cardview：PictureTextCardView");
        ptcardviewRight.getContents().setText("图片靠右展示");
        ptcardviewRight.getRemark().setText("2018-11-23");
        ptcardviewRight.setImageView(this,url);

    }


    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
