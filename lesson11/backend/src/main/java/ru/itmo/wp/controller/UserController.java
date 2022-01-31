package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.UserRegisterCredentials;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class UserController {
    private final JwtService jwtService;
    private final UserService userService;

    public UserController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping("users/auth")
    public User findUserByJwt(@RequestParam String jwt) {
        return jwtService.find(jwt);
    }

    @GetMapping("users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping("users")
    public String register(@RequestBody @Valid UserRegisterCredentials userRegisterCredentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        userService.register(userRegisterCredentials);

        User user = userService.findByLoginAndPassword(userRegisterCredentials.getLogin(), userRegisterCredentials.getPassword());
        return jwtService.create(user);
    }
}
