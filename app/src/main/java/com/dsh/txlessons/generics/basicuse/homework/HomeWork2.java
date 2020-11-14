package com.dsh.txlessons.generics.basicuse.homework;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * @author dongshuhuan
 * date 2020/11/12
 * version
 */
class HomeWork2 {
    public static void main(String[] args) {
        ArrayList<? extends View> viewList = new ArrayList<TextView>();
        ArrayList<? super AppCompatTextView> viewList2 = new ArrayList<TextView>();
    }
}
