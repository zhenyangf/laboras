package fms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "CompanyUser")
@DiscriminatorValue("CompanyUser")
public class CompanyUser extends User {
    private String companyName;
    private String contactInfo;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    @JsonIgnore
    public CompanyUser() {}

    public CompanyUser(String userID, String password, String companyName, String contactInfo, FinanceManagementSystem fms) {
        super(userID, password, fms);
        this.companyName = companyName;
        this.contactInfo = contactInfo;
    }
}
