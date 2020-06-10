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

import java.util.HashMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONSerializer {

	public JSONSerializer() {
		
	}
	
	public static void main(String[] args) {
		JSONSerializer jsonSerialier = new JSONSerializer();
		MiniLoanRequest request = new MiniLoanRequest("John Doe", 550, 80000,
				250000, 240, 0.05d);
		MiniLoanResponse response  = null;
		ObjectMapper mapper = new ObjectMapper();

		MiniLoanDecision decision = new MiniLoanDecision(request, response);
		//Decision
		System.out.println(jsonSerialier.serialize(decision));
		
		//Request
		System.out.println(request.serializeAsJSON());

	}
	
	public String serialize(MiniLoanDecision decision) {
		JSONSerializer runner = new JSONSerializer();
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("decision", decision);
		//results.put("request", decision.request);
		//results.put("answer", decision.response);
		String jsonInString = null;
		try {
			// Convert object to JSON string and save into a file directly
			// mapper.writeValue(System.out, request);

			// Convert object to JSON string
			jsonInString = mapper.writeValueAsString(results);

			// Convert object to JSON string and pretty print
			// jsonInString =
			// mapper.writerWithDefaultPrettyPrinter().writeValueAsString(staff);
			// System.out.println(jsonInString);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonInString;
	}
	
	public String serialize(MiniLoanRequest request) {
		JSONSerializer runner = new JSONSerializer();
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> results = new HashMap<String, Object>();
		results.put("request", request);
		String jsonInString = null;
		try {
			// Convert object to JSON string and save into a file directly
			// mapper.writeValue(System.out, request);

			// Convert object to JSON string
			jsonInString = mapper.writeValueAsString(results);

			// Convert object to JSON string and pretty print
			// jsonInString =
			// mapper.writerWithDefaultPrettyPrinter().writeValueAsString(staff);
			// System.out.println(jsonInString);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonInString;
	}

}

