package ru.valerych.splitreader.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.valerych.splitreader.entities.Role;
import ru.valerych.splitreader.repositories.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role getRoleByName(String name){
        return roleRepository.findOneByName(name);
    }
}
