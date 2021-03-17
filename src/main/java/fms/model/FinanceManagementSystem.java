package fms.model;

import java.time.LocalDate;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "fms_table")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "fmsID")
public class FinanceManagementSystem {
    public int getFmsID() {
        return fmsID;
    }

    public void setFmsID(int fmsID) {
        this.fmsID = fmsID;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setSystemUsers(List<User> systemUsers) {
        this.systemUsers = systemUsers;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int fmsID;

    private LocalDate dateCreated;
    private String systemVersion;

    @OneToMany(mappedBy = "fms", cascade = CascadeType.MERGE, orphanRemoval = true)
    @OrderBy("categoryID ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "fms", cascade = CascadeType.MERGE, orphanRemoval = true)
    @OrderBy("userIDInsideDB ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> systemUsers = new ArrayList<>();

    public FinanceManagementSystem() {}

    public FinanceManagementSystem(LocalDate dateCreated, String systemVersion) {
        this.dateCreated = dateCreated;
        this.systemVersion = systemVersion;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public List<User> getSystemUsers() {
        return systemUsers;
    }

    public void setSystemUsers(ArrayList<User> systemUsers) {
        this.systemUsers = systemUsers;
    }
}
