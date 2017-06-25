package com.veraplan.teacherWishlist.DataTransferObject;

import java.io.Serializable;
import java.util.Date;

public class AbsenceDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4842162873248676948L;

	private int id;
	private Date start;
	private Date end;
	private String comment;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
