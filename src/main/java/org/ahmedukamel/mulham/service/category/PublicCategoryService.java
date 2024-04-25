package org.ahmedukamel.mulham.service.category;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.ahmedukamel.mulham.dto.category.CategoryResponse;
import org.ahmedukamel.mulham.mapper.category.CategoryResponseMapper;
import org.ahmedukamel.mulham.model.Category;
import org.ahmedukamel.mulham.repository.CategoryRepository;
import org.ahmedukamel.mulham.service.db.DatabaseFetcher;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCategoryService implements IPublicCategoryService {
    final CategoryRepository repository;
    final CategoryResponseMapper mapper;

    @Override
    public Object getCategory(Integer id) {
        Category category = DatabaseFetcher.get(repository::findById, id, Category.class);
        CategoryResponse response = mapper.apply(category);

        return new ApiResponse(true, "Category with id %d returned successfully!".formatted(id), response);
    }

    @Override
    public Object getCategories() {
        List<CategoryResponse> response = repository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Category::getId))
                .map(mapper)
                .toList();

        return new ApiResponse(true, "Categories returned successfully!", response);
    }

    @Override
    public Object getCategories(long pageSize, long pageNumber) {
        List<CategoryResponse> response = repository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Category::getId))
                .skip(pageSize * (pageNumber - 1))
                .limit(pageSize)
                .map(mapper)
                .toList();

        return new ApiResponse(true, "Categories returned successfully!", response);
    }
}