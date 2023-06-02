package model;

import java.util.ArrayList;

public class Station {
    private String name;
    private int id;
    private ArrayList<String> lines;

    public Station(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getLines() {
        return lines;
    }

    public void setLines(ArrayList<String> lines) {
        this.lines = lines;
    }

    public String toString(){
        return name;
    }

    @Override
    public boolean equals(Object obj){
        if(this==obj) return true;
        if(obj==null || getClass() != obj.getClass()) return false;
        Station station = (Station) obj;
        return (id == station.id && name.equalsIgnoreCase(station.name));

    }
}
