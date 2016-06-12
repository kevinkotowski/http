package com.kevinkotowski.server;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

/**
 * Created by kevinkotowski on 6/11/16.
 */
public class HttpFileSystem implements IHFileSystem {
    String fullPath = null;
    File file;
    boolean exists = false;
    boolean isDirectory = false;
    boolean isImage = false;
    boolean isTextFile = false;

    public HttpFileSystem(String fullPath) {
        this.fullPath = fullPath;

        this.file = new File(fullPath);

        this.exists = Files.exists(
                Paths.get(this.file.getAbsolutePath()), NOFOLLOW_LINKS);

        if (this.exists) {
            this.isDirectory = this.file.isDirectory();
            if (!this.isDirectory) {
                if (this.imageType() != null) {
                    this.isImage = true;
                } else {
                    this.isTextFile = true;
                }
            }
        }
    }

    public boolean exists() {
        return this.exists;
    }

    public boolean isDirectory() {
        return this.isDirectory;
    }

    public boolean isImage() {
        return this.isImage;
    }

    public boolean isTextFile() {
        return this.isTextFile;
    }

    public byte[] getBytes() {
        try {
            return Files.readAllBytes(Paths.get(this.file.getAbsolutePath()));
        } catch (IOException e) {
            System.err.println("ERROR: FileSystem can't read: " +
                    this.file.getAbsolutePath() + "\n");
            return null;
        }
    }

    private String imageType() {
        String imageType = "";

        String mimeType =
                new MimetypesFileTypeMap().getContentType(this.file);
        String type = mimeType.split("/")[0].toLowerCase();
        if (mimeType.equals("application/octet-stream") &&
                file.getName().matches(".+\\.png")) {
            imageType = "png";
        } else if (type.equals("image")) {
            imageType = mimeType.split("/")[1].toLowerCase();
        } else {
            imageType = null;
        }
        return imageType;
    }
}
