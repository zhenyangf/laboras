package fms.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class BaseHibernateControl {
    protected EntityManagerFactory emf = null;

    public BaseHibernateControl(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
