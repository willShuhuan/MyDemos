package com.dsh.txlessons.annotaionprocessing;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dsh.lib_annotations.BindView;
import com.dsh.mydemos.R;
import com.xhmm.lib.Binding;

public class AptActivity extends AppCompatActivity {

    @BindView(R.id.textView) TextView textView;
    @BindView(R.id.layout) ViewGroup layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apt);

        //InnerBinding创建辅助AptActivityBinding实现findViewById
        //InnerBinding.bind(this);


        Binding.bind(this);

        haha();

    }

    /**
     * 方法过时，使用{@link #hehe()}来替代它
     */
    @Deprecated
    void haha(){
        textView.setText("hahahaha");
        layout.setBackgroundColor(Color.CYAN);
    }

    void hehe(){

    }
}