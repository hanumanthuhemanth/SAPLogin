package com.example.heman.wizardapp.dashboard.home;

import android.content.Intent;

import java.io.Serializable;

public class HomeBean implements Serializable {
    private String name;
    private int imageResource;
    private Intent intent;

    public HomeBean() {
    }

    public HomeBean(String name, int imageResource, Intent intent) {
        this.name = name;
        this.imageResource = imageResource;
        this.intent = intent;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }


    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
