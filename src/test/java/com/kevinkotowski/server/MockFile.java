package com.kevinkotowski.server;

import java.io.File;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public class MockFile implements IOFile {
    String path;

    public MockFile(String path) {
        this.path = path;
    }
    public boolean exists() { return true; }
    public boolean createNewFile() { return true; }
    public boolean canWrite() { return true; }
    public void writeln(String line) {}
    public String getAbsoluteFile() { return this.path; }
    public boolean isDirectory() { return true; }
    public String getName() { return "filename.txt"; }
    public byte[] getBytes() { return null; }
    public boolean delete() { return true; }
}
