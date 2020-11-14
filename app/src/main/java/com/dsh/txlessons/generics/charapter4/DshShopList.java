package com.dsh.txlessons.generics.charapter4;

import com.dsh.txlessons.generics.basicuse.fruit.Apple;
import com.dsh.txlessons.generics.charapter3.Shop;
import java.util.List;

/**
 * @author dongshuhuan
 * date 2020/11/14
 * version
 */
interface DshShopList<T> extends List<Shop<T>> {

}
