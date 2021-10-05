package com.revature.controller;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.revature.model.Employee;
import com.revature.service.EmployeeService;
import com.revature.service.ReimbursementService;

import com.revature.model.Reimbursement;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.text.DecimalFormat;
import java.math.RoundingMode;

public class ReimbursementController {

	private ReimbursementService reimbursementService;
	private DecimalFormat df2 = new DecimalFormat("#.##");
	
	public ReimbursementController(Javalin app) {
		this.reimbursementService = new ReimbursementService();
		
		app.put("/reimbursement/updateById/:id/:status", ReimbursementController::updateById2);
		
		app.routes(() -> {
			path("/reimbursement", () -> {
				path("/all", () -> {
					get(findAllReimbursements);
				});
				path("/AllById", () -> {
					get(getAllById);
				});
				path("/allByStatus/:status", () -> {
					get(findAllByStatus);
				});
				path("/new", () -> {
					post(saveReimbursement);
				});
				path("/reimbursementId/:id", () -> {
					get(findById);
				});
				path("/updateById/:id/:comment", () -> {
//					put(updateById);
				});
				path("/getStat/", () -> {
					get(getStat);
				});
				path("/getPendingAvg", () -> {
					get(getPendingAvg);
				});
			});
		});
	}
	
	public Handler getStat = ctx -> {
		
		List<Reimbursement> ri =  reimbursementService.findAll();
		double avg = 0;
		int count = 0;
		
		for(int i=0; i<ri.size(); i++) {
			
			if(ri.get(i).getReimbursementStatus().equals("Approved")) {
				avg += ri.get(i).getReimbursementAmt();
				count++;
			}
		}
		
		avg = avg/count;
		BigDecimal bd = new BigDecimal(avg).setScale(2, RoundingMode.HALF_UP);
		double newInput = bd.doubleValue();
		ctx.json(newInput);
		
	};
	
	public Handler getPendingAvg = ctx -> {
		
		List<Reimbursement> ri =  reimbursementService.findAll();
		double avg = 0;
		int count = 0;
		
		
		for(int i=0; i<ri.size(); i++) {
			
			if(ri.get(i).getReimbursementStatus().equals("Pending")) {
				avg += ri.get(i).getReimbursementAmt();
				count++;
			}
		}
		
		avg = avg/count;
		BigDecimal bd = new BigDecimal(avg).setScale(2, RoundingMode.HALF_UP);
		double newInput = bd.doubleValue();
		ctx.json(newInput);
		
	};


	public static void updateById2(Context ctx) {
		
		String id = ctx.formParam("id");

		ReimbursementService reimbursementService = new ReimbursementService();

		Reimbursement ri = reimbursementService.findById(Integer.parseInt(id));
		
		System.out.println(ri.getReimbursementComment());
		
		ri.setReimbursementComment(ctx.pathParam("comment"));
		ctx.status(200);	
	}
	
	private Handler updateById = ctx -> {
		Reimbursement ri = this.reimbursementService.findById(Integer.parseInt(ctx.pathParam("id")));
		System.out.println(ri.getReimbursementComment());
		ri.setReimbursementComment(ctx.pathParam("comment"));
		ctx.status(200);
	};
	
	private Handler findAllReimbursements = ctx -> {
		
		//To check for the existence of a session:
		HttpSession session = ctx.req.getSession(false);
		
		if(session != null)
			ctx.json(this.reimbursementService.findAll());
		else
			ctx.res.getWriter().write("{\"error\": \"You do not have a session.\"}");
		};
		

	
	private Handler getAllById = ctx -> {
		
		HttpSession session = ctx.req.getSession(false);
		Employee emp = (Employee)session.getAttribute("employee");
		
		if(session!=null)
			ctx.json(this.reimbursementService.findAllById(emp.getEmployeeId()));
		else
			ctx.res.getWriter().write("{\"error\": \"You do not have a session.\"}");
	};
	
	private Handler findAllByStatus = ctx -> {
		
		ctx.json(this.reimbursementService.serviceFindByStatus(ctx.pathParam("status")));
	};
		
	/**
	 * This method is for saving form input from the front end into the database.	
	 */
	private Handler saveReimbursement = ctx -> {
		
		String reimbursement_status = "Pending";
		String reimbursement_comment = "Pending";
		HttpSession s = ctx.req.getSession(false);
		Employee emp = (Employee)s.getAttribute("employee");
		
		Reimbursement reimbursement = new Reimbursement(1, 
				Double.parseDouble(ctx.req.getParameter("reimbursement_amt")),
				ctx.req.getParameter("reimbursement_type"),
				reimbursement_status,
				reimbursement_comment,
				emp);
	
		this.reimbursementService.save(reimbursement);
		
		if(emp.getEmployeeType().equals("Employee")) {
			ctx.redirect("/Pages/reimbursements.html");
		} else {
			ctx.redirect("/Pages/manage_reimbursements.html");
		}
	};
	
	private Handler findById = ctx -> {
		Reimbursement retrievedReimbursement = this.reimbursementService.findById(Integer.parseInt(ctx.pathParam("id")));
		ctx.json(retrievedReimbursement);
	};
	
//	private Handler findAllById = ctx -> {
//	
//	HttpSession session = ctx.req.getSession(false);
//	Employee emp = (Employee)session.getAttribute("employee");
//	
//	if(session!=null)
//		ctx.json(this.reimbursementService.findAllById(Integer.parseInt(ctx.pathParam("id"))));
//	else
//		ctx.res.getWriter().write("{\"error\": \"You do not have a session.\"}");
//};

}
