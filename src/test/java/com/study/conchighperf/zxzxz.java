package com.study.conchighperf;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class zxzxz {

    public static void main(String[] args) {
        double a=Stream.of("33248953498578934").distinct().count();
        System.out.println(a);
        Lock lock=new ReentrantLock();
    }
}
