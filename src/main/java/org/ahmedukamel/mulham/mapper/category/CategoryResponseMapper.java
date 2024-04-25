package org.ahmedukamel.mulham.mapper.category;

import org.ahmedukamel.mulham.dto.category.CategoryResponse;
import org.ahmedukamel.mulham.model.Category;
import org.ahmedukamel.mulham.util.ContextHolderUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CategoryResponseMapper implements Function<Category, CategoryResponse> {
    @Override
    public CategoryResponse apply(Category category) {
        CategoryResponse response = new CategoryResponse();
        BeanUtils.copyProperties(category, response);
        if (ContextHolderUtils.getLanguageCode().equalsIgnoreCase("ar")) {
            response.setName(category.getArabicName());
        } else {
            response.setName(category.getEnglishName());
        }
        return response;
    }
}