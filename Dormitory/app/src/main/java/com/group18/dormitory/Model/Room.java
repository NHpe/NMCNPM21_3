package com.group18.dormitory.Model;

public class Room {
    private String id;
    private String type;
    private float cost;
    private String condition;
    private String[] furniture;

    public Room() {

    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public float getCost() {return cost;}

    public void setCost(float cost) {this.cost = cost;}

    public String getCondition() {return condition;}

    public void setCondition(String condition) {this.condition = condition;}

    public String[] getFurniture() {return furniture;}

    public void setFurniture(String[] furniture) {this.furniture = furniture;}
}
