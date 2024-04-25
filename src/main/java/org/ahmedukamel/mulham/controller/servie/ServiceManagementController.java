package org.ahmedukamel.mulham.controller.servie;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.ahmedukamel.mulham.dto.service.ServiceDto;
import org.ahmedukamel.mulham.service.service.IServiceManagementService;
import org.ahmedukamel.mulham.service.service.ServiceManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/v1/service")
@PreAuthorize(value = "hasAuthority('ADMIN')")
public class ServiceManagementController {
    final IServiceManagementService service;

    public ServiceManagementController(ServiceManagementService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> addService(@Valid @RequestBody ServiceDto request) {
        return ResponseEntity.created(URI.create("api/v1/service")).body(service.addService(request));
    }

    @PutMapping(value = "{serviceId}")
    public ResponseEntity<?> updateService(@Min(value = 1) @PathVariable(value = "serviceId") Integer serviceId, @Valid @RequestBody ServiceDto request) {
        return ResponseEntity.ok().body(service.updateService(serviceId, request));
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

    @DeleteMapping(value = "{serviceId}")
    public ResponseEntity<?> deleteService(@PathVariable(value = "serviceId") Integer serviceId) {
        service.deleteService(serviceId);
        return ResponseEntity.noContent().build();
    }
}