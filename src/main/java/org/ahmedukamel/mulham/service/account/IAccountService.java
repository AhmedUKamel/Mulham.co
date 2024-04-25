package org.ahmedukamel.mulham.service.account;

public interface IAccountService {
    Object accountActivation(String email);

    Object passwordReset(String email);
}
