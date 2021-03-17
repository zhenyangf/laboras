package fms.hibernate;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class hibernateControl {
    static private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Kursinis");

    static public FMSHibernateControl fms = new FMSHibernateControl(emf);

    static public UserHibernateControl users = new UserHibernateControl(emf);

    static public CategoryHibernateControl categories = new CategoryHibernateControl(emf);

    static public FinanceHibernateControl finance = new FinanceHibernateControl(emf);
}
