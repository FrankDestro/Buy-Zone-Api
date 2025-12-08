package com.sysout.buy_zone_api.services;

import com.sysout.buy_zone_api.Util.LoggedUser;
import com.sysout.buy_zone_api.factory.UserFactory;
import com.sysout.buy_zone_api.mappers.UserDTOMapper;
import com.sysout.buy_zone_api.models.dto.UserDTO;
import com.sysout.buy_zone_api.models.entities.User;
import com.sysout.buy_zone_api.projections.UserDetailsProjection;
import com.sysout.buy_zone_api.repository.UserRepository;
import com.sysout.buy_zone_api.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoggedUser loggedUser;

    @Mock
    private UserDTOMapper userDTOMapper;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private String existingUserName;
    private String nonExistingUserName;
    private User user;
    private List<UserDetailsProjection> userDetails;
    private UserService userServiceSpy;
    private UserDTO userDTO;
    private PageImpl<User> pageUser;


    @BeforeEach
    void SetUp() {
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        existingUserName = "maria@gmail.com";
        nonExistingUserName = "user@gmail.com";
        user = UserFactory.createUserClient();
        userServiceSpy = spy(userService);
        userDTO = UserFactory.createUserClientDTO();
        pageUser = new PageImpl<>(List.of(user));
    }

    @Test
    void findAllUserPagedShouldReturnPageUserDTO() {
        Pageable pageable = PageRequest.of(0, 12);

        when(userRepository.findAll(pageable)).thenReturn(pageUser);
        when(userDTOMapper.toUserDTO(user)).thenReturn(userDTO);

        Page<UserDTO> result = userService.findAllUserPaged(pageable);

        assertNotNull(result);
    }

    @Test
    void findUserByIdShouldReturnUserDTO() {
        when(userRepository.findById(existingId)).thenReturn(Optional.of(user));
        when(userDTOMapper.toUserDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.findUserById(existingId);

        assertNotNull(result);
    }

    @Test
    void findUserByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Pageable pageable = PageRequest.of(0, 12);

        when(userRepository.findAll(pageable)).thenReturn(pageUser);

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.findUserById(nonExistingId);
        });
    }
}
