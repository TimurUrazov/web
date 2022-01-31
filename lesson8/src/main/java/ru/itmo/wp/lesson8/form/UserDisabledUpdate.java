package ru.itmo.wp.lesson8.form;

import javax.validation.constraints.Positive;

@SuppressWarnings("unused")
public class UserDisabledUpdate {
    @Positive
    private long sourceId;

    @Positive
    private long id;

    private boolean disabled;

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
