package com.group18.dormitory.Model;

import java.util.HashMap;
import java.util.Map;

public class Room {
    private static Room instance;
    private String id;
    private String type;
    private float cost;
    private String condition;
    private Map<String, Integer> furniture;

    public static Room getInstance() {
        if(instance == null) {
            instance = new Room();
        }
        return instance;
    }

    public Room() {
        furniture = new HashMap<String, Integer>();
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public float getCost() {return cost;}

    public void setCost(float cost) {this.cost = cost;}

    public String getCondition() {return condition;}

    public void setCondition(String condition) {this.condition = condition;}

    public Map<String, Integer> getFurniture() {return furniture;}

    public void setFurniture(Map<String, Integer> furniture) {this.furniture = furniture;}

    public void addFurniture(String str, Integer integer) {
        furniture.put(str, integer);
    }

    public void removeFurniture(String str) {
        furniture.remove(str);
    }
}
