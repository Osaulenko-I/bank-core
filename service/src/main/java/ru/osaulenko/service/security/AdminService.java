package ru.osaulenko.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.osaulenko.domain.HairColorDomain;
import ru.osaulenko.domain.UserDomain;
import ru.osaulenko.dto.request.user.*;
import ru.osaulenko.dto.response.UserResponseDto;
import ru.osaulenko.entities.User;
import ru.osaulenko.entities.security.BankAdmin;
import ru.osaulenko.entities.security.BankClient;
import ru.osaulenko.mapper.dto.UserDtoMapper;
import ru.osaulenko.mapper.persistence.HaircolorMapper;
import ru.osaulenko.mapper.persistence.UserMapper;
import ru.osaulenko.repository.UserRepository;
import ru.osaulenko.repository.security.AdminRepository;
import ru.osaulenko.repository.security.BankUserRepository;
import ru.osaulenko.repository.security.ClientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final SecurityUtils securityUtils;
    private final ClientRepository clientRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final UserDtoMapper userDtoMapper;
    private final HaircolorMapper haircolorMapper;
    private final BankUserRepository bankUserRepository;

    @Transactional
    public UserResponseDto createClient(CreateClientRequestDto requestDto) {
        if (!securityUtils.isAdmin()) {
            throw new AccessDeniedException("only admin can create clients");
        }

        if (bankUserRepository.existsByUsername(requestDto.getUsername())) {
            throw new RuntimeException("user already exists");
        }

        UserDomain user = new UserDomain();
        user.setMale(requestDto.isMale());
        user.setHairColor(HairColorDomain.fromString(requestDto.getHaircolor()));
        user.setName(requestDto.getName());

        User userSaved = userRepository.save(userMapper.toEntity(user));

        BankClient bankClient = new BankClient();
        bankClient.setUserProfile(
                userRepository.findById(userSaved.getUserId()).orElseThrow(
                        () -> new RuntimeException("cant find user")));
        bankClient.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        bankClient.setUsername(requestDto.getUsername());

        clientRepository.save(bankClient);

        return userDtoMapper.toDto(userMapper.toDomain(userSaved));
    }

    public void createAdmin(CreateAdminRequestDto requestDto) {
        if (!securityUtils.isAdmin()) {
            throw new AccessDeniedException("only admin can create clients");
        }

        if (bankUserRepository.existsByUsername(requestDto.getUsername())) {
            throw new RuntimeException("admin already exists");
        }

        BankAdmin bankAdmin = new BankAdmin();
        bankAdmin.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        bankAdmin.setUsername(requestDto.getUsername());

        adminRepository.save(bankAdmin);
    }


    @Transactional(readOnly = true)
    public List<UserResponseDto> getUserByGender(GetUserFilterByGenderRequestDto requestDto) {
        if (!securityUtils.isAdmin()) {
            throw new AccessDeniedException("only admin can view users");
        }
        List<BankClient> clients = clientRepository.findByUserProfile_Male(requestDto.isMale());
        return userDtoMapper.toDtoList(userMapper.toDomainList(
                clients.stream().map(BankClient::getUserProfile).toList()));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getUserByHaircolor(GetUsersFilterByHaircolorRequestDto requestDto) {
        if (!securityUtils.isAdmin()) {
            throw new AccessDeniedException("only admin can view users");
        }

        List<BankClient> clients = clientRepository.findByUserProfile_HairColor(
                haircolorMapper.toEntity(HairColorDomain.fromString(requestDto.getHaircolor())));

        return userDtoMapper.toDtoList(userMapper.toDomainList(
                clients.stream().map(BankClient::getUserProfile).toList()));
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(GetUserByIdRequestDto requestDto) {
        if (!securityUtils.isAdmin()) {
            throw new AccessDeniedException("only admin can view users");
        }

        BankClient client = clientRepository.findByUserProfile_UserId(requestDto.getUserid()).orElseThrow(
                () -> new RuntimeException("user not found"));

        User user = client.userProfile;

        return userDtoMapper.toDto(userMapper.toDomain(user));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getUsers() {
        if (!securityUtils.isAdmin()) {
            throw new AccessDeniedException("only admin can view users");
        }

        List<BankClient> users = clientRepository.findAll();

        return userDtoMapper.toDtoList(userMapper.toDomainList(
                users.stream().map(BankClient::getUserProfile).toList()));
    }
}
