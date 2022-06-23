package com.infosys.api.controller;

import com.infosys.api.bo.UserBO;
import com.infosys.api.dto.APIResponse;
import com.infosys.api.exception.UserNotFoundException;
import com.infosys.api.service.UserService;
import com.infosys.api.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/signup")
    public ResponseEntity<User> saveUser(@RequestBody @Valid User userRequest){
        return new ResponseEntity<>(service.saveUser(userRequest), HttpStatus.CREATED);
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<UserBO>> getAllUsers(){
        return ResponseEntity.ok(service.getALlUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) throws UserNotFoundException {
        return ResponseEntity.ok(service.getUser(id));
    }
    @PutMapping("/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable int id) {
        return service.updateUser(newUser, id);
    }
    @GetMapping("/Userpagination/{offset}/{pageSize}")
    private APIResponse<Page<User>> getProductsWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<User> productsWithPagination = service.findProductsWithPaginationAndSorting(offset,pageSize);
        return new APIResponse<>(productsWithPagination.getSize(), productsWithPagination);
    }
}

