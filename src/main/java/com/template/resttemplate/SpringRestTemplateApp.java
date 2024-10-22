package com.template.resttemplate;

import com.template.resttemplate.model.User;
import com.template.resttemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringRestTemplateApp {

    private final UserService userService;

    @Autowired
    public SpringRestTemplateApp(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringRestTemplateApp.class, args);
        SpringRestTemplateApp app = new SpringRestTemplateApp(new UserService());
        app.performUserOperations();
    }

    public void performUserOperations() {
        System.out.println("Все пользователи:");
        userService.getAllUsers().forEach(System.out::println);

        User newUser = new User(3L, "James", "Brown", (byte) 30);
        String part1 = userService.createUser(newUser);
        System.out.println("Первая часть: " + part1);

        newUser.setName("Thomas");
        newUser.setLastName("Shelby");
        String part2 = userService.updateUser(newUser);
        System.out.println("Вторая часть: " + part2);

        String part3 = userService.deleteUser(3L);
        System.out.println("Третья часть: " + part3);

        System.out.println("Итоговый код: " + part1 + part2 + part3);
    }
}