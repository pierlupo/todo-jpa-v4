package org.example.impl;

import org.example.dao.CategoryDAO;
import org.example.entity.Category;
import org.example.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
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
    }

