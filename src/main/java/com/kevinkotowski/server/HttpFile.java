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

    public byte[] getBytes() {
        try {
            return Files.readAllBytes(Paths.get(this.file.getAbsolutePath()));
        } catch (IOException e) {
            System.out.println("ERROR: File can't read: " +
                    this.file.getName() + "\n");
            return null;
        }
    }

    public boolean delete() {
        return this.file.delete();
    }
}
