package ru.alxstn.recipe.rest;

import ru.alxstn.recipe.user.User;
import ru.alxstn.recipe.user.UserJsonViews;
import ru.alxstn.recipe.user.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService service) {
        this.userService = service;
    }

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.OK)
    public void register(@JsonView(UserJsonViews.RegistrationView.class)
                         @Valid @RequestBody User user) {
        userService.add(user);
    }
}
