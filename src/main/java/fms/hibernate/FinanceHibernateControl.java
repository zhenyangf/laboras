package fms.hibernate;

import fms.model.Finance;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;

public class FinanceHibernateControl extends BaseHibernateControl {
    public FinanceHibernateControl(EntityManagerFactory emf) {
        super(emf);
    }

    public void create(Finance finance) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(finance);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Finance finance) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            finance = em.merge(finance);
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
            Finance finance = null;

            try {
                finance = em.getReference(Finance.class, id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            em.remove(finance);
            em.flush();
            em.clear();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Finance> getfinanceList() {
        return getfinanceList(true, -1, -1);
    }

    public List<Finance> getfinanceList(boolean all, int maxRes, int firstRes) {

        EntityManager em = getEntityManager();
        try {
            CriteriaQuery criteriaQuery = em.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Finance.class));
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






