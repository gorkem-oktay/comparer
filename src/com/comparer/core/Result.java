/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comparer.core;

import java.util.ArrayList;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author zafiru
 */
public class Result {

    public ArrayList<FilePair> changedFiles;
    public ArrayList<ExtraFile> extraFiles;
    public int pathIndex;

    public Result(int pathIndex){
        this.changedFiles = new ArrayList<>();
        this.extraFiles = new ArrayList<>();
        this.pathIndex = pathIndex;
    }

    public void addExtraFile(FTPFile file, int server){
        this.extraFiles.add(new ExtraFile(file, server));
    }

    public void addChangedFile(FTPFile file1, FTPFile file2){
        this.changedFiles.add(new FilePair(file1, file2));
    }
}
