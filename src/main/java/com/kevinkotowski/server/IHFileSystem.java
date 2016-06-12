package com.kevinkotowski.server;

import java.io.File;

/**
 * Created by kevinkotowski on 6/11/16.
 */
public interface IHFileSystem {
    public boolean exists();
    public boolean isDirectory();
    public boolean isImage();
    public boolean isTextFile();
    public byte[] getBytes();
}
