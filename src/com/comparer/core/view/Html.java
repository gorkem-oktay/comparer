package com.comparer.core.view;

import com.comparer.core.database.Column;
import com.comparer.core.database.ExtraTable;
import com.comparer.core.database.TablePair;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Html {

    private static final HashMap<Component, String> pathes;

    static {
        pathes = new HashMap<>();
        pathes.put(Component.DATABASE_RESULT, "style/database/result.html");
        pathes.put(Component.DATABASE_ROW, "style/database/row.html");
        pathes.put(Component.DATABASE_CHANGED_TABLE, "style/database/table_changed.html");
        pathes.put(Component.DATABASE_ADDED_TABLE, "style/database/table_added.html");
    }

    public static final String COLUMN_ADDED = "bg-danger";
    public static final String COLUMN_CHANGED = "bg-warning";

    public static String get(Component comp) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(pathes.get(comp)));
        return new String(encoded, "utf-8");
    }

    public static String parseDBResult(ArrayList<TablePair> changed, ArrayList<ExtraTable> added, String db1, String db2) throws IOException {
        String template = get(Component.DATABASE_RESULT);
        String changedTables = "";
        for (TablePair tp : changed) {
            String rows1 = "";
            String rows2 = "";
            for (Column col : tp.table1.columns) {
                rows1 += parseDBRow(col);
            }
            for (Column col : tp.table2.columns) {
                rows2 += parseDBRow(col);
            }

            changedTables += parseDBChangedTable(tp.table1.name, tp.table1.type, db1, db2, rows1, rows2);
        }

        String addedTables1 = "";
        String addedTables2 = "";

        for (ExtraTable et : added) {
            String rows = "";
            for (Column col : et.table.columns) {
                rows += parseDBRow(col);
            }

            if (et.database == 1) {
                addedTables1 += parseDBAddedTable(et.table.name, rows);
            } else {
                addedTables2 += parseDBAddedTable(et.table.name, rows);
            }
        }

        if(addedTables1.equalsIgnoreCase("")){ addedTables1 = "There isn't any table added"; }
        if(addedTables2.equalsIgnoreCase("")){ addedTables2 = "There isn't any table added"; }

        String result = template
                .replace("@added_tables_database_name1", db1)
                .replace("@added_tables_database_name2", db2)
                .replace("@addedTables1", addedTables1)
                .replace("@addedTables2", addedTables2)
                .replace("@changeTables", changedTables);

        return result;
    }

    private static String parseDBRow(Column col) throws IOException {
        String template = get(Component.DATABASE_ROW);
        String bgStyle = "";
        if(col.status == 1){
            bgStyle = Html.COLUMN_CHANGED;
        } else if (col.status == 2) {
            bgStyle = Html.COLUMN_ADDED;
        }
        String row = template
                .replace("@column", col.name)
                .replace("@type", col.type)
                .replace("@size", String.valueOf(col.size))
                .replace("@nullable", String.valueOf(col.nullable))
                .replace("@style", bgStyle);
        return row;
    }

    private static String parseDBChangedTable(String table, String type, String db1, String db2, String rows1, String rows2) throws IOException {
        String template = get(Component.DATABASE_CHANGED_TABLE);
        String tables = template
                .replace("@table_name", table)
                .replace("@table_type", type)
                .replace("@database_name1", db1)
                .replace("@database_name2", db2)
                .replace("@rows1", rows1)
                .replace("@rows2", rows2);
        return tables;
    }

    private static String parseDBAddedTable(String tableName, String rows) throws IOException {
        String template = get(Component.DATABASE_ADDED_TABLE);
        String table = template
                .replace("@table_name", tableName)
                .replace("@rows", rows);
        return table;
    }

    public static void save(String view) throws IOException {
        File dir = new File("results");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File("results/result.html");
        FileUtils.writeStringToFile(file, view, Charset.defaultCharset());
        System.out.println("Results saved to file at " + file.getAbsolutePath());
    }
}
