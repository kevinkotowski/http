package com.kevinkotowski.server;

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
    public boolean delete() { return true; }
}
