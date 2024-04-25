package org.ahmedukamel.mulham.service.category;

public interface ICategoryManagementService {
    Object createCategory(Object object);

    Object getCategory(Integer id);

    Object getCategories();

    Object getCategories(long pageSize, long pageNumber);

    Object updateCategory(Integer id, Object object);

    void deleteCategory(Integer id);
}