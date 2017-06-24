package com.veraplan.teacherWishlist.Model;

import java.sql.Date;
import java.time.LocalDate;

import com.veraplan.teacherWishlist.Entities.Absence; 

public class VacationItem {

	private LocalDate startDate;
	private LocalDate endDate;
	private String comment;

	public VacationItem(LocalDate startDate, LocalDate endDate, String comment) {

		this.startDate = startDate;
		this.endDate = endDate;
		this.comment = comment; 
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Absence toAbsenceEntity(){
		Absence newAbsence = new Absence();
		newAbsence.setComment(this.getComment());
		newAbsence.setStart(Date.valueOf(this.getStartDate()));
		newAbsence.setEnd(Date.valueOf(this.getEndDate()));
		return newAbsence;
	}

}
