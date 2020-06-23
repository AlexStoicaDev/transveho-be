package com.example.transvehobe.rest;

import com.example.transvehobe.authentication.response.MessageResponse;
import com.example.transvehobe.common.dto.UserDto;
import com.example.transvehobe.common.projection.CustomProjectionFactory;
import com.example.transvehobe.common.projection.UserProjection;
import com.example.transvehobe.entity.user.User;
import com.example.transvehobe.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transveho/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UsersController {

    private final UsersService usersService;
    private final CustomProjectionFactory factory;

    //TODO move exception throwing to either controller or service
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER') or hasAuthority('DRIVER')")
    public UserProjection getUserById(@PathVariable(value = "id") Long id) {
        User user = usersService.getUserById(id).orElseThrow(() -> new EntityNotFoundException("user with id " + id + " was not found"));
        return factory.createProjection(UserProjection.class, user);
    }

    @GetMapping("/find/{username}")
    @PreAuthorize("hasAuthority('ADMIN') or #username == authentication.name")
    public UserProjection getUserByUsername(@PathVariable(value = "username") String username) {
        User user = usersService.getUserByUsername(username)
                                .orElseThrow(() -> new EntityNotFoundException("user with username " + username + " was not found"));
        return factory.createProjection(UserProjection.class, user);
    }

    @GetMapping("/drivers")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public List<UserProjection> getAllDrivers() {
        return usersService.getAllDrivers()
                           .stream()
                           .map(user -> factory.createProjection(UserProjection.class, user))
                           .collect(Collectors.toList());
    }

    @GetMapping("/dispatchers")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public List<UserProjection> getAllDispatchers() {
        return usersService.getAllDispatchers()
                           .stream()
                           .map(user -> factory.createProjection(UserProjection.class, user))
                           .collect(Collectors.toList());
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public List<UserProjection> getAllUsers() {
        return usersService.getAllUsers()
                           .stream()
                           .map(user -> factory.createProjection(UserProjection.class, user))
                           .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or #username == authentication.name")
    public UserProjection updateUser(@PathVariable(value = "id") Long id,
                                     @RequestParam(value = "username") String username,
                                     @RequestBody UserDto userDto) {
        return factory.createProjection(UserProjection.class, usersService.updateUser(id, userDto));
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        if (usersService.findUserByUsernameOrEmail(userDto.getUsername(), userDto.getEmail()).isPresent()) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Email/Username is already in use!"));
        }
        User user = usersService.createUser(userDto);
        return ResponseEntity.ok(factory.createProjection(UserProjection.class, user));
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasAuthority('ADMIN') or #username == authentication.name")
    public void deleteUser(@PathVariable(value = "username") String username) {
        usersService.deleteUser(username);
    }
}
