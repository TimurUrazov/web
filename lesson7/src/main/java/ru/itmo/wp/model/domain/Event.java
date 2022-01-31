package ru.itmo.wp.model.domain;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {
    private enum Type {
        ENTER, LOGOUT
    }

    private long id;
    private long userId;
    Type type;
    private Date creationTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getType() {
        return (type == Type.ENTER ? "ENTER" : "LOGOUT");
    }

    public void setType(String type) {
        this.type = ("ENTER".equals(type) ? Type.ENTER : Type.LOGOUT);
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
