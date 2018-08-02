package com.comparer.core.database;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.json.JSONObject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class MySQLDB extends IDatabase {

    MySQLDB(JSONObject json) {
        super(json);
    }

    @Override
    public DataSource initDataSource() {
        MysqlDataSource dSource = new MysqlDataSource();
        dSource.setServerName(getHost());
        dSource.setPortNumber(port);
        dSource.setDatabaseName(getDatabase());
        dSource.setUser(getUsername());
        dSource.setPassword(getPassword());
        return dSource;
    }

    @Override
    public ArrayList<Table> initTables(String type) {
        ArrayList<Table> tables = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        try {
            con = connect();
            DatabaseMetaData md = con.getMetaData();
            rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                Table tbl = new Table(rs.getString("TABLE_NAME"), rs.getString("TABLE_TYPE"));
                if(!type.equals("all") || tbl.type.equalsIgnoreCase(type)){
                    continue;
                }
                ResultSet rset = md.getColumns(null, null, tbl.name, null);
                while(rset.next()){
                    Column col = new Column();
                    col.name = rset.getString("COLUMN_NAME");
                    col.type = rset.getString("TYPE_NAME");
                    col.nullable = rset.getInt("NULLABLE");
                    col.size = rset.getInt("COLUMN_SIZE");
                    tbl.columns.add(col);
                }
                tbl.sortColumns();
                tables.add(tbl);
                rset.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(con != null){
                    rs.close();
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tables;
    }
}
