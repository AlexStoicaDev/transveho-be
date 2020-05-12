package com.example.transvehobe.service;

import com.example.transvehobe.common.Mapper;
import com.example.transvehobe.common.dto.UserDto;
import com.example.transvehobe.entity.role.UserRole;
import com.example.transvehobe.entity.user.User;
import com.example.transvehobe.entity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    //TODO create custom exceptions
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllDrivers() {
        return userRepository.findAllByRole(UserRole.DRIVER);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //TODO fix bug, fe receives result even with error
    public User updateUser(Long id, UserDto userDto) {
        final Optional<User> byUsername = userRepository.findById(id);
        if (byUsername.isPresent()) {
            userRepository.save(Mapper.updateUser(byUsername.get(), userDto));
            return userRepository.findById(id)
                                 .orElseThrow(() -> new EntityNotFoundException("driver with" + userDto.getUsername() + " was not found"));
        } else {
            throw new EntityNotFoundException("driver with" + userDto.getUsername() + " was not found");
        }
    }

    public User createUser(UserDto userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        User driver = Mapper.mapUserDtoToUserEntity(userDto);
        userRepository.save(driver);
        return driver;
    }

    //TODO change logic to a more safe one, also remove drriver from assigned trips if necessary
    @Transactional
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

}
