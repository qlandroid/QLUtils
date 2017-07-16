package com.ql.view.utils;

/**
 * Created by android on 2017/7/11.
 */

public class StringUtils {

    public static boolean isEmpty(String str){
        if (str == null || str.isEmpty()){
            return true;
        }
        return false;
    }
}
