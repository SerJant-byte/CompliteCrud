package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.List;

@RequestMapping("/users")
@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getUsersControler(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users",users);
        return "users";
    }

    @PostMapping
    public String addUsersControler(@RequestParam Long id, @RequestParam String name) {
        User user = new User(id, name);
        userService.addUser(user);
        return "redirect:/users/";
    }

    @GetMapping("/{id}/edit")
    public String getUserId (@PathVariable Long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/{id}/edit")
    public String updateUser(@PathVariable Long id, @RequestParam String name) {
        User user = userService.getUser(id);
        user.setName(name);
        userService.updateUser(user);
        return "redirect:/users/";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users/";
    }

}
