package com.ibm.odm.spark.miniloan.res;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class DecisionTrace {
	@JsonIgnore private BatchDecisionTrace batchDecisionTrace;
	public HashMap<String, Long> ruleCoverageMap = new HashMap<String, Long>();
	
	public DecisionTrace(BatchDecisionTrace batchDecisionTrace) {
		this.batchDecisionTrace = batchDecisionTrace;
		
	}

}
