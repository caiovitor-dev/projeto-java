package com.caio.financial.service;

import com.caio.financial.entity.Role;
import com.caio.financial.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role saveRole(Role role){
        return roleRepository.save(role);
    }
    public Optional<Role> findRoleById(UUID id){
      return  roleRepository.findById(id);
    }
    public void deleteRole(Role role){
         roleRepository.delete(role);
    }
}
