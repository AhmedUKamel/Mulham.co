package org.ahmedukamel.mulham.service.user;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.ahmedukamel.mulham.dto.user.UserResponse;
import org.ahmedukamel.mulham.mapper.user.UserResponseMapper;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.repository.UserRepository;
import org.ahmedukamel.mulham.service.db.DatabaseFetcher;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    final UserRepository repository;
    final UserResponseMapper mapper;

    @Override
    public Object getUser(Long userId) {
        User user = DatabaseFetcher.get(repository::findById, userId, User.class);
        UserResponse response = mapper.apply(user);

        return new ApiResponse(true, "User profile have been fetched successfully.", response);
    }

    @Override
    public Object getUsers() {
        List<UserResponse> response = repository.findAll()
                .stream()
                .sorted(Comparator.comparingLong(User::getId))
                .map(mapper)
                .toList();

        return new ApiResponse(true, "All users profile have been fetched successfully.", response);
    }

    @Override
    public Object getUsers(long pageSize, long pageNumber) {
        List<UserResponse> response = repository.findAll()
                .stream()
                .sorted(Comparator.comparingLong(User::getId))
                .skip(pageSize * (pageNumber - 1))
                .limit(pageSize)
                .map(mapper)
                .toList();

        return new ApiResponse(true, "Users profile have been fetched successfully.", response);
    }
}