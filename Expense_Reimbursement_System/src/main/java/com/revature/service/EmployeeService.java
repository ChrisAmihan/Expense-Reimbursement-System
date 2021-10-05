package com.revature.service;

import java.util.List;

import com.revature.model.Employee;
import com.revature.repository.EmployeeRepository;

public class EmployeeService {

private EmployeeRepository employeeRepository;
	
	public EmployeeService() {
		this.employeeRepository = new EmployeeRepository();
	}
	
	public List<Employee> findAll(){
		return this.employeeRepository.findAll();
	}
	
	public void save(Employee employee) {
		this.employeeRepository.save(employee);
	}
	
	public Employee findByName(String name) {
		return this.employeeRepository.findByName(name);
	}
	
	public Employee serviceFindById(int Id) {
		return this.employeeRepository.findById(Id);
	}
	
	public Employee ValidateEmp(int Id, String password) {
		return this.employeeRepository.validateEmp(Id, password);
	}
	
//	public List<Recipe> findAllRecipesAlphabetical(){
//	List<Recipe> allRecipes = this.recipeRepository.findAll();
//	return null;
//}
	
}
