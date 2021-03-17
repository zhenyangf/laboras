package fms.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories_table")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "categoryID")
public class Category implements Serializable {
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryID;

    String name;
    String description;
    LocalDate dateCreated;
    LocalDate dateModified;

    @ManyToOne
    @JsonIgnore
    private FinanceManagementSystem fms;

    @ManyToOne
    @JsonIgnore
    private Category parentCategory;

    @JsonIgnore
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.MERGE, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Category> subCategories = new ArrayList<>();

//    @ManyToMany(cascade = CascadeType.MERGE)
//    @OrderBy("userIDInsideDB ASC")
//    @LazyCollection(LazyCollectionOption.FALSE)
    @Transient
    @JsonIgnore
    private List<User> responsibleUsers = new ArrayList<>();

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Income> income = new ArrayList<>();

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Expense> expense = new ArrayList<>();

    public Category() {}

    public Category(String name, String description, FinanceManagementSystem fms) {
        this.name = name;
        this.description = description;
        this.fms = fms;
    }

    public Category(String name, String description, Category parentCategory) {
        this.name = name;
        this.description = description;
        this.parentCategory = parentCategory;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public FinanceManagementSystem getFms() {
        return fms;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public Category(String name, String description, LocalDate dateCreated, LocalDate dateModified, List<Category> subCategories, List<User> responsibleUsers) {
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.responsibleUsers = responsibleUsers;
        this.subCategories = subCategories;
        this.income = null;
        this.expense = null;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
    }

    public List<User> getResponsibleUsers() {
        return responsibleUsers;
    }

    public void setResponsibleUsers(List<User> responsibleUsers) {
        this.responsibleUsers = responsibleUsers;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public List<Income> getIncome() {
        return income;
    }

    public void setIncome(List<Income> income) {
        this.income = income;
    }

    public List<Expense> getExpense() {
        return expense;
    }

    public void setExpense(List<Expense> expense) {
        this.expense = expense;
    }
}
