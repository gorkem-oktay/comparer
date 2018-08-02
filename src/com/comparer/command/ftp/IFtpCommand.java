package com.comparer.command.ftp;

import com.comparer.command.ICommand;
import com.comparer.core.ftp.Server;

import java.io.IOException;

public abstract class IFtpCommand extends ICommand {

    Server server1;
    Server server2;

    IFtpCommand(String command) {
        super(command);
    }

    @Override
    public void initVars() throws IOException {
        server1 = new Server("config/server1.json");
        server2 = new Server("config/server2.json");
    }
}
