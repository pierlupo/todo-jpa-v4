package org.example.impl;

import org.example.dao.CategoryDAO;
import org.example.entity.Category;
import org.example.entity.Todo;
import org.example.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {
    private EntityManagerFactory entityManagerFactory;

    public CategoryDAOImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    @Override
    public boolean addCategory(Category category) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(category);
            transaction.commit();
            return true;
        }catch (Exception e){
            if(transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }finally {
            entityManager.close();
        }
    }


    @Override
    public List<Category> getAllCategories() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Category> categories = entityManager.createQuery("Select c from Category c",Category.class).getResultList();
        entityManager.close();
        return categories;
    }

    @Override
    public boolean deleteCategory(Long categoryId) {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                Category category = entityManager.find(Category.class,categoryId);
                if(category != null){
                    //category = entityManager.merge(category); pas de besoin de merge ici :
                    entityManager.remove(category);
                    transaction.commit();
                    return true;
                } else {
                    return false;
                }
            }catch (Exception e){
                if(transaction.isActive()){
                    transaction.rollback();
                }
                e.printStackTrace();
                return false;
            }finally {
                entityManager.close();
            }
        }

    @Override
    public List<Todo> getTodosByCategory(Category category) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String jpql = "SELECT t FROM Todo t JOIN t.categories c WHERE c = :category";
        TypedQuery<Todo> query = entityManager.createQuery(jpql, Todo.class);
        query.setParameter("category", category);
        List<Todo> todos = query.getResultList();
        entityManager.close();
        return todos;
    }

    @Override
    public void addTodoToCategory(Todo todo, Category category) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        todo.getCategories().add(category);
        entityManager.merge(todo);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void removeTodoFromCategory(Todo todo, Category category) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    todo.getCategories().removeIf(category1 -> (category1.getCategoryTitle().equals(category.getCategoryTitle())));
    entityManager.merge(todo);
    entityManager.getTransaction().commit();
    entityManager.close();
    }

    @Override
    public String getCategoryName(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String categorytitle = entityManager.find(Category.class, id).getCategoryTitle();
        entityManager.close();
        return categorytitle;
    }

    @Override
    public Category getCategoryById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Category category = entityManager.find(Category.class, id);
        entityManager.close();
        return category;
    }
}

