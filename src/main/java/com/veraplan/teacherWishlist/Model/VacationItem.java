package com.veraplan.teacherWishlist.Model;

import java.sql.Date;
import java.time.LocalDate;

import com.veraplan.teacherWishlist.Entities.Absence; 

/**
 * VacationItem is representing a single vacation
 * @author Lukas Stuckstette
 */
public class VacationItem {

	private LocalDate startDate;
	private LocalDate endDate;
	private String comment;

	/**
	 * constructor for VacationItem
	 * @param startDate start date of vacation
	 * @param endDate end date of vacation
	 * @param comment comment for vacation
	 */
	public VacationItem(LocalDate startDate, LocalDate endDate, String comment) {

		this.startDate = startDate;
		this.endDate = endDate;
		this.comment = comment; 
	}

	/**
	 * @return returns the start date
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate start date of the vacation
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return returns the end date of the vacation
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate end date of the vacation
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return returns the comment of the vacation
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment comment of the vacation
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * converts the VacationItem to a Absence entity
	 * @return returns an Absence entity
	 */
	public Absence toAbsenceEntity(){
		Absence newAbsence = new Absence();
		newAbsence.setComment(this.getComment());
		newAbsence.setStart(Date.valueOf(this.getStartDate()));
		newAbsence.setEnd(Date.valueOf(this.getEndDate()));
		return newAbsence;
	}

}
