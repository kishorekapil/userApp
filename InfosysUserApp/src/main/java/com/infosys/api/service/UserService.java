package com.infosys.api.service;

import com.infosys.api.bo.UserBO;
import com.infosys.api.entity.User;
import com.infosys.api.exception.UserNotFoundException;
import com.infosys.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

@CacheEvict(value = "Users",allEntries = true)
    public User saveUser(User userRequest) {
        User user = new User
                (userRequest.getUserId(), userRequest.getName(), userRequest.getAge(), userRequest.getJob(), userRequest.getHeight(), userRequest.getWeight(), userRequest.getDescription() );
        return repository.save(user);
    }

    @Cacheable("Users")
    public User getUser(int id) throws UserNotFoundException {
        User user= repository.findByUserId(id);
        if(user!=null){
            return user;
        }else{
            throw new UserNotFoundException("user not found with id : "+id);
        }
    }
    public List<UserBO> getALlUsers() {
        List<User> users = repository.findAll();
        List<UserBO> userBOs = new ArrayList<>();
        for (User user : users) {
            UserBO bo = new UserBO();
            bo.setName(user.getName());
            bo.setJob(user.getJob());
            bo.setAge(user.getAge());
            userBOs.add(bo);
        }
        return userBOs;
    }
    public User updateUser(User userRequest, int id) {
        return repository.findById(id).map(user -> {
            user.setName(userRequest.getName());
            user.setJob(userRequest.getJob());
            user.setAge(userRequest.getAge());
            user.setHeight(userRequest.getHeight());
            user.setWeight(userRequest.getWeight());
            user.setDescription(userRequest.getDescription());
            return repository.save(user);
        }).orElseGet(() -> {
            userRequest.setUserId(id);
            return repository.save(userRequest);
        });
    }
    public Page<User> findProductsWithPaginationAndSorting(int offset, int pageSize){
        Page<User> user = repository.findAll(PageRequest.of(offset, pageSize));
        return user;
    }
}
