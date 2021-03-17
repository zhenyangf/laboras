package fms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "EmployeeUser")
@DiscriminatorValue("EmployeeUser")
public class EmployeeUser extends User  {
    private String name;
    private String surname;
    private String contactInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    @JsonIgnore
    public EmployeeUser() {}
    public EmployeeUser(String userID, String password, String name, String surname, String contactInfo, FinanceManagementSystem fms) {
        super(userID,password, fms);
        this.name = name;
        this.surname = surname;
        this.contactInfo = contactInfo;
    }




}
