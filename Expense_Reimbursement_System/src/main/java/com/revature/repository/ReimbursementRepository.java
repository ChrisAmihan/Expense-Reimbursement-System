package com.revature.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.util.HibernateSessionFactory;

public class ReimbursementRepository {
	
	public List<Reimbursement> findAll(){
		
		List<Reimbursement> reimbursements = null;
		
		Session s = null;
		Transaction tx = null;
		
		try {
			s = HibernateSessionFactory.getSession();
			tx = s.beginTransaction();
			reimbursements = s.createQuery("FROM Reimbursement", Reimbursement.class).getResultList();
			tx.commit();
		}catch(HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		}finally{
			s.close();
		}
		
		return reimbursements;
	}
	
	public List<Reimbursement> findAllById(int Id){
		
		List<Reimbursement> queryList = null;
		List<Reimbursement> newList = new ArrayList<>();
		Session s = null;
		Transaction tx = null;
		
		try {
			s = HibernateSessionFactory.getSession();
			tx = s.beginTransaction();
			
			queryList = s.createQuery("FROM Reimbursement", Reimbursement.class).getResultList();
			
			for(int i = 0; i < queryList.size(); i++) {
				if(queryList.get(i).getEmployee().getEmployeeId()==Id) {
					newList.add(queryList.get(i));	
				}
			}	
			tx.commit();
		}catch(HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		}finally {
			s.close();
		}
		
		return newList;
	}	

	
	public List<Reimbursement> findAllByStatus(String status){
		
		List<Reimbursement> reimbursements = new ArrayList<>();;
		
		//TODO

		return reimbursements;
	}
	
	// This method persists a new record to your DB.
	public void save(Reimbursement reimbursement) {
		
		
		Session session = null; //sessions allow you to perform work on your DB
		Transaction tx = null; //transaction allows you to finalize and/or revert changes to the DB
		
		try {
			session = HibernateSessionFactory.getSession();
			tx = session.beginTransaction();
			session.save(reimbursement);//Persistent State
			tx.commit();
		}catch(HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();//Detached State
		}
	}
	
	public void updateById(int id, String comment) {
		Session s = null; //sessions allow you to perform work on your DB
		Transaction tx = null; //transaction allows you to finalize and/or revert changes to the DB
		
		Reimbursement reimbursement = null;
		
		reimbursement = findById(id);
		
		reimbursement.setReimbursementStatus(comment);
		
		try {
			s = HibernateSessionFactory.getSession();
			tx = s.beginTransaction();
			
//			CriteriaBuilder cb = s.getCriteriaBuilder();//Builder allows us to construct queries
//			CriteriaUpdate<Reimbursement> update = cb.createCriteriaUpdate(Reimbursement.class);//This is an object representation of a query with restrictions
//			
//			Root e = update.from(Reimbursement.class);//select * from ROOT
//			
//			update.set("", null);
			
			s.update(reimbursement);//Persistent State
			tx.commit();
		}catch(HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		}finally {
			s.close();//Detached State
		}
	}
	
	public Reimbursement findById(int reimbursementId) {
		
		Reimbursement retrievedReimbursement = null;
		Session s = null;
		Transaction tx = null;
		
		try {
			s = HibernateSessionFactory.getSession();
			tx = s.beginTransaction();
			CriteriaBuilder cb = s.getCriteriaBuilder();//Builder allows us to construct queries
			CriteriaQuery<Reimbursement> cq = cb.createQuery(Reimbursement.class);//This is an object representation of a query with restrictions
			
			Root<Reimbursement> root = cq.from(Reimbursement.class);//select * from ROOT
			
			cq.select(root).where(cb.equal(root.get("reimbursementId"), reimbursementId));
			
			Query<Reimbursement> query = s.createQuery(cq);//Create a standard query from CriteriaQuery
			
			retrievedReimbursement = query.uniqueResult();//if using getSingleResult make sure you dont have duplicates, otherwise use uniqueResult
			
			tx.commit();
			
		}catch(HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		}finally {
			s.close();
		}
		
		return retrievedReimbursement;
	}	
	
}
