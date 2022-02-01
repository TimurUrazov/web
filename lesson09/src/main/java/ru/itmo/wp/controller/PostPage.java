package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.form.validator.CommentValidator;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;
    private final CommentValidator commentValidator;

    public PostPage(PostService postService, CommentValidator commentValidator) {
        this.postService = postService;
        this.commentValidator = commentValidator;
    }

    @InitBinder("comment")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(commentValidator);
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @PostMapping("/post/{id}")
    public String writeComment(@PathVariable long id,
                               @Valid @ModelAttribute("comment") Comment comment,
                               BindingResult bindingResult,
                               HttpSession httpSession,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", postService.find(id));
            model.addAttribute("user", getUser(httpSession));
            model.addAttribute("comment", comment);
            return "PostPage";
        }

//        StringBuilder line = new StringBuilder();
//        for (String currentLine : comment.getText().split("\n")) {
//            line.append(" <br> ").append(currentLine);
//        }
//        comment.setText(line.toString());

        postService.writeComment(getUser(httpSession), comment, postService.find(id));
        putMessage(httpSession, "You commented on the post");

        return "redirect:/post/" + id;
    }

    @Guest
    @GetMapping("/post/{id}")
    public String post(Model model, @PathVariable("id") String id) {
        try {
            long parsedId = Long.parseLong(id);
            model.addAttribute("post", postService.find(parsedId));
            model.addAttribute("comment", new Comment());
        } catch (NumberFormatException ignored) {
        }
        return "PostPage";
    }
}


