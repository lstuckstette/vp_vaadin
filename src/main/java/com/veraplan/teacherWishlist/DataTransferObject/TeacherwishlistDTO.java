package com.veraplan.teacherWishlist.DataTransferObject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TeacherwishlistDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3107302896573983233L;

	private int id;
	private Date timestamp;
	private String periodicabsencecomment;
	private List<AbsenceDTO> absenceList;
	private List<PeriodicabsencetimeslotDTO> periodicList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getPeriodicabsencecomment() {
		return periodicabsencecomment;
	}

	public void setPeriodicabsencecomment(String periodicabsencecomment) {
		this.periodicabsencecomment = periodicabsencecomment;
	}

	public List<AbsenceDTO> getAbsenceList() {
		return absenceList;
	}

	public void setAbsenceList(List<AbsenceDTO> absenceList) {
		this.absenceList = absenceList;
	}

	public List<PeriodicabsencetimeslotDTO> getPeriodicList() {
		return periodicList;
	}

	public void setPeriodicList(List<PeriodicabsencetimeslotDTO> periodicList) {
		this.periodicList = periodicList;
	}

}
