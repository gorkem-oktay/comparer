package com.comparer.core.database;

public class Column implements Comparable {

    public String name;
    public String type;
    public int size;
    public int nullable;
    /*
    0 -> normal
    1 -> changed
    2 -> added
     */
    public int status = 0;

    public Column() {
    }

    @Override
    public boolean equals(Object obj) {
        Column other = (Column) obj;
        if (other.name.equals(name)
                && other.type.equals(type)
                && other.size == size
                && other.nullable == nullable) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((Column) o).name);
    }
}
