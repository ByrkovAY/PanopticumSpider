/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.controllers;

import entities.exceptions.NonexistentEntityException;
import entities.exceptions.PreexistingEntityException;
import entities.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import jpa.entities.Terorgan;

/**
 *
 * @author BirkovAY
 */
public class TerorganJpaController implements Serializable {

    public TerorganJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Terorgan terorgan) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(terorgan);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTerorgan(terorgan.getId()) != null) {
                throw new PreexistingEntityException("Terorgan " + terorgan + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Terorgan terorgan) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            terorgan = em.merge(terorgan);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = terorgan.getId();
                if (findTerorgan(id) == null) {
                    throw new NonexistentEntityException("The terorgan with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Terorgan terorgan;
            try {
                terorgan = em.getReference(Terorgan.class, id);
                terorgan.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The terorgan with id " + id + " no longer exists.", enfe);
            }
            em.remove(terorgan);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Terorgan> findTerorganEntities() {
        return findTerorganEntities(true, -1, -1);
    }

    public List<Terorgan> findTerorganEntities(int maxResults, int firstResult) {
        return findTerorganEntities(false, maxResults, firstResult);
    }

    private List<Terorgan> findTerorganEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Terorgan.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Terorgan findTerorgan(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Terorgan.class, id);
        } finally {
            em.close();
        }
    }

    public int getTerorganCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Terorgan> rt = cq.from(Terorgan.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
