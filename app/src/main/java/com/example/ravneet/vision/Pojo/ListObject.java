package com.example.ravneet.vision.Pojo;

public class ListObject {

    public String getHeading() {

        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public ListObject(String uid,String heading) {

        this.heading = heading;
        this.uid = uid;
    }

    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String heading;

}
