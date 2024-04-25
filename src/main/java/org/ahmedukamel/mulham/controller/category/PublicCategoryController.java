package org.ahmedukamel.mulham.controller.category;

import jakarta.validation.constraints.Min;
import org.ahmedukamel.mulham.service.category.IPublicCategoryService;
import org.ahmedukamel.mulham.service.category.PublicCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/public/category")
public class PublicCategoryController {
    private final IPublicCategoryService service;

    public PublicCategoryController(PublicCategoryService service) {
        this.service = service;
    }


    @GetMapping(value = "{categoryId}")
    public ResponseEntity<?> getPublicCategory(@Min(value = 1) @PathVariable(value = "categoryId") Integer categoryId) {
        return ResponseEntity.ok().body(service.getCategory(categoryId));
    }

    @GetMapping(value = "page")
    public ResponseEntity<?> getPublicCategory(@Min(value = 1) @RequestParam(value = "size") long pageSize,
                                               @Min(value = 1) @RequestParam(value = "number") long pageNumber) {
        return ResponseEntity.ok().body(service.getCategories(pageSize, pageNumber));
    }

    @GetMapping(value = "all")
    public ResponseEntity<?> getPublicCategories() {
        return ResponseEntity.ok().body(service.getCategories());
    }
}