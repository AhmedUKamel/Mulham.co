package org.ahmedukamel.mulham.controller;

import org.ahmedukamel.mulham.service.web.IWebViewService;
import org.ahmedukamel.mulham.service.web.WebViewService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
public class WebController {
    final IWebViewService service;

    public WebController(WebViewService service) {
        this.service = service;
    }

    @GetMapping(value = "p/activate")
    public ModelAndView activateAccount(@RequestParam(value = "token") UUID tokenId,
                                        @RequestParam(value = "email") String email) {
        return service.activateAccount(tokenId, email);
    }
}