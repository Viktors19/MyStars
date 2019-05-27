package lv.vlab.stars.controllers;

import lv.vlab.stars.models.User;
import lv.vlab.stars.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public List<User> all() {
        return (List<User>) userRepository.findAll();
    }

    @PostMapping("")
    public User newUser(@RequestBody User user) {
        Random rand = new Random();
        user.setApiKey(Integer.toHexString(rand.nextInt(900000000) + 1000000000));
        return userRepository.save(user);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable long userId) {
        User user = userRepository.findById(userId).get();
        user.add(linkTo(methodOn(UserController.class).getUser(userId)).withSelfRel());
        user.add(linkTo(methodOn(UserController.class).all()).withRel("catalog"));
        return user;
    }

    @PatchMapping("/{userId}")
    public User editUser(@PathVariable long userId, @RequestBody User user) {
        Random rand = new Random();
        user.setApiKey(Integer.toHexString(rand.nextInt(900000000) + 1000000000));
        user.setUserId(userId);
        return userRepository.save(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        userRepository.deleteById(userId);
    }

}