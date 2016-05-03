package com.kevinkotowski.server;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by kevinkotowski on 5/3/16.
 */
public class FileProtocol {
    File file;
    FileReader fileReader;

    FileProtocol(File file) {
        this.file = file;
        try {
            this.fileReader = new FileReader(this.file);
        } catch (IOException e) {
            System.out.println("Exception caught initializing FileReader: "
                    + this.file.getAbsolutePath() );
            System.out.println(e.getMessage());
        }
    }

    public int read (char[] chars) {
        int response = 0;
        try {
            response = this.fileReader.read(chars);
        } catch (IOException e) {
            System.out.println("Exception caught when trying to read File: "
                    + this.file.getAbsolutePath() );
            System.out.println(e.getMessage());
        }
        return response;
    }
}
