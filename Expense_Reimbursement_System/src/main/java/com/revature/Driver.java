package com.revature;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.revature.controller.EmployeeController;
import com.revature.controller.ReimbursementController;
import com.revature.controller.StaticController;
import com.revature.model.Employee;
import com.revature.repository.EmployeeRepository;
import com.revature.service.EmployeeService;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class Driver {
	
	public static void main(String...main) {
		
		//I'm starting my server on port 5555
		Javalin app = Javalin.create().start(5555);
		
		app.get("/testConnection", ctx -> {
			ctx.req.getSession();
			ctx.status(200);
			ctx.result("Test Received!");
		});
		
		app.after(ctx -> {
			ctx.res.addHeader("Access-Control-Allow-Origin", "null");
		});
		
		app.config.addStaticFiles("/static", Location.CLASSPATH);
		
		EmployeeController employeeController = new EmployeeController(app);
		ReimbursementController reimbursementController = new ReimbursementController(app);
		StaticController.init(app);
	}
}
