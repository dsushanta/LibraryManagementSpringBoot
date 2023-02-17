package com.bravo.johny.controller;

import com.bravo.johny.controller.filterbeans.UserFilterBean;
import com.bravo.johny.controller.filterbeans.UserIssuedBookFilterBean;
import com.bravo.johny.dto.User;
import com.bravo.johny.dto.UserIssuedBook;
import com.bravo.johny.service.BookIssueService;
import com.bravo.johny.service.UserService;
import com.bravo.johny.utils.CommonUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private BookIssueService bookIssueService;

    private Logger logger;

    public UserController() {
        logger = CommonUtils.getLoggerInstance(this.getClass());
    }

    @GetMapping
    public List<User> getUsers(UserFilterBean filterBean) {

        List<User> users;
        if(filterBean.getLastName() == null) {
            users = userService.getAllUsers(filterBean.getOffset(), filterBean.getLimit());
            logger.info("returning all the Users");
        } else {
            users = userService.getUsers(filterBean.getLastName(), filterBean.getOffset(), filterBean.getLimit());
            logger.info("returning all the Users with last name : "+filterBean.getLastName());
        }

        for(User user : users) {
            user.add(linkTo(methodOn(UserController.class)
                    .getUsers(filterBean))
                    .slash(user.getUserName())
                    .withSelfRel());
        }

        return users;
    }

    @GetMapping ("/{userName}")
    public User getUserDetails(@PathVariable("userName") String userName) {
        User user = userService.getUserDetails(userName);
        user.add(linkTo(methodOn(UserController.class)
                .getUserDetails(userName))
                .withSelfRel());

        return user;
    }

    @PutMapping("/{userName}")
    public User updateUserDetails(@RequestBody User user,
                                  @PathVariable("userName") String userName) {

        user = userService.updateUserDetails(userName, user);
        user.add(linkTo(methodOn(UserController.class)
                .updateUserDetails(user, userName))
                .withSelfRel());

        return user;
    }

    @PostMapping
    public Response addNewUser(@RequestBody User user) {

        User newUser = userService.addNewUser(user);
        WebMvcLinkBuilder userLink = linkTo(methodOn(UserController.class)
                .addNewUser(user))
                .slash(newUser.getUserName());
        newUser.add(userLink.withSelfRel());
        Response response = Response.created(userLink.toUri())
                .entity(newUser)
                .build();

        return response;
    }

    @DeleteMapping("/{userName}")
    public void deleteUser(@PathVariable("userName") String userName) {

        userService.deleteUser(userName);
    }

    @GetMapping("/{userName}/books")
    public List<UserIssuedBook> getUserIssuedBooks(@PathVariable("userName") String userName,
                                                   UserIssuedBookFilterBean bean) {

        return bookIssueService.getListOfBooksIssuedToAUser(userName, bean);
    }
}
