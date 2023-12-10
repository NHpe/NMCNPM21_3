package com.group18.dormitory.Model;

public class Date {
    private int day;
    private int month;
    private int year;

    public Date() {

    }

    public Date(String date) {
        String[] string_split = date.split("/");
        day = Integer.parseInt(string_split[0]);
        month = Integer.parseInt(string_split[1]);
        year = Integer.parseInt(string_split[2]);
    }

    public int getDay() {return day;}

    public void setDay(int day) {this.day = day; }

    public int getMonth() {return month;}

    public void setMonth(int month) {this.month = month;}

    public int getYear() {return year;}

    public void setYear(int year) {this.year = year;}

    @Override
    public String toString() {
        return day + "/" + month + "/" + year;
    }
}
