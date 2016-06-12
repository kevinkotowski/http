package com.kevinkotowski.server;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Created by kevinkotowski on 6/11/16.
 */
public class _FileSystemTest {
    // NOTE: this app only has requirements for image types found in cob_spec.
    String workingDir = System.getProperty("user.dir");
    String testDir = "/src/test/java/com/kevinkotowski/server";

    @Test
    public void doesFileExist() throws Exception {
        String path = this.workingDir + this.testDir + "/TestImage.gif";
        IHFileSystem fileSystem = new HttpFileSystem(path);
        assertTrue(fileSystem.exists());
    }

    @Test
    public void isDirectory() throws Exception {
        String path = this.workingDir + this.testDir + "/";
        IHFileSystem fileSystem = new HttpFileSystem(path);
        assertTrue(fileSystem.isDirectory());
    }

    @Test
    public void isImageGif() throws Exception {
        String path = this.workingDir + this.testDir + "/TestImage.gif";
        IHFileSystem fileSystem = new HttpFileSystem(path);
        assertTrue(fileSystem.isImage());
    }

    @Test
    public void isImageJpeg() throws Exception {
        String path = this.workingDir + this.testDir + "/TestImage.jpeg";
        IHFileSystem fileSystem = new HttpFileSystem(path);
        assertTrue(fileSystem.isImage());
    }

    @Test
    public void isImagePng() throws Exception {
        String path = this.workingDir + this.testDir + "/TestImage.png";
        IHFileSystem fileSystem = new HttpFileSystem(path);
        assertTrue(fileSystem.isImage());
    }

    @Test
    public void isTextFile() throws Exception {
        String path = this.workingDir + this.testDir + "/TestTextFile.txt";
        IHFileSystem fileSystem = new HttpFileSystem(path);
        assertTrue(fileSystem.isTextFile());
    }

    @Test
    public void getBytesImage() throws Exception {
        String path = this.workingDir + this.testDir + "/TestImage.gif";
        File file = new File(path);
        IHFileSystem fileSystem = new HttpFileSystem(path);

        assertTrue( fileSystem.getBytes().length > 0 );
        assertEquals(getBytes(file).length, fileSystem.getBytes().length);
    }

    @Test
    public void getBytesText() throws Exception {
        String path = this.workingDir + this.testDir + "/TestTextFile.txt";
        File file = new File(path);
        IHFileSystem fileSystem = new HttpFileSystem(path);

        assertTrue( fileSystem.getBytes().length > 0 );
        assertEquals(getBytes(file).length, fileSystem.getBytes().length);
    }

    public byte[] getBytes(File file) {
        try {
            return Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        } catch (IOException e) {
            System.out.println("ERROR: File can't read: " +
                    file.getName() + "\n");
            return null;
        }
    }
}