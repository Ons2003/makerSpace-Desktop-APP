package com.example.ya;

import java.time.LocalDateTime;

public class Instant {
    public int year;
    public int month;
    public int day;
    public int hour;
    public int minute;
    public int second;

    public Instant(Instant instant){
        this.year = instant.year;
        this.month = instant.month;
        this.day = instant.day;
        this.hour = instant.hour;
        this.minute = instant.minute;
        this.second = instant.second;
    }

    public Instant(int year, int month, int day, int hour, int minute, int second){
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public Instant(int hour, int minute, int second){
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public Instant(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }

    public Instant(int month, int day, int hour, int minute, int second){
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public Instant(int month, int day, int hour, int minute){
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public String toString(){
        return (hour>9?hour : "0"+  hour) + " : " + (minute > 9? minute : "0" + minute);
    }

    public String Encode(){
        return year + ";" + month + ";" + day + ";" + hour + ";" + minute + ";" + second;
    }

    public static Instant Decode(String code){
        String[] dateComponents = code.split(";");
        for(int i = 0;i<dateComponents.length;i++){
            System.out.println("]"+ dateComponents[i]+"[");
        }
        int year = Integer.parseInt(dateComponents[0]);
        int month = Integer.parseInt(dateComponents[1]);
        int day = Integer.parseInt(dateComponents[2]);
        int hour = Integer.parseInt(dateComponents[3]);
        int minute = Integer.parseInt(dateComponents[4]);
        int second = Integer.parseInt(dateComponents[5]);
        return new Instant(year, month, day, hour, minute, second);
    }

    public static boolean IsEqual(Instant a, Instant b){
        return a.year == b.year && a.month == b.month && a.day == b.day && a.hour == b.hour && a.minute == b.minute && a.second == b.second;
    }

    public static boolean IsSoonerThan(Instant a, Instant b){
        if(a.year == b.year){
            if(a.month == b.month){
                if(a.day == b.day){
                    if(a.hour == b.hour){
                        if(a.minute == b.minute){
                            return a.second <= b.second;
                        }else return a.minute < b.minute;
                    }else return a.hour < b.hour;
                }else return a.day < b.day;
            }else return a.month < b.month;
        }else return a.year < b.year;
    }

    public static Instant GetCurrentTimeHM(){ //hour minute
        LocalDateTime now = LocalDateTime.now();
        return new Instant(now.getMonthValue(), now.getDayOfMonth(), now.getHour(), now.getMinute());
    }
}
