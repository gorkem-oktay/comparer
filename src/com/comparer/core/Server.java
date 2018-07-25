/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comparer.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.json.JSONObject;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author zafiru
 */
public class Server {

    public String name;
    private String url;
    private String username;
    private String password;
    public ArrayList<String> pathes;

    private FTPClient client;

    public Server(String filePath) throws IOException{
        File file = new File(filePath);
        String fileAsString = new String(Files.readAllBytes(file.toPath()));
        JSONObject json = new JSONObject(fileAsString);

        this.name = (String) json.get("name");
        this.url = (String) json.get("url");
        this.username = (String) json.get("username");
        this.password = (String) json.get("password");
        this.pathes = new ArrayList<>();

        List<Object> tempPathes = json.getJSONArray("pathes").toList();
        for(Object object : tempPathes){
            this.pathes.add(Objects.toString(object, ""));
        }
    }

    public void login() throws IOException {
        client = new FTPClient();
        client.connect(url);
        client.login(username, password);
    }

    public FTPFile[] getFiles(int pathIndex) throws IOException {
        if(client == null || client.isConnected()) {
            login();
        }
        for (String dir : pathes.get(pathIndex).split("/")) {
            client.changeWorkingDirectory(dir);
        }
        return client.listFiles();
    }

    public void uploadFile(FTPFile file, Server otherServer) throws IOException {
        InputStream stream = client.retrieveFileStream(file.getName());
        otherServer.client.storeFile(file.getName(), stream);
        client.completePendingCommand();
    }

    public void disconnect() {
        try {
            client.logout();
            client.disconnect();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
