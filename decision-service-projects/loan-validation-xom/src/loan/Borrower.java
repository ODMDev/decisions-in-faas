/*
* Licensed Materials - Property of IBM
* 5725-B69 5655-Y17 5655-Y31 5724-X98 5724-Y15 5655-V82 
* Copyright IBM Corp. 1987, 2016. All Rights Reserved.
*
* Note to U.S. Government Users Restricted Rights: 
* Use, duplication or disclosure restricted by GSA ADP Schedule 
* Contract with IBM Corp.
*/

package loan;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Borrower implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 888265255939699136L;
	private String   firstName;
	private String   lastName;
	private Calendar birth;
	private SSN      SSN;
	private int      yearlyIncome;
	private String   zipCode ;
	private int      creditScore;
	private Borrower spouse;

	/**
	 * @return Returns the creditScore.
	 */
	public int getCreditScore() {
		return creditScore;
	}
	/**
	 * @param creditScore The creditScore to set.
	 */
	public void setCreditScore(int creditScore) {
		this.creditScore = creditScore;
	}
	
	private Bankruptcy latestBankruptcy;

	public Borrower() {
		//Default date
		Date birthDate = new Date();
		Calendar cal = Calendar.getInstance();
		birthDate = DateUtil.dateAsDay(birthDate);
		cal.setTime(birthDate);
		this.birth = cal;
		
		//Default SSN
		this.SSN = new SSN("408-414-10000");
	}
	
	public Borrower(String firstName, String lastName, 
			Date birthDate,	String SSNCode) {
		this.firstName = firstName;
		this.lastName = lastName;
		Calendar cal = Calendar.getInstance();
		birthDate = DateUtil.dateAsDay(birthDate);
		cal.setTime(birthDate);
		this.birth = cal;
		this.SSN = new SSN(SSNCode);
	}
	
	public String toString() {
		String msg = Messages.getMessage("borrower");
		Object[] arguments = { firstName, lastName,
				DateUtil.format(getBirthDate()), getSSN() };
		String result = MessageFormat.format(msg, arguments);
	     
	     if (zipCode != null) {
			Object[] zipCodeObj = { getZipCode() };
			String zipCodeStr = MessageFormat.format(Messages
					.getMessage("zipCode"), zipCodeObj);
			result = result + "\n" + "   - " + zipCodeStr;
	     }
	     
	     if (yearlyIncome != 0) {
			Object[] incomeObj = { getYearlyIncome() };
			String incomeStr = MessageFormat.format(Messages
					.getMessage("yearlyIncome"), incomeObj);
			result = result + "\n" + "   - " + incomeStr;
	     }
	     
	     if (creditScore>0) {
			Object[] creditScoreObj = { getCreditScore() };
			String creditScoreStr = MessageFormat.format(Messages
					.getMessage("creditScore"), creditScoreObj);
			result = result + "\n" + "   - " + creditScoreStr;
	     }
	     
	     if (hasLatestBankrupcy()) {
			Object[] bankruptcyObj = {
					DateUtil.format(getLatestBankruptcyDate()),
					getLatestBankruptcyReason(), getLatestBankruptcyChapter() };
			String bankruptcyStr = MessageFormat.format(Messages
					.getMessage("bankruptcy"), bankruptcyObj);
			result = result + "\n" + "   - " + bankruptcyStr;
	     }
	     
		return result;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonIgnore public Date getBirthDate() {
		return birth.getTime();
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public SSN getSSN() {
		return this.SSN;
	}
	
	public void setSSN(SSN theSSN) {
		this.SSN = theSSN;
	}

	@JsonIgnore public String getSSNCode() {
		return SSN.toString();
	}

	public int getYearlyIncome() {
		return yearlyIncome;
	}

	public void setYearlyIncome(int income) {
		this.yearlyIncome = income;
	}

	@JsonIgnore public boolean hasLatestBankrupcy() {
		return latestBankruptcy != null;
	}

	@JsonIgnore public Date getLatestBankruptcyDate() {
		return latestBankruptcy.getDate();
	}

	@JsonIgnore public String getLatestBankruptcyReason() {
		return latestBankruptcy.getReason();
	}
	// Among Unemployment; Large medical expenses; Seriously overextended credit; Marital problems, and Other large unexpected expenses

	@JsonIgnore public int getLatestBankruptcyChapter() {
		return latestBankruptcy.getChapter();
	}
	
	public void setLatestBankruptcy(Date date, int chapter, String reason) {
		this.latestBankruptcy = new Bankruptcy(date, chapter, reason);
	}
	
	public void setSpouse(Borrower spouse) {
	    this.spouse = spouse;
	}
	
	public Borrower getSpouse() {
	    return spouse;
	}

};
