package com.comparer.core.database;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.json.JSONObject;

import javax.sql.DataSource;
import java.util.ArrayList;

public class MSSQLDB extends IDatabase {

    MSSQLDB(JSONObject json) {
        super(json);
    }

    @Override
    public DataSource initDataSource() {
        SQLServerDataSource dSource = new SQLServerDataSource();
        dSource.setServerName(getHost());
        dSource.setPortNumber(port);
        dSource.setDatabaseName(getDatabase());
        dSource.setUser(getUsername());
        dSource.setPassword(getPassword());
        return dSource;
    }

    @Override
    public ArrayList<Table> initTables(String type) {
        return null;
    }
}
