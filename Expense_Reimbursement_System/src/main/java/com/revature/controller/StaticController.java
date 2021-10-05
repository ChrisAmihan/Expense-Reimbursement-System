package com.revature.controller;

import java.sql.SQLException;

import com.revature.model.Reimbursement;
import com.revature.service.ReimbursementService;

import io.javalin.Javalin;
import io.javalin.http.Context;


public class StaticController {

	private static Javalin javalin;
	
	public static void init(Javalin app) {
		
		javalin = app;
		
		app.put("/reimbursement/updateById3/:id/:status", StaticController::updateById3);
		
	}
	
	public static void updateById3(Context ctx) {
		
		ReimbursementService reimbursementService = new ReimbursementService();

		reimbursementService.updateById(Integer.parseInt(ctx.pathParam("id")), ctx.pathParam("status"));
		
		ctx.status(200);	
		
	}
	
	
	
}
