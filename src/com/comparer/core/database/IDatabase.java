package com.comparer.core.database;

import com.comparer.core.SSH;
import org.json.JSONObject;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;

public abstract class IDatabase {

    private String host;
    public int port;
    private String database;
    private String username;
    private String password;
    private SSH ssh;

    IDatabase(JSONObject json) {
        this.database = json.getString("database");
        this.host = json.getString("host");
        this.port = json.getInt("port");
        this.username = json.getString("username");
        this.password = json.getString("password");
        if (json.has("ssh")) {
            ssh = new SSH(json.getJSONObject("ssh"));
        }
    }

    public String getHost() {
        return host;
    }

    public SSH getSSH(){
        return ssh;
    }

    public String getDatabase() {
        return database;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    Connection connect() throws SQLException {
        DataSource dSource = initDataSource();
        return dSource.getConnection();
    }

    public abstract DataSource initDataSource();
    public abstract ArrayList<Table> initTables(String type);
}
