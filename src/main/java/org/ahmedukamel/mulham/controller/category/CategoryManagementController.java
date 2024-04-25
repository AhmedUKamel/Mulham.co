package org.ahmedukamel.mulham.controller.category;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.ahmedukamel.mulham.dto.category.CategoryDto;
import org.ahmedukamel.mulham.service.category.CategoryManagementService;
import org.ahmedukamel.mulham.service.category.ICategoryManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/v1/category")
@PreAuthorize(value = "hasAuthority('ADMIN')")
public class CategoryManagementController {
    private final ICategoryManagementService service;

    public CategoryManagementController(CategoryManagementService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto requestBody) {
        return ResponseEntity.created(URI.create("api/v1/category")).body(service.createCategory(requestBody));
    }

    @GetMapping(value = "{categoryId}")
    public ResponseEntity<?> getCategory(@Min(value = 1) @PathVariable(value = "categoryId") Integer categoryId) {
        return ResponseEntity.ok().body(service.getCategory(categoryId));
    }

    @GetMapping(value = "all")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok().body(service.getCategories());
    }

    @GetMapping(value = "page")
    public ResponseEntity<?> getCategories(@Min(value = 1) @RequestParam(value = "size") long pageSize,
                                           @Min(value = 1) @RequestParam(value = "number") long pageNumber) {
        return ResponseEntity.ok().body(service.getCategories(pageSize, pageNumber));
    }

    @PutMapping(value = "{categoryId}")
    public ResponseEntity<?> updateCategory(@Min(value = 1) @PathVariable(value = "categoryId") Integer categoryId,
                                            @Valid @RequestBody CategoryDto requestBody) {
        return ResponseEntity.ok().body(service.updateCategory(categoryId, requestBody));
    }

    @DeleteMapping(value = "{categoryId}")
    public ResponseEntity<?> deleteCategory(@Min(value = 1) @PathVariable(value = "categoryId") Integer categoryId) {
        service.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}