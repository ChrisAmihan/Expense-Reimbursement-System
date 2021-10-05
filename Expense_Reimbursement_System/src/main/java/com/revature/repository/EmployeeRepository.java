package com.revature.repository;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.model.Employee;
import com.revature.util.HibernateSessionFactory;

//This is my DAO layer. It has been rewritten with Hibernate.
public class EmployeeRepository {

	public List<Employee> findAll(){
		
		List<Employee> employees = null;
		
		Session s = null;
		Transaction tx = null;
		
		try {
			s = HibernateSessionFactory.getSession();
			tx = s.beginTransaction();
			employees = s.createQuery("FROM Employee", Employee.class).getResultList();//HQL notation "FROM Recipe" refers to the Recipe above
			tx.commit();
		}catch(HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		}finally{
			s.close();
		}
		
		return employees;
	}
	
	// This method persists a new record to your DB.
	public void save(Employee employee) {
		Session session = null; //sessions allow you to perform work on your DB
		Transaction tx = null; //transaction allows you to finalize and/or revert changes to the DB
		
		try {
			session = HibernateSessionFactory.getSession();
			tx = session.beginTransaction();
			session.save(employee);//Persistent State
			tx.commit();
		}catch(HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();//Detached State
		}
	}
	
	public Employee findByName(String name) {
		
		Employee retrievedEmployee = null;
		Session s = null;
		Transaction tx = null;
		
		try {
			s = HibernateSessionFactory.getSession();
			tx = s.beginTransaction();
			CriteriaBuilder cb = s.getCriteriaBuilder();//Builder allows us to construct queries
			CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);//This is an object representation of a query with restrictions
			
			Root<Employee> root = cq.from(Employee.class);//select * from ROOT
			
			cq.select(root).where(cb.equal(root.get("name"), name));
			
			Query<Employee> query = s.createQuery(cq);//Create a standard query from CriteriaQuery
			
			retrievedEmployee = query.uniqueResult();//if using getSingleResult make sure you dont have duplicates, otherwise use uniqueResult
			
			tx.commit();
			
		}catch(HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		}finally {
			s.close();
		}
		
		return retrievedEmployee;
	}	
	
	public Employee findById(int Id) {
		
		Employee retrievedEmployee = null;
		Session s = null;
		Transaction tx = null;
		
		try {
			s = HibernateSessionFactory.getSession();
			tx = s.beginTransaction();
			CriteriaBuilder cb = s.getCriteriaBuilder();//Builder allows us to construct queries
			CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);//This is an object representation of a query with restrictions
			
			Root<Employee> root = cq.from(Employee.class);//select * from ROOT
			
			cq.select(root).where(cb.equal(root.get("employeeId"), Id));
			
			Query<Employee> query = s.createQuery(cq);//Create a standard query from CriteriaQuery
			
			retrievedEmployee = query.uniqueResult();//if using getSingleResult make sure you dont have duplicates, otherwise use uniqueResult
			
			tx.commit();
			
		}catch(HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		}finally {
			s.close();
		}
		
		return retrievedEmployee;
	}
	
	public int validateInt(int Id, String password) {
		
		Transaction tx = null;
		Session s = null;
		Employee retrievedEmployee = null;
		
		try {
			s = HibernateSessionFactory.getSession();
			tx = s.beginTransaction();
			retrievedEmployee = s.get(Employee.class,Id);
			
			if((retrievedEmployee.getEmployeePass().equals(password))&&(retrievedEmployee.getEmployeeType().equals("Manager"))) {
				
				return 1;
			} else if((retrievedEmployee.getEmployeePass().equals(password))&&(retrievedEmployee.getEmployeeType().equals("Employee"))) {
				return 2;
			} 
		}catch(HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		}catch(Exception e) {
			tx.rollback();
			System.out.println("Incorrect Credentials");
		}finally {
			s.close();
		}
			return 0;	
	}
	
	public Employee validateEmp(int Id, String password) {
		Transaction tx = null;
		Session s = null;
		Employee retrievedEmployee = null;
		Employee notEmployee = null;
		try {
			s = HibernateSessionFactory.getSession();
			tx = s.beginTransaction();
			retrievedEmployee = s.get(Employee.class,Id);
			
			if((retrievedEmployee.getEmployeePass().equals(password))){
				return retrievedEmployee;
			}
			
//			if((retrievedEmployee.getEmployeePass().equals(password))&&(retrievedEmployee.getEmployeeType().equals("Manager"))) {
//				return retrievedEmployee;
//			} else if((retrievedEmployee.getEmployeePass().equals(password))&&(retrievedEmployee.getEmployeeType().equals("Employee"))) {
//				return retrievedEmployee;
//			} 
		}catch(HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		}catch(Exception e) {
			tx.rollback();
			System.out.println("Incorrect Credentials");
		}finally {
			s.close();
		}
		System.out.println("This happened");
		return notEmployee;
	}
}
