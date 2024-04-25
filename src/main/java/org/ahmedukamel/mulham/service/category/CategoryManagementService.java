package org.ahmedukamel.mulham.service.category;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.dto.category.CategoryDto;
import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.ahmedukamel.mulham.mapper.category.CategoryDtoMapper;
import org.ahmedukamel.mulham.model.Category;
import org.ahmedukamel.mulham.repository.CategoryRepository;
import org.ahmedukamel.mulham.service.db.DatabaseFetcher;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryManagementService implements ICategoryManagementService {
    final CategoryRepository repository;
    final CategoryDtoMapper mapper;

    @Override
    public Object createCategory(Object object) {
        Category category = new Category();
        BeanUtils.copyProperties(object, category);

        Category savedCategory = repository.save(category);
        CategoryDto response = mapper.apply(savedCategory);

        return new ApiResponse(true, "Category created successfully!", response);
    }

    @Override
    public Object getCategory(Integer id) {
        Category category = DatabaseFetcher.get(repository::findById, id, Category.class);
        CategoryDto response = mapper.apply(category);

        return new ApiResponse(true, "Category with id %d returned successfully!".formatted(id), response);
    }

    @Override
    public Object getCategories() {
        List<CategoryDto> response = repository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Category::getId))
                .map(mapper)
                .toList();

        return new ApiResponse(true, "Categories returned successfully!", response);
    }

    @Override
    public Object getCategories(long pageSize, long pageNumber) {
        List<CategoryDto> response = repository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Category::getId))
                .skip(pageSize * (pageNumber - 1))
                .limit(pageSize)
                .map(mapper)
                .toList();

        return new ApiResponse(true, "Categories returned successfully!", response);
    }

    @Override
    public Object updateCategory(Integer id, Object object) {
        Category category = DatabaseFetcher.get(repository::findById, id, Category.class);
        BeanUtils.copyProperties(object, category);
        category.setId(id);
        Category savedCategory = repository.save(category);
        CategoryDto response = mapper.apply(savedCategory);

        return new ApiResponse(true, "Category updated successfully!", response);
    }

    @Override
    public void deleteCategory(Integer id) {
        Category category = DatabaseFetcher.get(repository::findById, id, Category.class);
        repository.delete(category);
    }
}