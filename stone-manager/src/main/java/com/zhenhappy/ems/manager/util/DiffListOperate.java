package com.zhenhappy.ems.manager.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/5/4.
 */
public class DiffListOperate {
    public List intersect(List ls, List ls2) {
        List list = new ArrayList(Arrays.asList(new Object[ls.size()]));
        Collections.copy(list, ls);
        list.retainAll(ls2);
        return list;
    }

    public List union(List ls, List ls2) {
        List list = new ArrayList(Arrays.asList(new Object[ls.size()]));
        Collections.copy(list, ls);
        list.addAll(ls2);
        return list;
    }

    public List diff(List ls, List ls2) {
        List list = new ArrayList(Arrays.asList(new Object[ls.size()]));
        Collections.copy(list, ls);
        list.removeAll(ls2);
        return list;
    }

    public static void main(String[] args) {
        DiffListOperate opt = new DiffListOperate();
        List l1 = new ArrayList();
        l1.add(1);
        l1.add(2);
        l1.add(3);
        l1.add(4);
        List l2 = new ArrayList();
        l2.add(3);
        l2.add(4);
        l2.add(1);
        l2.add(2);
        List diffList = opt.diff(l1, l2);
        System.out.println("差集有" + diffList.size() + "个，分别为：");
        for (int i = 0; i < diffList.size(); i++) {
            System.out.print(diffList.get(i) + " ");
        }
        /*diffList = opt.diff(l2, l1);
        for (int i = 0; i < diffList.size(); i++) {
            System.out.print(diffList.get(i) + " ");
        }*/
        System.out.println();
    }
}
