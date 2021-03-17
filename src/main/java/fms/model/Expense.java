package fms.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "financeId")
public class Expense extends Finance {
    public Expense() {

    }
    public Expense(String name, Integer money, Category category) {
        super(name, money, category);
    }
}
