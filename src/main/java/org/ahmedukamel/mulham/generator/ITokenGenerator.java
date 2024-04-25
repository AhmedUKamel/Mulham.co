package org.ahmedukamel.mulham.generator;

import org.ahmedukamel.mulham.model.AccountToken;
import org.ahmedukamel.mulham.model.User;

import java.util.function.Function;

public interface ITokenGenerator extends Function<User, AccountToken> {
}