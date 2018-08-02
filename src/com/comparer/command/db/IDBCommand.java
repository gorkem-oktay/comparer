package com.comparer.command.db;

import com.comparer.command.ICommand;
import com.comparer.core.SSH;
import com.comparer.core.database.DatabaseFactory;
import com.comparer.core.database.IDatabase;
import com.jcraft.jsch.JSchException;

import java.io.IOException;

public abstract class IDBCommand extends ICommand {

    IDatabase db1;
    IDatabase db2;

    IDBCommand(String command) {
        super(command);
    }

    @Override
    public void initVars() throws IOException{
        db1 = DatabaseFactory.init("config/database1.json");
        db2 = DatabaseFactory.init("config/database2.json");
    }

    @Override
    public void connectToSSH() throws JSchException {
        SSH ssh1 = db1.getSSH();
        if(ssh1 != null){
            db1.port = ssh1.connect(db1.getHost(), db1.port, 4321);
        }

        SSH ssh2 = db2.getSSH();
        if(ssh2 != null){
            db2.port = ssh2.connect(db2.getHost(), db2.port, 4322);
        }
    }

    @Override
    public void disconnectFromSSH() {
        SSH ssh1 = db1.getSSH();
        if(ssh1 != null){
            ssh1.disconnect();
        }

        SSH ssh2 = db2.getSSH();
        if(ssh2 != null){
            ssh2.disconnect();
        }
    }
}
