package com.revature.controller;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import java.util.Set;
import java.util.HashSet;
import com.revature.model.Employee;
import com.revature.model.Reimbursement;

import javax.servlet.http.HttpSession;
import com.revature.service.EmployeeService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class EmployeeController {

	private EmployeeService employeeService;
	private Employee employee;
	
	public EmployeeController(Javalin app) {
		this.employeeService = new EmployeeService();
		
		app.routes(() -> {
			path("/employee", () -> {
				path("/all", () -> {
					get(findAllEmployees);
				});
				path("/new", () -> {
					post(saveEmployee);
				});
				path("/update/:id", () -> {
					post(updateEmployeeById);
				});
				path("/name/:name", () -> {
					get(employeeByName);
				});
				path("/id/:id", () -> {
					get(employeeById);
				});
				path("/login", () -> {
					post(validate);
				});
				path("/logout", () -> {
					post(logout);
				});		
			});
		});
	}
	
	private Handler validate = ctx -> {
		ctx.req.getSession();
		
		EmployeeService employeeService = new EmployeeService();
		
		Employee validate = employeeService.ValidateEmp(Integer.parseInt(ctx.req.getParameter("employeeId")), ctx.req.getParameter("employeePass"));
		
		HttpSession s = ctx.req.getSession(false);
			
		try {
			if(validate.getEmployeeType().equals("Manager")){
				s.setAttribute("employee", validate);
				ctx.redirect("/Pages/manager.html");
			} else if(validate.getEmployeeType().equals("Employee")){	
				s.setAttribute("employee", validate);
				ctx.redirect("/Pages/reimbursements.html");
			} else {
				ctx.redirect("/Pages/login.html");
			}
		} catch(Exception e) {
			e.printStackTrace();
			ctx.redirect("/Pages/login.html");
		}
	};
	
	
	private Handler findAllEmployees = ctx -> {
		//To check for the existence of a session:
		HttpSession session = ctx.req.getSession(false);
		
		if(session != null)
			ctx.json(this.employeeService.findAll());
		else
			ctx.res.getWriter().write("{\"error\": \"You do not have a session.\"}");
		};
		
		
	/**
	 * This method will save a new employee into the database.
	 */
	private Handler saveEmployee = ctx -> {
		
		Employee employee = new Employee(1, 
				ctx.req.getParameter("employee_name"),
				ctx.req.getParameter("employee_type"),
				ctx.req.getParameter("employee_login"),
				ctx.req.getParameter("employee_pass"));
		
		this.employeeService.save(employee);
		
		ctx.redirect("/Pages/employee.html");
	};
	
	//TODO
	private Handler updateEmployeeById = ctx -> {
		
	};
	
	private Handler employeeByName = ctx -> {
		Employee retrievedEmployee = this.employeeService.findByName(ctx.pathParam("name"));
		ctx.json(retrievedEmployee);
	};
	
	private Handler employeeById = ctx -> {
		Employee retrievedEmployee = this.employeeService.serviceFindById(Integer.parseInt(ctx.pathParam("id")));
		ctx.json(retrievedEmployee);
	};
	
	//TODO: May need more logic?
	private Handler logout = ctx -> {
		//If you pass in "false", an existing session is checked for.
		HttpSession session = ctx.req.getSession(false);
		if(session != null) session.invalidate();
		ctx.redirect("/Pages/login.html");
	};
	
}
