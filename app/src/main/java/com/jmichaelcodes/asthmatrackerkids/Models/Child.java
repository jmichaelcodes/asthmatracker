package com.jmichaelcodes.asthmatrackerkids.Models;

/**
 * Created by docto_000 on 6/21/2016.
 */
public class Child {

    private String childName;
    private String phone;
    private String email;
    private Integer dailyCheckIns;

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getDailyCheckIns() {
        return dailyCheckIns;
    }

    public void setDailyCheckIns(Integer dailyCheckIns) {
        this.dailyCheckIns = dailyCheckIns;
    }
//    public ArrayList<Child> children = new ArrayList<>();

    public Child(String childName, String phone, String email) {
        this.childName = childName;
        this.phone = phone;
        this.email = email;
        this.dailyCheckIns = dailyCheckIns;
    }

}
