package com.revature.service;

import java.util.List;

import com.revature.model.Reimbursement;
import com.revature.repository.ReimbursementRepository;

public class ReimbursementService {

private ReimbursementRepository reimbursementRepository;
	
	public ReimbursementService() {
		this.reimbursementRepository = new ReimbursementRepository();
	}
	
	public List<Reimbursement> findAll(){
		return this.reimbursementRepository.findAll();
	}
	
	public void save(Reimbursement reimbursement) {
		this.reimbursementRepository.save(reimbursement);
	}
	
	public Reimbursement findById(int reimbursementId) {
		return this.reimbursementRepository.findById(reimbursementId);
	}
	
	public Reimbursement serviceFindByStatus(String status) {
		return (Reimbursement) this.reimbursementRepository.findAllByStatus(status);
	}
	
	public List<Reimbursement> findAllById(int Id) {
		return this.reimbursementRepository.findAllById(Id);
	}
	
	public void updateById(int id, String comment) {
		this.reimbursementRepository.updateById(id, comment);
	}
	
}
