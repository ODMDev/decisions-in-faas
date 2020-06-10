package com.ibm.odm.spark.miniloan.res;

import java.io.Serializable;

import miniloan.Borrower;
import miniloan.Loan;

public class MiniLoanRequest implements Serializable  {

	private static final long serialVersionUID = 1L;
	//Operation signature
	public Borrower borrower;
	public Loan loan;
	
	public MiniLoanRequest() {
	}
	
	public Borrower getBorrower() {
		return borrower;
	}
	
	public Loan getLoan() {
		return loan;
	}
	
	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}
	
	public void setLoan(Loan loan) {
		this.loan = loan;
	}
	
	
	public MiniLoanRequest(Borrower borrower, Loan loan) {
		this.borrower = borrower;
		this.loan = loan;
	}
	
	public MiniLoanRequest(String borrowerName, int borrowerCreditScore, int borrowerYearlyIncome, int loanAmount, int loanDuration, double yearlyInterestRate) {
		this.borrower = new Borrower(borrowerName, borrowerCreditScore,
				borrowerYearlyIncome);
		this.loan = new Loan(loanAmount, loanDuration, yearlyInterestRate);
	}
	
	public static MiniLoanRequest parseAsCSV(String s) {
		String[] tokens = s.split(",");

		String borrowerName = tokens[0];
		int borrowerCreditScore = Integer.parseInt(tokens[1].trim());
		int borrowerYearlyIncome = Integer.parseInt(tokens[2].trim());
		int loanAmount = Integer.parseInt(tokens[3].trim());
		int loanDuration = Integer.parseInt(tokens[4].trim());
		double yearlyInterestRate = Double.parseDouble(tokens[5].trim());

		Borrower borrower = new Borrower(borrowerName, borrowerCreditScore,
				borrowerYearlyIncome);
		Loan loan = new Loan(loanAmount, loanDuration, yearlyInterestRate);
		MiniLoanRequest request = new MiniLoanRequest(borrower, loan);

		return request;
	}

	public String serializeAsJSON() {
		JSONSerializer jsonSerializer = new JSONSerializer();
		String json = jsonSerializer.serialize(this);
		return json;
	}

}
