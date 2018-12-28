package com.example.ravneet.vision.Pojo;

public class
ItemDetails {

    String id;
    String mbno;
    String code;
    String name;
    String rno;
    String issueDate;
    String returnDate;
    Boolean returned;

    public ItemDetails(String id, String mbno, String code, String name, String rno, String issueDate, String returnDate, Boolean returned) {
        this.id = id;
        this.mbno = mbno;
        this.code = code;
        this.name = name;
        this.rno = rno;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.returned = returned;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMbno() {
        return mbno;
    }

    public void setMbno(String mbno) {
        this.mbno = mbno;
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

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Boolean getReturned() {
        return returned;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }
}
