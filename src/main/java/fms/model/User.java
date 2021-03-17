package fms.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@DiscriminatorColumn(name = "user_type")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userIDInsideDB")
public class User implements Serializable {
    public void setUserIDInsideDB(Integer userIDInsideDB) {
        this.userIDInsideDB = userIDInsideDB;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer userIDInsideDB;

    @JsonIgnore
    @ManyToOne
    protected FinanceManagementSystem fms;

    @JsonIgnore
    @OneToMany
    protected List<Category> categoryList = new ArrayList();

    public Integer getUserIDInsideDB() {
        return userIDInsideDB;
    }

    public FinanceManagementSystem getFms() {
        return fms;
    }

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
    }


    protected String userID;
    protected String password;


    public User(String userID, String password, FinanceManagementSystem fms) {
        this.userID = userID;
        this.password = password;
        this.fms = fms;
    }

    public User() {}

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
