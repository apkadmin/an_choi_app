package com.anchoi;


public enum Language
{
    PORTUGUESE("pt"),
    ENGLISH("en"),
    FRENCH("fr"),
    GERMAN("gr"),
    VIETNAM("vi"),
    SPANISH("es");

    String value;

    private Language(String value) {
        this.value = value;
    }

    public String value(){
        return this.value;
    }
}
