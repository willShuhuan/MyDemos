package com.dsh.txlessons.generics.charapter4;

import android.os.Parcel;
import android.os.Parcelable;
import com.dsh.txlessons.generics.basicuse.fruit.Apple;
import java.io.Serializable;

/**
 * @author dongshuhuan
 * date 2020/11/13
 * version
 */
class SerializableApple extends Apple implements Serializable {

    public SerializableApple(float price) {
        super(price);
    }
}
