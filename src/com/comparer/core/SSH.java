package com.comparer.core;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.json.JSONObject;

public class SSH {

    private String host;
    private int port;
    private String user;
    private String password;
    private String privateKey;

    private Session session;

    public SSH(JSONObject json) {
        host = json.getString("host");
        port = json.getInt("port");
        user = json.getString("user");
        if (json.has("password")) {
            password = json.getString("password");
        }
        if (json.has("private_key")) {
            privateKey = json.getString("private_key");
        }
    }

    public int connect(String remoteHost, int remotePort, int localPort) throws JSchException {
        JSch jsch = new JSch();
        if (privateKey != null) {
            if (password != null) {
                jsch.addIdentity(privateKey, password);
            } else {
                jsch.addIdentity(privateKey);
            }
        }

        session = jsch.getSession(user, host, port);
        if (password != null) {
            session.setPassword(password);
        }

        session.setConfig("StrictHostKeyChecking", "no");

        session.connect();
        return session.setPortForwardingL(localPort, remoteHost, remotePort);
    }

    public void disconnect() {
        if (session != null) {
            session.disconnect();
        }
    }
}
