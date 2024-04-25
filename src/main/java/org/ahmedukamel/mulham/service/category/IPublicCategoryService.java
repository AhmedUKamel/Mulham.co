package org.ahmedukamel.mulham.service.category;

public interface IPublicCategoryService {

    Object getCategory(Integer id);

    Object getCategories();

    Object getCategories(long pageSize, long pageNumber);
}