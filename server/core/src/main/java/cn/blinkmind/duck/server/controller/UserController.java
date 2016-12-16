package cn.blinkmind.duck.server.controller;

import cn.blinkmind.duck.server.bean.web.ObjectId;
import cn.blinkmind.duck.server.service.UserService;
import cn.blinkmind.duck.server.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ObjectId> create(@RequestBody User user) {
        userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ObjectId(user.getId()));
    }
}