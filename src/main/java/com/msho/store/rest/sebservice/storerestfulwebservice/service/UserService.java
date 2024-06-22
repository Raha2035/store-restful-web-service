package com.msho.store.rest.sebservice.storerestfulwebservice.service;

import com.msho.store.rest.sebservice.storerestfulwebservice.exception.ItemNotFoundException;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.User;
import com.msho.store.rest.sebservice.storerestfulwebservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers(){
        List<User> users = userRepository.findAll();
        if (users.isEmpty()){
            throw new ItemNotFoundException("No users found");
        }
        return users;
    }

    public User getSpecificUser(int id){
        Optional<User> user;
        user = userRepository.findById(id);
        user.orElseThrow();
        return user.orElse(null);
    }

    public void deleteSpecificUser(int id){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new ItemNotFoundException("User not found with id: " + id);
        userRepository.delete(user.get());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new ItemNotFoundException("User not found"));
    }
}
