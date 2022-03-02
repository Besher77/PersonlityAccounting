package com.qashar.mypersonalaccounting.CountriesCurrency;

public class Currency {
    private String name,shortName;
    private Integer image;

    public Currency(String name,String shortName, Integer image) {
        this.name = name;
        this.shortName = shortName;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}
