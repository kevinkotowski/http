package com.kevinkotowski.server;

import java.io.File;
import java.io.IOException;

/**
 * Created by kevinkotowski on 6/9/16.
 */
public interface IOFile {
    public boolean exists();
    public boolean createNewFile() throws IOException;
    public boolean canWrite();
    public void writeln(String line) throws IOException;
    public String getAbsoluteFile();
    public boolean isDirectory();
    public String getName();
    public byte[] getBytes();
    public boolean delete();
}
