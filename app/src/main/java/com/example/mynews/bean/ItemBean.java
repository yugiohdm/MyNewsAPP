package com.example.mynews.bean;

import java.io.Serializable;

public class ItemBean  implements Serializable {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
