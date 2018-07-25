package com.comparer.core;

import org.apache.commons.net.ftp.FTPFile;

public class UploadedFile {

    public FTPFile file;
    public String type;

    public UploadedFile(FTPFile file, String type){
        this.file = file;
        this.type = type;
    }
}
