package com.jmichaelcodes.asthmatrackerkids.Models;

import java.util.ArrayList;

/**
 * Created by docto_000 on 6/22/2016.
 */
public class Parent {

    private Child child;
    private String email;
    private String phone;

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
