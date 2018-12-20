package com.example.ravneet.vision.Pojo;

public class
ItemDetails {

    String code;
    String name;
    String rno;
    String date;
    Boolean returned;

    public ItemDetails(String code, String name, String rno, String date, Boolean returned) {
        this.code = code;
        this.name = name;
        this.rno = rno;
        this.date = date;
        this.returned = returned;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        date = date;
    }

    public Boolean getReturned() {
        return returned;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRno() {
        return rno;
    }

    public void setRno(String rno) {
        this.rno = rno;
    }
}
