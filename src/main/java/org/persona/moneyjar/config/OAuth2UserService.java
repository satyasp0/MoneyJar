package org.persona.moneyjar.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.persona.moneyjar.dto.Oauth2UserInfoDto;
import org.persona.moneyjar.entity.User;
import org.persona.moneyjar.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Satya
 * @created 04/07/2024 - 14:54
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @SneakyThrows
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        log.trace("Load user {}", oAuth2UserRequest);
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        processOAuth2User(oAuth2UserRequest, oAuth2User);
        return oAuth2User;
    }

    private void processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        Oauth2UserInfoDto userInfoDto = Oauth2UserInfoDto
                .builder()
                .name(oAuth2User.getAttributes().get("name").toString())
                .id(oAuth2User.getAttributes().get("sub").toString())
                .email(oAuth2User.getAttributes().get("email").toString())
                .picture(oAuth2User.getAttributes().get("picture").toString())
                .build();

        log.trace("User info is {}", userInfoDto);
        Optional<User> userOptional = userRepository.findByUsername(userInfoDto.getEmail());
        log.trace("User is {}", userOptional);
        User user = userOptional
                .map(existingUser -> updateExistingUser(existingUser, userInfoDto))
                .orElseGet(() -> registerNewUser(oAuth2UserRequest, userInfoDto));
        log.info("User Operation Successfully {}", user);
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, Oauth2UserInfoDto userInfoDto) {
        User user = new User();
        user.setProvider(oAuth2UserRequest.getClientRegistration().getRegistrationId());
        user.setProviderId(userInfoDto.getId());
        user.setName(userInfoDto.getName());
        user.setUsername(userInfoDto.getEmail());
        user.setPicture(userInfoDto.getPicture());
        user.setId(UUID.randomUUID());
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, Oauth2UserInfoDto userInfoDto) {
        existingUser.setName(userInfoDto.getName());
        existingUser.setPicture(userInfoDto.getPicture());
        log.info("Updating existing user {}", existingUser);
        return userRepository.save(existingUser);
    }

}