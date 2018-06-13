package com.sky.framework.tests;

import com.sky.framework.common.utils.HttpUtil;

/**
 * Created by easyfun on 2018/5/31.
 */
public class HttpTest {

    public static void main(String[] args) throws Exception {
        String result = HttpUtil.getRequest("http://www.linkblock.com", "", "UTF-8");
        System.out.println(result);
    }

}
