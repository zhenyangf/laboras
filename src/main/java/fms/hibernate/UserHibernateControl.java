package fms.hibernate;

import fms.model.User;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;

public class UserHibernateControl extends BaseHibernateControl {
    public UserHibernateControl(EntityManagerFactory emf) {
        super(emf);
    }
    
    public void create(User user) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            user = em.merge(user);
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
            User user = null;

            try {
                user = em.getReference(User.class, id);
                System.out.println("Refff" + user.getUserIDInsideDB());
            } catch (Exception e) {
                e.printStackTrace();
            }
            em.remove(user);
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

    public List<User> getuserList() {
        return getuserList(true, -1, -1);
    }

    public List<User> getuserList(boolean all, int maxRes, int firstRes) {

        EntityManager em = getEntityManager();
        try {
            CriteriaQuery criteriaQuery = em.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(User.class));
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






