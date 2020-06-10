package loan;

import java.util.Date;

public class Bankruptcy implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3107842066700686170L;
	private Date date;
	private int chapter;
	private String reason;

	@SuppressWarnings("unused")
	private Bankruptcy() {
	}

	public Bankruptcy(Date date, int chapter, String reason) {
		this.date = date;
		this.chapter = chapter;
		this.reason = reason;
	}

	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public int getChapter() {
		return this.chapter;
	}
	
	public void setChapter(int chapter) {
		this.chapter = chapter;
	}
}
