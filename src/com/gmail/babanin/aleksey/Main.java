package com.gmail.babanin.aleksey;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        FileFinder f = new FileFinder(new File("D:\\"), "desktop.ini", Runtime.getRuntime().availableProcessors());
        f.search();
        time = System.currentTimeMillis() - time;
        System.out.println(f);
        System.out.println(time + " ms");
        
    }

}
