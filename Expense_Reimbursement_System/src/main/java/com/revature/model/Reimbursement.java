package com.revature.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity

@Table(name = "hibernate_reimbursements")
public class Reimbursement {

	@Id
	@Column
	@GeneratedValue(generator = "reimbursement_id_seq", strategy = GenerationType.AUTO)
	@SequenceGenerator(allocationSize = 1, name = "reimbursement_id_seq", sequenceName = "reimbursement_id_seq")
	private int reimbursementId;
	@Column
	private double reimbursementAmt;
	@Column
	private String reimbursementType;
	@Column
	private String reimbursementStatus;
	@Column
	private String reimbursementComment;
	
	@ManyToOne
	private Employee employee;//employees can have many reimbursements

	public Reimbursement(int reimbursementId, double reimbursementAmt, String reimbursementType,
			String reimbursementStatus, String reimbursementComment, Employee employee) {
		super();
		this.reimbursementId = reimbursementId;
		this.reimbursementAmt = reimbursementAmt;
		this.reimbursementType = reimbursementType;
		this.reimbursementStatus = reimbursementStatus;
		this.reimbursementComment = reimbursementComment;
		this.employee = employee;
	}

	public Reimbursement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getReimbursementId() {
		return reimbursementId;
	}

	public void setReimbursementId(int reimbursementId) {
		this.reimbursementId = reimbursementId;
	}

	public double getReimbursementAmt() {
		return reimbursementAmt;
	}

	public void setReimbursementAmt(double reimbursementAmt) {
		this.reimbursementAmt = reimbursementAmt;
	}

	public String getReimbursementType() {
		return reimbursementType;
	}

	public void setReimbursementType(String reimbursementType) {
		this.reimbursementType = reimbursementType;
	}

	public String getReimbursementStatus() {
		return reimbursementStatus;
	}

	public void setReimbursementStatus(String reimbursementStatus) {
		this.reimbursementStatus = reimbursementStatus;
	}

	public String getReimbursementComment() {
		return reimbursementComment;
	}

	public void setReimbursementComment(String reimbursementComment) {
		this.reimbursementComment = reimbursementComment;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public int hashCode() {
		return Objects.hash(employee, reimbursementAmt, reimbursementComment, reimbursementId, reimbursementStatus,
				reimbursementType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		return Objects.equals(employee, other.employee)
				&& Double.doubleToLongBits(reimbursementAmt) == Double.doubleToLongBits(other.reimbursementAmt)
				&& Objects.equals(reimbursementComment, other.reimbursementComment)
				&& reimbursementId == other.reimbursementId
				&& Objects.equals(reimbursementStatus, other.reimbursementStatus)
				&& Objects.equals(reimbursementType, other.reimbursementType);
	}

	@Override
	public String toString() {
		return "Reimbursements [reimbursementId=" + reimbursementId + ", reimbursementAmt=" + reimbursementAmt
				+ ", reimbursementType=" + reimbursementType + ", reimbursementStatus=" + reimbursementStatus
				+ ", reimbursementComment=" + reimbursementComment + ", employee=" + employee + "]";
	}
	
}
