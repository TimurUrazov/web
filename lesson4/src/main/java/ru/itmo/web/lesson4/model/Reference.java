package ru.itmo.web.lesson4.model;

public class Reference {
    private final String href;
    private final String name;

    public Reference(String href, String name) {
        this.href = href;
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public String getName() {
        return name;
    }
}
