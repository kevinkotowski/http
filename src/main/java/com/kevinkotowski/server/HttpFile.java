package com.kevinkotowski.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public class HttpFile implements IOFile {
    String fullPath;
    File file;

    public HttpFile(String fullPath) {
        this.fullPath = fullPath;
        this.file = new File(fullPath);
    }

    public HttpFile(File file) {
        this.fullPath = file.getAbsolutePath();
        this.file = file;
    }

    public boolean exists() {
        return this.file.exists();
    }

    public boolean createNewFile() throws IOException {
        return this.file.createNewFile();
    }

    public boolean canWrite() {
        return this.file.canWrite();
    }

    public void writeln(String line) throws IOException {
        FileWriter fw = new FileWriter(
                this.file.getAbsoluteFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(line);
        bw.close();
    }

    public String getAbsoluteFile() {
        return this.file.getAbsolutePath();
    }

    public boolean isDirectory() { return file.isDirectory(); }

    public String getName() { return this.file.getName(); }

    public boolean delete() {
        return this.file.delete();
    }

    public IOFile[] listFiles() {
        File[] files = this.file.listFiles();
        IOFile[] ioFiles = new IOFile[files.length];
        int x = 0;
        for (File file : files) {
            ioFiles[x] = new HttpFile(file);
            x++;
        }
        return ioFiles;
    }
}
