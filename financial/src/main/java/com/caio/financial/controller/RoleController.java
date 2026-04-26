package com.caio.financial.controller;

import com.caio.financial.dto.RoleCreateDTO;
import com.caio.financial.dto.UserCreateDTO;
import com.caio.financial.entity.Role;
import com.caio.financial.entity.User;
import com.caio.financial.mapper.RoleMapper;
import com.caio.financial.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("role")
public class RoleController {


    private final RoleService roleService;
    private final RoleMapper roleMapper;


    @PostMapping
    public ResponseEntity<Void> createRole(@RequestBody RoleCreateDTO dto){
        Role role = roleMapper.toEntity(dto);
        roleService.saveRole(role);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(role.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable String id){
        return roleService.findRoleById(UUID.fromString(id)).map(role -> {

            roleService.deleteRole(role);
            return ResponseEntity.noContent().build();

        }).orElseGet(() -> ResponseEntity.notFound().build());
    }



}
