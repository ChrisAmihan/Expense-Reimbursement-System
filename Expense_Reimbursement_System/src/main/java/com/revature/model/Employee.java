
package com.revature.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity

@Table(name = "hibernate_employee")
public class Employee {

	@Id
	@Column
	@GeneratedValue(generator = "employee_id_seq", strategy = GenerationType.AUTO)
	@SequenceGenerator(allocationSize = 1, name = "employee_id_seq", sequenceName = "employee_id_seq")
	private int employeeId;
	@Column
	private String name;
	@Column
	private String employeeType;
	@Column
	private String employeeLogin;
	@Column
	private String employeePass;
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Employee(int employeeId, String name) {
		super();
		this.employeeId = employeeId;
		this.name = name;
	}
	
	public Employee(String employeeLogin, String employeePass) {
		super();
		this.employeeLogin = employeeLogin;
		this.employeePass = employeePass;
	}
	
	public Employee(int employeeId, String name, String employeeType, String employeeLogin, String employeePass) {
		super();
		this.employeeId = employeeId;
		this.name = name;
		this.employeeType = employeeType;
		this.employeeLogin = employeeLogin;
		this.employeePass = employeePass;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getEmployeeLogin() {
		return employeeLogin;
	}

	public void setEmployeeLogin(String employeeLogin) {
		this.employeeLogin = employeeLogin;
	}

	public String getEmployeePass() {
		return employeePass;
	}

	public void setEmployeePass(String employeePass) {
		this.employeePass = employeePass;
	}

	@Override
	public int hashCode() {
		return Objects.hash(employeeId, employeeLogin, employeePass, employeeType, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return employeeId == other.employeeId && Objects.equals(employeeLogin, other.employeeLogin)
				&& Objects.equals(employeePass, other.employeePass) && Objects.equals(employeeType, other.employeeType)
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", name=" + name + ", employeeType=" + employeeType
				+ ", employeeLogin=" + employeeLogin + ", employeePass=" + employeePass + "]";
	}
}
