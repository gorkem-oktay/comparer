package com.comparer.core.database;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DatabaseFactory {

    public static IDatabase init(String filePath) throws IOException, UnsupportedOperationException {
        File file = new File(filePath);
        String fileAsString = new String(Files.readAllBytes(file.toPath()));
        JSONObject json = new JSONObject(fileAsString);

        String dbType = json.getString("type");

        if(dbType.equals("mysql")){
            return new MySQLDB(json);
        } else if (dbType.equals("mssql")) {
            return new MSSQLDB(json);
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
