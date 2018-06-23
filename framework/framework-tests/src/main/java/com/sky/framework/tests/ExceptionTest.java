package com.sky.framework.tests;

/**
 * Created by easyfun on 2018/6/23.
 */
public class ExceptionTest {

    public static void main(String[] args) {
        try {
            System.out.println(1 / 0);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("--------------------");
            System.out.println(e.getMessage());
            System.out.println("--------------------");
            e.printStackTrace();
        }
    }
}
