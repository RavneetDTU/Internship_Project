package com.example.ravneet.vision.Pojo;

public class ItemDetails {

    String code;
    String name;
    String rno;

    public ItemDetails(String code, String name, String rno) {
        this.code = code;
        this.name = name;
        this.rno = rno;
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
