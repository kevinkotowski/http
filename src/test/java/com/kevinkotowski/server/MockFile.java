package com.kevinkotowski.server;

import java.io.File;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public class MockFile implements IOFile {
    String path;
    boolean directory = true;

    public MockFile(String path) {
        this.path = path;
    }
    public boolean exists() { return true; }
    public boolean createNewFile() { return true; }
    public boolean canWrite() { return true; }
    public void writeln(String line) {}
    public String getAbsoluteFile() { return this.path; }
    public void setDirectory(boolean directory) { this.directory = directory; }
    public boolean isDirectory() { return this.directory; }
    public String getName() { return "filename.txt"; }
    public boolean delete() { return true; }
    public IOFile[] listFiles() {
        MockFile[] files = new MockFile[3];
        for (int x = 0; x < files.length; x++) {
            files[x] = new MockFile("boo");
            files[x].setDirectory(false);
        }
        return files;
    }
}
