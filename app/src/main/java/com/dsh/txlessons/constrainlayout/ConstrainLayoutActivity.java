package com.dsh.txlessons.constrainlayout;

import android.support.constraint.ConstraintSet;
import android.support.constraint.Placeholder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.dsh.mydemos.R;

public class ConstrainLayoutActivity extends AppCompatActivity implements View.OnClickListener {

    Placeholder placeholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_constrain_layout);

        //constraintCircleAngle 角度布局
        //setContentView(R.layout.activity_circular_positioning);

        //constrainedWidth  文字宽度约束
        //setContentView(R.layout.activity_sample_constrained);

        //bias 文字左右偏移量 0-1之间 配合constrainedWidth使用
        //setContentView(R.layout.activity_sample_bias);

        //goneMarginStart  图片左侧margin
        //setContentView(R.layout.activity_sample_gonemargin);

        //chainStyle  约束链风格
        //setContentView(R.layout.activity_sample_chainstyle);

        //constraintDimensionRatio 宽高比
        //setContentView(R.layout.activity_dimension_ratio);

        //百分比布局
        //setContentView(R.layout.activity_percent);


        //辅助控件
        // 水平方向辅助 android.support.constraint.Guideline
        //setContentView(R.layout.activity_guide_line);

        //android.support.constraint.Group
        //通过 constraint_referenced_ids 使用引用的方式来避免布局嵌套
        //setContentView(R.layout.activity_group);
        //findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
        //    @Override public void onClick(View v) {
        //        findViewById(R.id.group).setVisibility(View.GONE);
        //    }
        //});


        //Layer
        //和 Group 类似，同样通过引用的方式来避免布局嵌套，可以为一组控件统一设
        //置旋转/缩放/ 位移。
        //setContentView(R.layout.activity_layer);
        //findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
        //    @Override public void onClick(View v) {
        //        findViewById(R.id.layer).setRotation(45f);
        //        findViewById(R.id.layer).setTranslationY(100f);
        //        findViewById(R.id.layer).setTranslationX(100f);
        //    }
        //});

        //Barrier
        //通过设置一组控件的某个方向的屏障，来 避免布局嵌套 。
        //setContentView(R.layout.activity_barrier);

        //ConstraintHelper
        //setContentView(R.layout.activity_circular_reveal);

        //Placeholder
        //通过 setContentId 来将指定控件放到占位符的位置。
        //setContentView(R.layout.activity_place_holder);
        //placeholder = findViewById(R.id.placeholder);

        //Flow
        //通过引用的方式来避免布局嵌套。
        //wrapMode
        //chain aligned none(默认)
        //注意这个控件是可以被测量的，所以对应方向上的值可能需要被确定(即不能 只约束同一方 向的单个约束)
        setContentView(R.layout.activity_flow);

        //ConstraintSet
        // 使用 ConstraintSet 对象来动态修改布局。
        //防止布局中有无 id 控件时报错，需要设置 isForceId = false
        //通过 ConstraintSet#clone 来从 xml 布局中获取约束集。
        //setContentView(R.layout.activity_constraint_set);
        //setContentView(R.layout.activity_constraint_start);
        //setContentView(R.layout.activity_constraint_end);






    }

    @Override
    public void onClick(View view) {
        //placeholder.setContentId(view.getId());

        //val constraintLayout = view as ConstraintLayout
        //val constraintSet = ConstraintSet().apply {
        //    clone(constraintLayout)
        //    connect(
        //        R.id.twitter,
        //        ConstraintSet.BOTTOM,
        //        ConstraintSet.PARENT_ID,
        //        ConstraintSet.BOTTOM
        //    )
        //}
        //constraintSet.applyTo(constraintLayout)

    }
}