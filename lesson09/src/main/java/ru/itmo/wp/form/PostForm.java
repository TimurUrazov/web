package ru.itmo.wp.form;

import com.sun.istack.NotNull;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PostForm {
    @NotEmpty
    @Size(min = 1, max = 60)
    private String title;

    @NotNull
    @Size(max = 200)
    @Pattern(regexp = "([a-zA-Z\\s])*", message = "latin letters expected")
    private String tags;

    @NotEmpty
    @Size(min = 1, max = 65000)
    @Lob
    private String text;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
