package com.gmail.babanin.aleksey;

import java.io.File;

public class CheckDir implements Runnable {
    private FileFinder finder;

    public CheckDir(FileFinder finder) {
        super();
        this.finder = finder;
    }

    @Override
    public void run() {
        String fileName = finder.getFileName();
        while (true) {
            File dir = finder.getDirectory();
            if (dir == null) {
                break;
            }
            
            File[] list = dir.listFiles();

            File check = new File(dir, fileName);

            if (check.exists()) {
                finder.putFile(check);
            }

            if (list == null) {
                continue;
            }

            for (File directory : list) {
                if (directory.isDirectory()) {
                    finder.putDirectories(directory);
                }
            }
        }
    }
}
