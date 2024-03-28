package org.ahmedukamel.mulham.service.user;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.ahmedukamel.mulham.dto.response.ProfileResponse;
import org.ahmedukamel.mulham.mapper.ProfileMapper;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.repository.UserRepository;
import org.ahmedukamel.mulham.service.db.DatabaseFetcher;
import org.ahmedukamel.mulham.service.file.IFileService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    final MessageSource messageSource;
    final UserRepository repository;
    final IFileService fileService;
    final ProfileMapper mapper;

    @Override
    public Object getUserProfile(Long userId) {
        User user = DatabaseFetcher.get(repository::findById, userId, User.class);
        ProfileResponse data = mapper.apply(user);
        String message = messageSource.getMessage("successful.get.profile", null, Locale.ENGLISH);
        return new ApiResponse(true, message, Map.of("user", data));
    }
}