package com.kevinkotowski.server;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by kevinkotowski on 6/11/16.
 */
public class MockFileSystem implements IHFileSystem {
    public boolean exists;
    boolean directory;
    boolean image;
    boolean textFile;
    boolean inPath;

    public void setExists(boolean exists) {
        this.exists = exists;
    }
    public boolean exists() {
        return this.exists;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }
    public boolean isDirectory() {
        return this.directory;
    }

    public void setImage(boolean image) {
        this.image = image;
    }
    public boolean isImage() {
        return this.image;
    }

    public void setTextFile(boolean textFile) {
        this.textFile = textFile;
    }
    public boolean isTextFile() {
        return this.textFile;
    }

    public void setInPath(boolean inPath) {
        this.inPath = inPath;
    }
    public boolean inPath(String testPath) {
        return this.inPath;
    }

    public byte[] getBytes() {
        return null;
    }
}
