package ru.valerych.splitreader.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.valerych.splitreader.dto.RegUserDTO;
import ru.valerych.splitreader.dto.UserDTO;
import ru.valerych.splitreader.entities.Role;
import ru.valerych.splitreader.entities.User;
import ru.valerych.splitreader.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public User getAuthUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (auth!=null){
            String username = auth.getName();
            user = userRepository.findUserByUsername(username);
        }
        return user;
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Transactional
    public User createUser(RegUserDTO regUserDTO) {
        User user = new User();
        user.setUsername(regUserDTO.getUsername());
        user.setPassword(regUserDTO.getPassword());
        user.setRoles(Collections.singletonList(roleService.getRoleByName("USER")));
        user.setEnabled(false);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setFirstName("");
        user.setLastName("");
        user.setBirthDate(null);
        user.setCity("");
        user.setLastVisit(null);
        user.setCurrentBookId(null);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public UserDTO getAuthUserDTO() {
        User user = getAuthUser();
        if (user==null) return null;
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword("******");
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setRoles(user.getRoles());
        userDTO.setCity(userDTO.getCity());
        userDTO.setBirthDate(user.getBirthDate());
        userDTO.setLastVisit(user.getLastVisit());
        userDTO.setCurrentBookId(user.getCurrentBookId());
        return userDTO;
    }

    @Transactional
    public void updateAuthUserDTO(UserDTO userDTO) {
        User user = getAuthUser();
        if (user==null) return;
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setCity(userDTO.getCity());
        user.setBirthDate(userDTO.getBirthDate());
        userRepository.save(user);
    }
}
