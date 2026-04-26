package com.caio.financial.dto;

import java.util.List;
import java.util.UUID;

public record UserCreateDTO(String login, String password,String name, List<UUID> rolesId) {
}
