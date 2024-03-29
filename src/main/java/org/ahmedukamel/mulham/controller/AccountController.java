package org.ahmedukamel.mulham.controller;

import org.ahmedukamel.mulham.service.account.IAccountService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/p")
public class AccountController {
    private final IAccountService service;

    public AccountController(@Qualifier(value = "accountService") IAccountService service) {
        this.service = service;
    }

    @PostMapping(value = "account-activation")
    public ResponseEntity<Object> resendActivationMail(@RequestParam(value = "email") String email) {
        Object response = service.resendActivationMail(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}