package fms.hibernate;

import fms.model.FinanceManagementSystem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class FMSHibernateControl extends BaseHibernateControl {
    public FMSHibernateControl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    public void create(FinanceManagementSystem fms) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(fms);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FinanceManagementSystem fms) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            fms = em.merge(fms);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void remove(Integer id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FinanceManagementSystem fms = null;

            try {
                fms = em.getReference(FinanceManagementSystem.class, id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            em.remove(fms);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FinanceManagementSystem> getfmsList() {
        return getfmsList(true, -1, -1);
    }

    public List<FinanceManagementSystem> getfmsList(boolean all, int maxRes, int firstRes) {

        EntityManager em = getEntityManager();
        try {
            CriteriaQuery criteriaQuery = em.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(FinanceManagementSystem.class));
            Query query = em.createQuery(criteriaQuery);

            if (!all) {
                query.setMaxResults(maxRes);
                query.setFirstResult(firstRes);
            }
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }
}
