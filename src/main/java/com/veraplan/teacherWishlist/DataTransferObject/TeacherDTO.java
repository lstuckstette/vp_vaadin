package com.veraplan.teacherWishlist.DataTransferObject;

import java.io.Serializable;
import java.util.List;

/**
 * The data transfer class for the Teacher entity.
 * 
 * @author Lukas Stuckstette 
 */
public class TeacherDTO implements Serializable {

	private static final long serialVersionUID = -1395448203478664316L;
	private int idTeacher;
	private List<TeacherwishlistDTO> teacherWishlists;

	public int getIdTeacher() {
		return idTeacher;
	}

	public void setIdTeacher(int idTeacher) {
		this.idTeacher = idTeacher;
	}

	public List<TeacherwishlistDTO> getTeacherWishlists() {
		return teacherWishlists;
	}

	public void setTeacherWishlists(List<TeacherwishlistDTO> teacherWishlists) {
		this.teacherWishlists = teacherWishlists;
	}

}
