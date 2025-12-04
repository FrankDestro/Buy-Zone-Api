package com.sysout.buy_zone_api.services;

import com.sysout.buy_zone_api.Util.LoggedUser;
import com.sysout.buy_zone_api.mappers.PhoneDTOMapper;
import com.sysout.buy_zone_api.mappers.UserDTOMapper;
import com.sysout.buy_zone_api.models.dto.UserDTO;
import com.sysout.buy_zone_api.models.dto.UserInsertDTO;
import com.sysout.buy_zone_api.models.dto.UserUpdateDTO;
import com.sysout.buy_zone_api.models.entities.Phone;
import com.sysout.buy_zone_api.models.entities.Role;
import com.sysout.buy_zone_api.models.entities.User;
import com.sysout.buy_zone_api.projections.UserDetailsProjection;
import com.sysout.buy_zone_api.repository.PhoneRepository;
import com.sysout.buy_zone_api.repository.RoleRepository;
import com.sysout.buy_zone_api.repository.UserRepository;
import com.sysout.buy_zone_api.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserDTOMapper userDTOMapper;
    private final PhoneDTOMapper phoneDTOMapper;
    private final PhoneRepository phoneRepository;
    private final LoggedUser loggedUser;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllUserPaged(Pageable pageable) {
        Page<User> list = userRepository.findAll(pageable);
        return list.map(user -> userDTOMapper.toUserDTO(user));
    }

    @Transactional(readOnly = true)
    public UserDTO findUserById(Long id) {
        User entity = getUser(id);
        return userDTOMapper.toUserDTO(entity);
    }

    @Transactional(readOnly = true)
    public UserDTO findUserLogged() {
        User entity = authenticated();
        return userDTOMapper.toUserDTO(entity);
    }

    @Transactional
    public UserDTO Register(UserInsertDTO dto) {
        User entity = userDTOMapper.toUserEntity(dto);
        entity.getRoles().clear();
        Role role = roleRepository.findByAuthority("ROLE_OPERATOR");
        entity.getRoles().add(role);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setCreatedAt(Instant.now());
        entity = userRepository.save(entity);
        List<Phone> phones = phoneDTOMapper.toPhoneList(dto.getPhones());
        for (Phone phone : phones) {
            phone.setClient(entity);
        }
        phoneRepository.saveAll(phones);
        return userDTOMapper.toUserDTO(entity);
    }


    @Transactional
    public UserDTO update(Long id, UserUpdateDTO dto) {
        try {
            User entity = userRepository.getReferenceById(id);
            entity.setFullName(dto.getFullName());
            entity.setBirthDate(dto.getBirthDate());
            entity = userRepository.save(entity);
            return userDTOMapper.toUserDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public User getUser(Long id) {
        Optional<User> obj = userRepository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return entity;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<UserDetailsProjection> result = userRepository.searchUserAndRoleByEmail(username);
        if (result.size() == 0) {
            throw new UsernameNotFoundException("User not found");
        }

        User user = new User();

        System.out.println(result);


        user.setId(result.get(0).getId());
        user.setEmail(username);
        user.setPassword(result.get(0).getPassword());
        user.setEnabled(result.get(0).getEnabled());
        user.setAccountNonLocked(result.get(0).getAccountNonLocked());
        user.setAccountNonExpired(result.get(0).getAccountNonExpired());
        user.setCredentialsNonExpired(result.get(0).getCredentialsNonExpired());

        for (UserDetailsProjection projection : result) {
            user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }
        return user;
    }

    public User authenticated() {
        try {
            String userName = loggedUser.getLoggedUserName();
            return userRepository.findByEmail(userName);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Invalid user");
        }
    }
}
