package com.ibm.odm.spark.miniloan.res;

/*
 * Licensed Materials - Property of IBM 
 * 5724-X98 
 * (c) Copyright IBM Corp. 1987, 2010. All Rights Reserved. 
 * 
 * Note to U.S. Government Users Restricted Rights: 
 * Use, duplication or disclosure restricted by GSA ADP Schedule 
 * Contract with IBM Corp. 
 */

import ilog.rules.res.model.IlrAlreadyExistException;
import ilog.rules.res.model.IlrEngineType;
import ilog.rules.res.model.IlrFormatException;
import ilog.rules.res.model.IlrMutableRepository;
import ilog.rules.res.model.IlrMutableRuleAppInformation;
import ilog.rules.res.model.IlrMutableRulesetArchiveInformation;
import ilog.rules.res.model.IlrPath;
import ilog.rules.res.model.IlrRepositoryFactory;
import ilog.rules.res.model.IlrVersion;
import ilog.rules.res.session.IlrJ2SESessionFactory;
import ilog.rules.res.session.IlrSessionException;
import ilog.rules.res.session.IlrSessionRequest;
import ilog.rules.res.session.IlrSessionResponse;
import ilog.rules.res.session.IlrStatelessSession;
import ilog.rules.res.session.ruleset.IlrExecutionTrace;

import java.io.InputStream;
import java.util.Map;

import miniloan.*;

public class MiniLoanRESRunner {

	// private static IlrJ2SESessionFactory ruleSessionFactory = null;
	static private IlrJ2SESessionFactory ruleSessionFactory = null;

	static private String RulesetFileName = "/miniloan-890.dsar";
	static private String RuleAppName = "miniloanservice";
	static private String RuleAppVersion = "1.0";
	static private String RulesetName = "miniloanrules";
	static private String RulesetVersion = "1.0";

	private static IlrJ2SESessionFactory GetRuleSessionFactory() {
		if (ruleSessionFactory == null) {

			// Create the RES Session Factory
			IlrJ2SESessionFactory memoryJ2SEFactory = new IlrJ2SESessionFactory();
			ruleSessionFactory = memoryJ2SEFactory;
		}
		return ruleSessionFactory;
	}

	public static void main(String[] args) {
		MiniLoanRESRunner runner = new MiniLoanRESRunner();
		MiniLoanRequest request = new MiniLoanRequest("John Doe", 550, 80000,
				250000, 240, 0.05d);
		MiniLoanDecision decision = runner.execute(request);
	}

	public MiniLoanDecision executeAsString(String s) {

		MiniLoanRequest request = MiniLoanRequest.parseAsCSV(s);
		return execute(request);
	}

	public MiniLoanDecision execute(MiniLoanRequest request) {

		MiniLoanResponse response = execute3(request);

		System.out.print("Loan approved=" + response.loan.isApproved()
				+ " with a yearly repayment="
				+ response.loan.getYearlyRepayment() + " messages= "
				+ response.loan.getMessages());
		System.out.println(" executed in thread "
				+ Thread.currentThread().getName());

		return new MiniLoanDecision(request, response);
	}

	public MiniLoanResponse execute3(MiniLoanRequest request) {
		try {

			IlrJ2SESessionFactory sessionFactory = GetRuleSessionFactory();

			// Creating the decision request
			IlrSessionRequest sessionRequest = sessionFactory.createRequest();
			//String rulesetPath = "/miniloanservice/miniloanrules";
			//String rulesetPath = "/Miniloan/Miniloan_ServiceRuleset";
			String rulesetPath = "/miniloan_ruleapp/validate_ruleset";
			
			sessionRequest.setRulesetPath(IlrPath.parsePath(rulesetPath));

			sessionRequest.setTraceEnabled(true);
			// sessionRequest.getTraceFilter().setInfoAllFilters(true);
			sessionRequest.getTraceFilter().setInfoRules(true);
			sessionRequest.getTraceFilter().setInfoRulesNotFired(true);

			Map<String, Object> inputParameters = sessionRequest
					.getInputParameters();
			inputParameters.put("loan", request.loan);
			inputParameters.put("borrower", request.borrower);

			// Creating the rule session
			IlrStatelessSession session = sessionFactory
					.createStatelessSession();

			// long t2 = System.currentTimeMillis();

			IlrSessionResponse response = session.execute(sessionRequest);
			// long t3 = System.currentTimeMillis();
			MiniLoanResponse miniLoanResponse = new MiniLoanResponse(response);
			return miniLoanResponse;

		} catch (Exception exception) {
			exception.printStackTrace(System.err);
		}
		return null;
	}

}
