package com.comparer.core.database;

import java.util.ArrayList;
import java.util.Collections;

public class Table implements Comparable {

    public String name;
    public String type;
    public ArrayList<Column> columns;

    public Table(String name, String type){
        this.columns = new ArrayList<>();
        this.name = name;
        this.type = type;
    }

    public void sortColumns(){
        Collections.sort(columns);
    }

    @Override
    public boolean equals(Object obj) {
        Table other = (Table) obj;
        if(!other.name.equals(name)){
            return false;
        }
        if(!other.type.equals(type)){
            return false;
        }
        if(other.columns.size() != columns.size()){
            return false;
        }
        if(!other.columns.equals(columns)){
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((Table) o).name);
    }
}
