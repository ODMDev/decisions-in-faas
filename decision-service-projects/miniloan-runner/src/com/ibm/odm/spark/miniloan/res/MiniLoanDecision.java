package com.ibm.odm.spark.miniloan.res;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ilog.rules.res.session.ruleset.IlrRuleInformation;

public class MiniLoanDecision {

	public MiniLoanDecision() {
	}
	
	public MiniLoanDecision(MiniLoanRequest request, MiniLoanResponse response) {
		this.id = request.hashCode() + "-" + response.hashCode();
		this.request = request;
		this.response = response;
	}

	public String id;
	public MiniLoanRequest request;
	public MiniLoanResponse response;
	
	public String serializeAsJSON() {
		JSONSerializer jsonSerializer = new JSONSerializer();
		String json = jsonSerializer.serialize(this);
		return json;
	}

	public static MiniLoanDecision deserializeAsJSON(String decisionJSON) {
		ObjectMapper mapper = new ObjectMapper();
		MiniLoanDecision decisionFromJSON = null;
		try {
			//ToDo Remove this shortcut
			//mapper.writerWithDefaultPrettyPrinter();
			
			String originToken = "{\"decision:";
			decisionJSON = decisionJSON.substring(decisionJSON.indexOf(originToken) + originToken.length() + 2);
			decisionJSON = decisionJSON.substring(0, decisionJSON.lastIndexOf("}"));
			decisionFromJSON = mapper.readValue(decisionJSON, MiniLoanDecision.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return decisionFromJSON;
	}
	
	public static void main(String[] args) {
		MiniLoanDecision decision = new MiniLoanDecision();
		MiniLoanRequest decisionRequest = new MiniLoanRequest("John Doe", 600, 100000, 120000, 120, 0.05);
		decision.request = decisionRequest;

		
		
	}

}
