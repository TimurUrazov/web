package ru.itmo.wp.lesson8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.lesson8.form.UserDisabledUpdate;
import ru.itmo.wp.lesson8.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @PostMapping("/users/all")
    public String updateDisabled(@Valid UserDisabledUpdate userDisabledUpdate,
                               BindingResult bindingResult,
                               HttpSession httpSession,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            return "UsersPage";
        }

        if (userService.findById(userDisabledUpdate.getSourceId()).isDisabled()) {
            unsetUser(httpSession);
            setMessage(httpSession, "You have been disabled");
        }

        userService.updateHidden(userDisabledUpdate.getId(), userDisabledUpdate.isDisabled());

        if (userDisabledUpdate.getId() == getUser(httpSession).getId() && userDisabledUpdate.isDisabled()) {
            return "redirect:/logout";
        }

        setMessage(httpSession, "You " + (userDisabledUpdate.isDisabled() ? "disabled " : "enabled ")
                + userService.findById(userDisabledUpdate.getId()).getLogin());

        return "redirect:/users/all";
    }
}
