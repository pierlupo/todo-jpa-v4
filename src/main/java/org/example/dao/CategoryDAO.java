package org.example.dao;

import org.example.entity.Category;
import org.example.entity.User;

import java.util.List;

public interface CategoryDAO {

    public boolean addCategory(Category category);

    public List<Category> getAllCategories();

    public boolean deleteCategory(Long categoryId);
}
