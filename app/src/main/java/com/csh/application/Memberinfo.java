package com.csh.application;

public class Memberinfo {
    private String id;
    private String username;
    private String uni;
    private String number;
    private String imageURL;
    private String status;
    private String search;

    public Memberinfo(String id, String name, String uni, String number, String imageURL, String status, String search){
        this.id = id;
        this.username = name;
        this.uni = uni;
        this.number = number;
        this.imageURL = imageURL;
        this.status = status;
        this.search = search;
    }
    public Memberinfo() {

    }

    public String getId() {
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUni(){
        return uni;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }

    public String getNumber(){
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImageURL(){
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSearch(){
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
