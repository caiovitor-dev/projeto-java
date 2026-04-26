package com.caio.financial.controller;

import com.caio.financial.dto.UserCreateDTO;
import com.caio.financial.entity.User;
import com.caio.financial.mapper.UserMapper;
import com.caio.financial.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @PostMapping
    public ResponseEntity<Void> save(@RequestBody UserCreateDTO dto){
        User user = userMapper.toEntity(dto);
        userService.saveUser(user,dto.rolesId());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{userId}/{roleId}")
    public ResponseEntity<?> removeRoleUser(@PathVariable UUID userId,@PathVariable UUID roleId){

        return userService.getUserById(userId).map(user -> {

            userService.removeRoleFromUser(user,roleId);
            return ResponseEntity.noContent().build();

        }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeUser(@PathVariable UUID id){

        return userService.getUserById(id).map(user -> {

            userService.deleteUser(user);
            return ResponseEntity.noContent().build();

        }).orElseGet(() -> ResponseEntity.notFound().build());

    }
}
