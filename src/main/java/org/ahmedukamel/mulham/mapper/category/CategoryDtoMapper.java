package org.ahmedukamel.mulham.mapper.category;

import org.ahmedukamel.mulham.dto.category.CategoryDto;
import org.ahmedukamel.mulham.model.Category;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CategoryDtoMapper implements Function<Category, CategoryDto> {
    @Override
    public CategoryDto apply(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getEnglishName(),
                category.getArabicName(),
                category.getIcon()
        );
    }
}