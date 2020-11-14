package com.dsh.txlessons.generics.basicuse.simcard;

/**
 * @author dongshuhuan
 * date 2020/11/12
 * version
 */
public class ChinaMobile implements SimCard, Cloneable {

    private String name;
    private String id;

    public ChinaMobile(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override public String toString() {
        return "中国电信电话卡{" +
                "姓名：'" + name + '\'' +
                ", 身份证号：'" + id + '\'' +
                ", 余额：'" + (int)(Math.random()*1000_000) + '\'' +
                '}';
    }
}
