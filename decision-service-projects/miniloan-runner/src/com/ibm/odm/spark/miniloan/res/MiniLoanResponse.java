package com.ibm.odm.spark.miniloan.res;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ilog.rules.res.session.IlrSessionResponse;
import ilog.rules.res.session.ruleset.IlrExecutionTrace;
import miniloan.Borrower;
import miniloan.Loan;

public class MiniLoanResponse {

	//Variable signature
		public Loan loan;
		
	//Technical signature
	@JsonIgnore public IlrExecutionTrace decisionTrace;
		
	public MiniLoanResponse() {
	}
	
	public MiniLoanResponse(IlrSessionResponse response) {
		Map<String, Object> outParameters = response.getOutputParameters();
		this.loan = (Loan) outParameters.get("loan");
		
		decisionTrace = response.getRulesetExecutionTrace();
	}

	
}
