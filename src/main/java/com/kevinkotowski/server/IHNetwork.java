package com.kevinkotowski.server;

import java.io.*;

/**
 * Created by kevinkotowski on 5/5/16.
 */
public interface IHNetwork {
    public IHRequest next() throws IOException;
}
