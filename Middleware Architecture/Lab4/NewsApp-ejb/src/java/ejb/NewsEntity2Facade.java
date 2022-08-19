/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Ronila
 */
@Stateless
public class NewsEntity2Facade {

    @PersistenceContext(unitName = "NewsApp-ejbPU")
    private EntityManager em;

    @PostConstruct
    public void PostConstruct() {
        System.out.println("NewsApp Initialize");
    }

    public void create(NewsEntity2 newsEntity) {
        em.persist(newsEntity);
    }

    public void edit(NewsEntity2 newsEntity) {
        em.merge(newsEntity);
    }

    public void remove(NewsEntity2 newsEntity) {
        em.remove(em.merge(newsEntity));
    }

    public NewsEntity2 find(Object id) {
        return em.find(NewsEntity2.class, id);
    }

    public List<NewsEntity2> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(NewsEntity2.class));
        return em.createQuery(cq).getResultList();
    }

    public List<NewsEntity2> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(NewsEntity2.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<NewsEntity2> rt = cq.from(NewsEntity2.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

//    public List<NewsEntity2> findAllSortedDesc() {
//        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//        cq.select(cq.from(NewsEntity2.class));
//        List <NewsEntity2>entityList = em.createQuery(cq).getResultList();
//        Collections.sort(entityList, new NewsEntityComparator());
//        return entityList;
//    }

}
