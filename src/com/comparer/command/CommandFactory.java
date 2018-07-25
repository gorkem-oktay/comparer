/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comparer.command;

import com.comparer.command.ftp.Cmp;
import com.comparer.command.ftp.Upload;

/**
 *
 * @author zafiru
 */
public class CommandFactory {

    public static ICommand init(String fullCommand) throws UnsupportedOperationException {
        String command = fullCommand.split(" ")[0];
        if(command.equals("cmp")){
            return new Cmp(fullCommand);
        } else if (command.equals("upload")) {
            return new Upload(fullCommand);
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
