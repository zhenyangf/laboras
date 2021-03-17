package fms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@MappedSuperclass
public class Finance {
    public Integer getFinanceId() {
        return financeId;
    }

    public void setFinanceId(Integer financeId) {
        this.financeId = financeId;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer financeId;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    private Integer money;

    @ManyToOne
    @JsonIgnore
    private Category parentCategory;

    public Finance() {}

    public Finance(String name, Integer money, Category category) {
        this.name = name;
        this.money = money;
        this.parentCategory = category;
    }
}
