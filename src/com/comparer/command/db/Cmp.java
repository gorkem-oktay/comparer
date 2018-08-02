package com.comparer.command.db;

import com.comparer.core.database.Column;
import com.comparer.core.database.ExtraTable;
import com.comparer.core.database.Table;
import com.comparer.core.database.TablePair;
import com.comparer.core.view.Html;
import com.comparer.view.Style;

import java.io.IOException;
import java.util.ArrayList;

public class Cmp extends IDBCommand {

    private ArrayList<TablePair> changedTables = new ArrayList<>();
    private ArrayList<ExtraTable> addedTables = new ArrayList<>();

    public Cmp(String command) {
        super(command);
    }

    @Override
    public void process() {
        String option = "all";
        if (options.size() > 0) {
            option = options.get(0);
        }

        ArrayList<Table> tables1 = db1.initTables(option);
        ArrayList<Table> tables2 = db2.initTables(option);

        for (Table t1 : tables1) {
            boolean found = false;
            for (Table t2 : tables2) {
                if (t1.name.equalsIgnoreCase(t2.name)) {
                    found = true;
                    if (!t1.equals(t2)) {
                        for (Column col1 : t1.columns) {
                            boolean cFound = false;
                            for (Column col2 : t2.columns) {
                                if (col1.name.equalsIgnoreCase(col2.name)) {
                                    cFound = true;
                                    if (!col1.equals(col2)) {
                                        col1.status = 1;
                                        col2.status = 1;
                                    }
                                }
                            }
                            if (!cFound) {
                                col1.status = 1;
                            }
                        }
                        for (Column col2 : t2.columns) {
                            boolean cFound = false;
                            for (Column col1 : t1.columns) {
                                if (col2.name.equalsIgnoreCase(col1.name)) {
                                    cFound = true;
                                }
                            }
                            if (!cFound) {
                                col2.status = 2;
                            }
                        }
                        changedTables.add(new TablePair(t1, t2));
                    }
                    break;
                }
            }
            if (!found) {
                addedTables.add(new ExtraTable(t1, 1));
            }
        }
        for (Table t2 : tables2) {
            boolean found = false;
            for (Table t1 : tables1) {
                if(t2.name.equalsIgnoreCase(t1.name)){
                    found = true;
                    break;
                }
            }
            if(!found){
                addedTables.add(new ExtraTable(t2, 2));
            }
        }
    }

    @Override
    public void print() {
        if(options.contains("-of")){
            try {
                String view = Html.parseDBResult(changedTables, addedTables, db1.getDatabase(), db2.getDatabase());
                Html.save(view);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String db1Extras = "";
            String db2Extras = "";
            for (ExtraTable t: addedTables){
                if(t.database == 1){
                    db1Extras += t.table.name + " (" + t.table.type + ")\n";
                } else {
                    db2Extras += t.table.name + " (" + t.table.type + ")\n";
                }
            }

            System.out.println("\nExtra tables and views");
            System.out.println(Style.SEPERATOR);
            System.out.println("Database: " + db1.getDatabase());
            System.out.println(db1Extras.equalsIgnoreCase("") ? "There isn't any extra table or view" : db1Extras);
            System.out.println();
            System.out.println("Database: " + db2.getDatabase());
            System.out.println(db1Extras.equalsIgnoreCase("") ? "There isn't any extra table or view" : db2Extras);

            System.out.println("\n\nChanged tables and views");
            System.out.println(Style.SEPERATOR);
            for (TablePair tp: changedTables) {
                System.out.println("\n\nTable: " + tp.table1.name + "()");
                System.out.println(Style.SEPERATOR);
                System.out.println("Database: " + db1.getDatabase());
                for (Column c: tp.table1.columns) {
                    String status = "-";
                    if(c.status == 1){
                        status = "changed";
                    } else if (c.status == 2) {
                        status = "added";
                    }
                    System.out.println(Style.ROW
                            .replace("@name", c.name)
                            .replace("@type", c.type)
                            .replace("@size", String.valueOf(c.size))
                            .replace("@nullable", String.valueOf(c.nullable))
                            .replace("@status", status));
                }
                System.out.println("\nDatabase: " + db2.getDatabase());
                for (Column c: tp.table2.columns) {
                    String status = "-";
                    if(c.status == 1){
                        status = "changed";
                    } else if (c.status == 2) {
                        status = "added";
                    }
                    System.out.println(Style.ROW
                            .replace("@name", c.name)
                            .replace("@type", c.type)
                            .replace("@size", String.valueOf(c.size))
                            .replace("@nullable", String.valueOf(c.nullable))
                            .replace("@status", status));
                }
            }
        }
    }
}
