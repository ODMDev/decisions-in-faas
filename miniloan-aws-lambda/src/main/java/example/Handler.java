package example;

import com.amazonaws.services.lambda.runtime.Context;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.ibm.odm.spark.miniloan.res.MiniLoanDecision;
import com.ibm.odm.spark.miniloan.res.MiniLoanRESRunner;
import com.ibm.odm.spark.miniloan.res.MiniLoanRequest;

import java.util.Map;

// Handler value: example.Handler
public class Handler implements RequestHandler<Map<String, String>, String> {
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Override
	public String handleRequest(Map<String, String> event, Context context) {
		LambdaLogger logger = context.getLogger();
		String response = new String("200 OK");
		// log execution details
		logger.log("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()));
		logger.log("CONTEXT: " + gson.toJson(context));
		// process event
		logger.log("EVENT: " + gson.toJson(event));
		logger.log("EVENT TYPE: " + event.getClass().toString());

		try {
			String borrowerName = event.get("name");
			int borrowerCreditScore = Integer.parseInt(event.get("creditScore"));
			int borrowerIncome = Integer.parseInt(event.get("income"));
			int loanAmout = Integer.parseInt(event.get("amount"));
			int loanDuration = Integer.parseInt(event.get("duration"));
			float loanRate = Float.parseFloat(event.get("rate"));

			// Using input parameter map to automate a decision making
			MiniLoanRESRunner runner = new MiniLoanRESRunner();
			MiniLoanRequest request = new MiniLoanRequest(borrowerName, borrowerCreditScore, borrowerIncome, loanAmout,
					loanDuration, loanRate);
			MiniLoanDecision decision = runner.execute(request);
			response += " : " + decision.serializeAsJSON();
		} catch (Exception ex) {
			response += " Error in reading parameters";
		}

		return response;
	}
}