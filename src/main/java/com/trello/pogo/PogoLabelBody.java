package com.trello.pogo;

public class PogoLabelBody {
    private String name;
    private String color;

    public PogoLabelBody() {
    }

    public PogoLabelBody(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
