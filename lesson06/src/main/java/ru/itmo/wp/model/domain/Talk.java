package ru.itmo.wp.model.domain;

import java.util.Date;

public class Talk {
    private long id;
    private long sourceId;
    private long targetId;
    private String message;
    private Date creationTime;

    public long getId() {
        return id;
    }

    public long getSourceId() {
        return sourceId;
    }

    public long getTargetId() {
        return targetId;
    }

    public String getMessage() {
        return message;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
