package loan;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SSN implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2186494815176523547L;
	private String areaNumber; 
	private String groupCode;
	private String serialNumber;
	
	private void parseSSN(String number) {
		int firstDash = number.indexOf('-');
		if (firstDash >= 1) {
			areaNumber = number.substring(0, firstDash);
			int secondDash = number.indexOf('-', firstDash+1);
			if (secondDash >= firstDash+2) {
				groupCode = number.substring(firstDash+1, secondDash);
				serialNumber = number.substring(secondDash+1);
			} 
			else {
				groupCode = number.substring(firstDash+1, Math.min(number.length(), firstDash+3));
				serialNumber = number.substring(Math.min(number.length(), firstDash+3), number.length());
			}
		}
		else {
			areaNumber = number.substring(0, Math.min(number.length(), 3));
			groupCode = number.substring(Math.min(number.length(), 3), Math.min(number.length(), 5));
			serialNumber = number.substring(Math.min(number.length(), 5), number.length());
		}
	}

	@SuppressWarnings("unused")
	private SSN() {
	}

	public SSN(String number) {
		parseSSN(number);
	}
	
	public SSN(String areaNumber, String groupCode, String serialNumber) {
		this.areaNumber = areaNumber;
		this.groupCode = groupCode;
		this.serialNumber = serialNumber;
	}
	
	@JsonIgnore public int getDigits() {
		return areaNumber.length() + groupCode.length() + serialNumber.length();
	}

	public String getAreaNumber() {
		return areaNumber;
	}
	
	public void setAreaNumber(String areaNumber) {
		this.areaNumber = areaNumber;
	}
	
	public String getGroupCode() {
		return groupCode;
	}
	
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	@JsonIgnore public String getFullNumber() {
		return getAreaNumber() + "-" + getGroupCode() + "-" + getSerialNumber();
	}
	
	@JsonIgnore public String toString() {
		return this.getFullNumber();
	}
}
