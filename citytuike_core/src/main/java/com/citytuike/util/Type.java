package com.citytuike.util;

public enum Type {
    text,
    image,
    sound;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
