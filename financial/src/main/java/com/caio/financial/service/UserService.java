package com.caio.financial.service;

import com.caio.financial.entity.Role;
import com.caio.financial.entity.User;
import com.caio.financial.repository.RoleRepository;
import com.caio.financial.repository.UserRepository;
import com.caio.financial.security.SecurityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityService securityService;

    public User saveUser(User user, List<UUID> roleIds){

        String encryptedPassword= user.getPassword();
        user.setPassword(passwordEncoder.encode(encryptedPassword));

        List<Role> rolesValid= roleIds.stream()
                .map(role -> roleRepository.findById(role).
                        orElseThrow(() -> new IllegalArgumentException("Role não encontrada")
                        ))
                .toList();

        user.setRoles(rolesValid);

        return  userRepository.save(user);
    }

    @Transactional
    public User getUserByLogin(String login){
        User byLogin = userRepository.findByLogin(login);

        byLogin.getRoles().size();
        return byLogin;

    }

    public Optional<User> getUserById(UUID id){
        return userRepository.findById(id);
    }
    public void removeRoleFromUser(User user,UUID roleId){

        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Id inválido"));

        user.getRoles().removeIf(r -> r.getId().equals(roleId));
        userRepository.save(user);

    }
    public void deleteUser(User user){

        User loggedUser = securityService.getLoggedUser();

//        if(loggedUser.getId()!=user.getId()){
//            throw new RuntimeException("Não é possível apagar uma conta que não seja sua.");
//        }

        user.getRoles().clear();
        userRepository.delete(user);

    }
}
