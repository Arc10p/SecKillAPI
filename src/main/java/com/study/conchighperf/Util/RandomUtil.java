package com.study.conchighperf.Util;

import java.util.Random;

public class RandomUtil {
    public static String getName(){
        Random random=new Random();
        StringBuffer stringBuffer=new StringBuffer();

        for (int i = 0; i < random.nextInt(3)+2; i++) {
            stringBuffer.append(new String(new char[] { (char) (new Random().nextInt(20902) + 19968) }));
        }

        return stringBuffer.toString();
    }
    public static String getTele(){
        Random random=new Random();
        StringBuffer stringBuffer=new StringBuffer();

        for (int i = 0; i < 11; i++) {
            stringBuffer.append(random.nextInt(10));
        }

        return stringBuffer.toString();
    }
    public static String getEmail(){
        Random random=new Random();
        StringBuffer stringBuffer=new StringBuffer();
        String s="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int i = 0; i < random.nextInt(20); i++) {
            stringBuffer.append(s.charAt(random.nextInt(62)));
        }
        stringBuffer.append("@gmail.com");
        return stringBuffer.toString();
    }
    public static int getInt(){
        Random random =new Random();
        return random.nextInt(50000);

    }
}
