package com.app.controller;

import com.app.models.User;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Class UserController
 * REST Controller that handles all user request
 *
 * Created by Kulinenko Roman
 */
@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Page<User> getUsers(HttpServletRequest httpServletRequest,
            @RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) throws Exception {
        return userService.getUsers(page, perPage);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addUser(HttpServletRequest httpServletRequest, @RequestBody User user) throws Exception {
        userService.insertUser(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUser(HttpServletRequest httpServletRequest, @PathVariable(value = "id") Integer id) throws Exception {
        userService.deleteUserById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateUser(HttpServletRequest httpServletRequest, @RequestBody User user) throws Exception {
        userService.updateUser(user);
    }

}
