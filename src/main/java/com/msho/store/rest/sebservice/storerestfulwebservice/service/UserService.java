package com.msho.store.rest.sebservice.storerestfulwebservice.service;

import com.msho.store.rest.sebservice.storerestfulwebservice.exception.ItemNotFoundException;
import com.msho.store.rest.sebservice.storerestfulwebservice.model.User;
import com.msho.store.rest.sebservice.storerestfulwebservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
*
* Service class for managing users.
*
* */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    /*
    * Get all users .
    *
    * @return a list of all users
    * @throws ItemNotFoundException if no users are found
    * */
    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();
        if (users.isEmpty()){
            throw new ItemNotFoundException("No users found");
        }
        return users;
    }

    /*
    * Get a specific user by ID
    *
    * @param id the user ID
    * @return the user with the specified ID
    * @throws ItemNotFoundException if the user is not found
    * */
    public User getUserById(int id){
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ItemNotFoundException("User not found with id:" + id));
    }

    /*
    * Delete a specific user by ID.
    *
    * @param id the user ID
    * @Throws ItemNotFoundException if the user is
    * */
    public void deleteUserById(int id){
        User user = this.getUserById(id);
        userRepository.delete(user);
    }

    /*
    * Load a user by username.
    *
    * @param username the username
    * @return the user details
    * @throws UsernameNotFoundException if the user is not found
    * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new ItemNotFoundException("User not found"));
    }
}
