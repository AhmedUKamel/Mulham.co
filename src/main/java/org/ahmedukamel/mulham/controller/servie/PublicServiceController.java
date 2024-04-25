package org.ahmedukamel.mulham.controller.servie;

import jakarta.validation.constraints.Min;
import org.ahmedukamel.mulham.service.service.IPublicServiceService;
import org.ahmedukamel.mulham.service.service.PublicServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/public/service")
public class PublicServiceController {
    final IPublicServiceService service;

    public PublicServiceController(PublicServiceService service) {
        this.service = service;
    }

    @GetMapping(value = "{serviceId}")
    public ResponseEntity<?> getService(@Min(value = 1) @PathVariable(value = "serviceId") Integer serviceId) {
        return ResponseEntity.ok().body(service.getService(serviceId));
    }

    @GetMapping(value = "all")
    public ResponseEntity<?> getServices() {
        return ResponseEntity.ok().body(service.getServices());
    }

    @GetMapping(value = "page")
    public ResponseEntity<?> getServices(@Min(value = 1) @RequestParam(value = "size") long pageSize,
                                         @Min(value = 1) @RequestParam(value = "number") long pageNumber) {
        return ResponseEntity.ok().body(service.getServices(pageSize, pageNumber));
    }
}