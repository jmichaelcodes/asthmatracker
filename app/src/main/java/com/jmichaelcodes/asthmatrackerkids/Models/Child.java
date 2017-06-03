package com.jmichaelcodes.asthmatrackerkids.Models;

/**
 * Created by docto_000 on 6/21/2016.
 */
public class Child {

    private String childName;
    private String phone;
    private String email;
    private Integer pfmMorningHour;
    private Integer pfmMorningMinute;
    private Integer pfmEveningHour;
    private Integer pfmEveningMinute;

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

    public Integer getPfmMorningHour() {
        return pfmMorningHour;
    }

    public void setPfmMorningHour(Integer pfmMorningHour) {
        this.pfmMorningHour = pfmMorningHour;
    }

    public Integer getPfmMorningMinute() {
        return pfmMorningMinute;
    }

    public void setPfmMorningMinute(Integer pfmMorningMinute) {
        this.pfmMorningMinute = pfmMorningMinute;
    }

    public Integer getPfmEveningHour() {
        return pfmEveningHour;
    }

    public void setPfmEveningHour(Integer pfmEveningHour) {
        this.pfmEveningHour = pfmEveningHour;
    }

    public Integer getPfmEveningMinute() {
        return pfmEveningMinute;
    }

    public void setPfmEveningMinute(Integer pfmEveningMinute) {
        this.pfmEveningMinute = pfmEveningMinute;
    }

    public Child(String childName, String phone, String email, Integer pfmMorningHour, Integer pfmMorningMinute, Integer pfmEveningHour, Integer pfmEveningMinute) {
        this.childName = childName;
        this.phone = phone;
        this.email = email;
        this.pfmMorningHour = pfmMorningHour;
        this.pfmMorningMinute = pfmMorningMinute;
        this.pfmEveningHour = pfmEveningHour;
        this.pfmEveningMinute = pfmEveningMinute;
    }


}
