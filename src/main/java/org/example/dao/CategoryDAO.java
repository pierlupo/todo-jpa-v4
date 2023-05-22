package org.example.dao;

import org.example.entity.Category;
import org.example.entity.Todo;
import org.example.entity.User;

import java.util.List;

public interface CategoryDAO {

    public boolean addCategory(Category category);

    public List<Category> getAllCategories();

    public boolean deleteCategory(Long categoryId);

    List<Todo> getTodosByCategory(Category category);

    void addTodoToCategory(Todo todo, Category category);

    void removeTodoFromCategory(Todo todo, Category category);

    String getCategoryName(Long id);

    Category getCategoryById(Long id);
}
