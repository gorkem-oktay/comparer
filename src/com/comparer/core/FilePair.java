/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comparer.core;

import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author zafiru
 */
public class FilePair {

    public FTPFile server1File;
    public FTPFile server2File;

    FilePair(FTPFile server1File, FTPFile server2File){
        this.server1File = server1File;
        this.server2File = server2File;
    }
}
