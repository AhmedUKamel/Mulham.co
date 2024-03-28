package org.ahmedukamel.mulham.controller;

import jakarta.validation.constraints.NotBlank;
import org.ahmedukamel.mulham.service.web.IWebViewService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
public class WebController {
    final IWebViewService service;

    public WebController(@Qualifier(value = "webViewService") IWebViewService service) {
        this.service = service;
    }

    @GetMapping(value = "p/activate")
    public ModelAndView activateAccount(@RequestParam(value = "token") UUID tokenId,
                                        @RequestParam(value = "email") String email) {
        return service.activateAccount(tokenId, email);
    }
}