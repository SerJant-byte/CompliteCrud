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
    public String addUsersControler( @RequestParam String name) {
        User user = new User(name);
        userService.addUser(user);
        return "redirect:/users/";
    }
    //переделал, Id приходит как RequestParam
    @GetMapping("/edit")
    public String getUserId (@RequestParam Long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "user";
    }
//переделал, Id приходит как RequestParam
    @PostMapping("/edit")
    public String updateUser(@RequestParam Long id, @RequestParam String name) {
        User user = userService.getUser(id);
        user.setName(name);
        userService.updateUser(user);
        return "redirect:/users/";
    }
//переделал, Id приходит как RequestParam
    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/users/";
    }
// как я понял, теперь пользователю в браузере не нужно вводить id, но браузер сам отправляет на сервер его
}
