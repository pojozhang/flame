package cn.blinkmind.depot.server.controller;

import cn.blinkmind.depot.server.bean.web.ObjectId;
import cn.blinkmind.depot.server.service.UserService;
import cn.blinkmind.depot.server.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author jiaan.zhang@oracle.com
 * @date 12/10/2016 3:11 PM
 */
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
