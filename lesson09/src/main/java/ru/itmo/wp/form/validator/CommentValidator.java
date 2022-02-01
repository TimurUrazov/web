package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.domain.Comment;

@Component
public class CommentValidator implements Validator {
    public boolean supports(Class<?> clazz) {
        return Comment.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            Comment comment = (Comment) target;
            if (comment.getText().trim().isEmpty() || comment.getText() == null) {
                errors.rejectValue("text", "text.null-or-empty", "message should contain at least on non-space symbol");
            }
        }
    }
}
