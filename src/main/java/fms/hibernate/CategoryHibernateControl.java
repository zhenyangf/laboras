package fms.hibernate;

import fms.model.Category;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;

public class CategoryHibernateControl extends BaseHibernateControl {
    public CategoryHibernateControl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    public void create(Category category) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(category);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Category category) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            category = em.merge(category);
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
            Category category = null;

            try {
                category = em.getReference(Category.class, id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            em.remove(category);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Category> getcategoryList() {
        return getcategoryList(true, -1, -1);
    }

    public List<Category> getcategoryList(boolean all, int maxRes, int firstRes) {

        EntityManager em = getEntityManager();
        try {
            CriteriaQuery criteriaQuery = em.getCriteriaBuilder().createQuery();
            criteriaQuery.select(criteriaQuery.from(Category.class));
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



