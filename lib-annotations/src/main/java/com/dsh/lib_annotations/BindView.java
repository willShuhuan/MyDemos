package com.dsh.lib_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dongshuhuan
 * date 2020/11/9
 * version
 */
@Retention(RetentionPolicy.SOURCE)//不保留只在编译的时候使用
@Target(ElementType.FIELD)
public @interface BindView {
    int value();
}
