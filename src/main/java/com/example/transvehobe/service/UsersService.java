package com.example.transvehobe.service;

import com.example.transvehobe.common.dto.UserDto;
import com.example.transvehobe.common.enums.UserStatus;
import com.example.transvehobe.common.mappers.UserMapper;
import com.example.transvehobe.entity.role.UserRole;
import com.example.transvehobe.entity.user.User;
import com.example.transvehobe.entity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static org.springframework.beans.TypeMismatchException.ERROR_CODE;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;

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
        final List<User> allDriversByRole = userRepository.findAllByRole(UserRole.DRIVER);
        if(allDriversByRole.size()>0){
            allDriversByRole.sort((a,b)->b.getUserStatus().compareTo(a.getUserStatus()));
            return allDriversByRole;
        }
        return new ArrayList<>();
    }

    public List<User> getAllAvailableDrivers() {
        return userRepository.findAllByRoleAndUserStatus(UserRole.DRIVER, UserStatus.Available);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAllDispatchers() {
        final List<User> allByRole = userRepository.findAllByRole(UserRole.DISPATCHER);
        if(allByRole.size()>0){
            allByRole.sort((a,b)->b.getUserStatus().compareTo(a.getUserStatus()));
            return allByRole;
        }
        return new ArrayList<>();
    }

    public User createUser(UserDto userDto) {
        User newUser = UserMapper.mapUserDtoToUserEntity(new User(), userDto);
        String userPassword = generatePassayPassword();
        newUser.setPassword(passwordEncoder.encode(userPassword));
        userRepository.save(newUser);
        final User userFromDb = userRepository.findByUsername(newUser.getUsername()).orElseThrow(() -> new EntityNotFoundException(
                                            "user with username: " + userDto.getUsername() + " was not found in db"));
        emailService.sendNewAccountEmail(newUser.getEmail(), newUser.getUsername(), newUser.getFirstName(), userPassword);
        return userFromDb;
    }

    private String generatePassayPassword() {
        PasswordGenerator passwordGenerator =
            new PasswordGenerator();
        return passwordGenerator
            .generatePassword(10,
                getSpecialCharactersRule(),
                getLowerCaseRule(),
                getUpperCaseRule(),
                getDigitRule());
    }

    private CharacterRule getSpecialCharactersRule() {
        CharacterData specialChars =
            new CharacterData() {
            public String getErrorCode() {
                return ERROR_CODE;
            }
            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule =
            new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);
        return splCharRule;
    }

    private CharacterRule getLowerCaseRule() {
        CharacterData lowerCaseChars =
            EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule =
            new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);
        return lowerCaseRule;
    }

    private CharacterRule getUpperCaseRule() {
        CharacterData upperCaseChars =
            EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule =
            new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);
        return upperCaseRule;
    }

    private CharacterRule getDigitRule() {
        CharacterData digitChars =
            EnglishCharacterData.Digit;
        CharacterRule digitRule =
            new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);
        return digitRule;
    }

    //TODO fix bug, fe receives result even with error
    public User updateUser(Long id, UserDto userDto) {
        final Optional<User> byUsername = userRepository.findById(id);
        if (byUsername.isPresent()) {
            userRepository.save(UserMapper.mapUserDtoToUserEntity(byUsername.get(), userDto));
            return userRepository.findById(id)
                                 .orElseThrow(() -> new EntityNotFoundException(
                                     "user with username:" + userDto.getUsername() + " was not found in db"));
        } else {
            throw new EntityNotFoundException("driver with" + userDto.getUsername() + " was not found");
        }
    }

    //TODO change logic to a more safe one, also remove drriver from assigned trips if necessary
    @Transactional
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }


}
