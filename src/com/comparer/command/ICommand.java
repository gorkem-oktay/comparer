/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comparer.command;

import com.jcraft.jsch.JSchException;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author zafiru
 */
public abstract class ICommand {

    protected ArrayList<String> parameters;
    protected ArrayList<String> options;
    private final String command;

    public ICommand(String command){
        this.command = command;
        this.parameters = new ArrayList<>();
        this.options = new ArrayList<>();
    }

    private void parseCommand(){
        String[] args = command.split(" ");
        for(int i = 1; i < args.length; i++){
            if(args[i].startsWith("-")){
                options.add(args[i]);
            } else {
                parameters.add(args[i]);
            }
        }
    }

    public void execute(){
        parseCommand();
        try {
            initVars();
        } catch(IOException ex) {
            ex.printStackTrace();
            System.out.println("Couldn't parse variables");
            return;
        }
        try {
            connectToSSH();
        } catch (JSchException ex) {
            ex.printStackTrace();
            System.out.println("Couldn't connect to SSH");
            return;
        }
        process();
        print();
        disconnectFromSSH();
    }

    public abstract void disconnectFromSSH();
    public abstract void connectToSSH() throws JSchException;
    public abstract void initVars() throws IOException;
    public abstract void process();
    public abstract void print();
}
