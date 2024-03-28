package org.ahmedukamel.mulham.service.web;

import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

public interface IWebViewService {
    ModelAndView activateAccount(UUID tokenId, String email);
}