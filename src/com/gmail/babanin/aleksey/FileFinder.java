package com.gmail.babanin.aleksey;

import java.io.File;
import java.util.ArrayList;

public class FileFinder {
    private ArrayList<File> found;
    private ArrayList<File> directories = new ArrayList<>();
    private String fileName;
    private boolean finish = false;
    private int threads;
    private int inWait = 0;

    public FileFinder(File directory, String fileName, int threads) {
        super();
        this.directories.add(checkDirectory(directory));
        this.fileName = fileName;
        this.threads = threads;
    }

    private final File checkDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            return directory;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String getFileName() {
        return fileName;
    }
    
    public synchronized File getDirectory() {
        try {
            while (directories.size() == 0) {
                inWait += 1;
                if (inWait == threads) {
                    finish = true;
                }
                if (finish) {
                    notifyAll();
                    return null;
                }
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        File dir = directories.get(0);
        directories.remove(0);
        return dir;
    }

    public synchronized void putDirectories(File directory) {
        this.directories.add(directory);
        notify();
    }

    public void putFile(File file) {
        this.found.add(file);
    }

    public void search() {
        found = new ArrayList<>();
        CheckDir checker[] = new CheckDir[threads];
        Thread[] th = new Thread[threads];

        try {
            for (int i = 0; i < th.length; i++) {
                checker[i] = new CheckDir(this);
                th[i] = new Thread(checker[i]);
                th[i].start();
            }
            for (int i = 0; i < th.length; i++) {
                th[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        if (finish == false) {
            return "Start search first";
        }
        
        if (found.size() == 0) {
            return "File not found";
        }
        
        String s = "";
        for (int i = 0; i < found.size(); i++) {
            s += found.get(i).getAbsolutePath();
            s += System.lineSeparator();
        }
        return s;
    }

}
