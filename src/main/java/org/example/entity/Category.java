package org.example.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private Long Id;

    private String CategoryTitle;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "category_todo", joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "todo_id"))
    private List<Todo> todos = new ArrayList<>();

    public Category() {
    }

    public Category(Long id, String categoryTitle) {
        Id = id;
        CategoryTitle = categoryTitle;
    }

    public Category(String categoryTitle) {
        CategoryTitle = categoryTitle;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getCategoryTitle() {
        return CategoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        CategoryTitle = categoryTitle;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    @Override
    public String toString() {
        return "Category{" +
                "Id=" + Id +
                ", CategoryTitle='" + CategoryTitle + '\'' +
                ", todos=" + todos +
                '}';
    }
}
