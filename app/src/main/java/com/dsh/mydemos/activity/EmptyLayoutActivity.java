package com.dsh.mydemos.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.dsh.mydemos.EmptyLayout.CommonDataEmptyView;
import com.dsh.mydemos.R;
import com.dsh.mydemos.base.BaseActivity;

/**
 * Created by dongshuhuan
 * createDate 2018/12/21
 * Email 785434788@qq.com
 * Description
 * Update Log
 */

public class EmptyLayoutActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.empty_viewlayout) CommonDataEmptyView emptyView;
    @BindView(R.id.error_network) Button errorNetwork;
    @BindView(R.id.error_dataempty) Button errorDataempty;
    @BindView(R.id.error_diy) Button errorDiy;

    @Override public int getContentView() {
        return R.layout.activity_empty_layout;
    }

    @Override public void initView() {

    }

    @Override public void setListener() {

    }

    @Override public void initData() {

    }

    @Override public void onClick(View arg0) {

    }

    @OnClick({ R.id.error_network, R.id.error_dataempty, R.id.error_diy })
    public void onViewClicked(View view) {
        resetViews();
        switch (view.getId()) {
            case R.id.error_network:
                emptyView.setType(CommonDataEmptyView.TYPE_NETWORK_ERROR);
                emptyView.setBtnText("点击重试");
                emptyView.setErrorImg(R.mipmap.network_error);
                emptyView.setClickViewsListener(new CommonDataEmptyView.onViewsClickListener() {
                    @Override public void onRetryBtnClick() {
                        Toast.makeText(EmptyLayoutActivity.this, "点击重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override public void onErrorTextClick() {

                    }

                    @Override public void onErrorImgClick() {

                    }
                });
                break;
            case R.id.error_dataempty:
                emptyView.setType(CommonDataEmptyView.TYPE_DATA_EMPTY);
                emptyView.setClickViewsListener(new CommonDataEmptyView.onViewsClickListener() {
                    @Override public void onRetryBtnClick() {

                    }

                    @Override public void onErrorTextClick() {
                        Toast.makeText(EmptyLayoutActivity.this, "点击了错误文字", Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override public void onErrorImgClick() {
                        Toast.makeText(EmptyLayoutActivity.this, "点击了错误占位图", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
                break;
            case R.id.error_diy:
                emptyView.setType(CommonDataEmptyView.TYPE_DATA_EMPTY);
                emptyView.setMarginBottom(50);
                emptyView.setErrorImg(R.mipmap.ic_android);
                emptyView.setBtnVisible(true);
                emptyView.setBtnText("自定义按钮文字");
                emptyView.setErrorText("自定义错误提示");
                emptyView.setErrorTextColor(R.color.colorPrimary);
                emptyView.setClickViewsListener(new CommonDataEmptyView.onViewsClickListener() {
                    @Override public void onRetryBtnClick() {
                        Toast.makeText(EmptyLayoutActivity.this, "点击了重试按钮", Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override public void onErrorTextClick() {
                        Toast.makeText(EmptyLayoutActivity.this, "点击了错误提示", Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override public void onErrorImgClick() {
                        Toast.makeText(EmptyLayoutActivity.this, "点击了错误占位图", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
                break;
            default:
                break;
        }
    }

    private void resetViews() {
        emptyView.setCenter();
        emptyView.setType(CommonDataEmptyView.TYPE_DATA_EMPTY);
        emptyView.setBtnVisible(false);
        emptyView.setErrorImg(R.mipmap.no_data);
        emptyView.setErrorText("no data");
        emptyView.setErrorTextColor(R.color.gray);
        emptyView.setErrorTextVisible(true);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
