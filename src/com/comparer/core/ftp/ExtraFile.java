/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comparer.core.ftp;

import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author zafiru
 */
public class ExtraFile {

    public FTPFile file;
    public int server;

    public ExtraFile(FTPFile file, int server){
        this.file = file;
        this.server = server;
    }
}
