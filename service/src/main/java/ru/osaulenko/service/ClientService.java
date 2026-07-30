package ru.osaulenko.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.osaulenko.domain.HairColorDomain;
import ru.osaulenko.domain.UserDomain;
import ru.osaulenko.dto.request.user.*;
import ru.osaulenko.dto.response.UserResponseDto;
import ru.osaulenko.entities.User;
import ru.osaulenko.entities.security.BankClient;
import ru.osaulenko.mapper.dto.UserDtoMapper;
import ru.osaulenko.mapper.persistence.HaircolorMapper;
import ru.osaulenko.mapper.persistence.UserMapper;
import ru.osaulenko.repository.UserRepository;
import ru.osaulenko.repository.security.ClientRepository;
import ru.osaulenko.service.security.SecurityUtils;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;
    private final ClientRepository clientRepository;

    private final HaircolorMapper colorMapper;
    private final UserMapper userMapper;
    private final UserDtoMapper userDtoMapper;

    @Transactional(readOnly = true)
    public UserResponseDto getProfile() {
        if (!securityUtils.isClient()) {
            throw new AccessDeniedException("only user can has profile");
        }
        UUID userId = securityUtils.getUUID();


        User user = clientRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"))
                .userProfile;
        return userDtoMapper.toDto(userMapper.toDomain(user));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getFriends() {
        if (!securityUtils.isClient()) {
            throw new AccessDeniedException("only user can has friends");
        }

        UUID userId = securityUtils.getUUID();

        BankClient client = clientRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("client not found"));
        User user = client.userProfile;

        return userDtoMapper.toDtoList(userMapper.toDomainList(user.getFriends()));
    }

    @Transactional
    public UserResponseDto addFriend(AddFriendRequestDto requestDto) {
        if (!securityUtils.isClient()) {
            throw new AccessDeniedException("only user can has friends");
        }

        UUID userId = securityUtils.getUUID();

        BankClient client = clientRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("client not found"));

        BankClient friendClient = clientRepository.findByUserProfile_UserId(requestDto.getFriendId())
                .orElseThrow(() -> new RuntimeException("client not found"));

        User user = client.userProfile;
        User userFriend = friendClient.userProfile;

        UserDomain userDomain = userMapper.toDomain(user);
        UserDomain friendDomain = userMapper.toDomain(userFriend);

        userDomain.addFriends(friendDomain.getUserId());
        friendDomain.addFriends(userDomain.getUserId());

        User updatedUser = userRepository.save(userMapper.toEntity(userDomain));
        userRepository.save(userMapper.toEntity(friendDomain));

        return userDtoMapper.toDto(userMapper.toDomain(updatedUser));
    }

    @Transactional
    public UserResponseDto deleteFriend(DeleteFriendRequestDto requestDto) {
        if (!securityUtils.isClient()) {
            throw new AccessDeniedException("only user can has friends");
        }

        UUID userId = securityUtils.getUUID();

        BankClient client = clientRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("client not found"));

        BankClient friendClient = clientRepository.findByUserProfile_UserId(requestDto.getFriendId())
                .orElseThrow(() -> new RuntimeException("client not found"));

        User user = client.userProfile;
        User userFriend = friendClient.userProfile;

        UserDomain userDomain = userMapper.toDomain(user);
        UserDomain friendDomain = userMapper.toDomain(userFriend);

        userDomain.removeFriend(friendDomain.getUserId());
        friendDomain.removeFriend(userDomain.getUserId());

        User updatedUser = userRepository.save(userMapper.toEntity(userDomain));
        userRepository.save(userMapper.toEntity(friendDomain));

        return userDtoMapper.toDto(userMapper.toDomain(updatedUser));
    }
}
