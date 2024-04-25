package org.ahmedukamel.mulham.service.user;

public interface IUserService {
    Object getUser(Long userId);

    Object getUsers();

    Object getUsers(long pageSize, long pageNumber);
}