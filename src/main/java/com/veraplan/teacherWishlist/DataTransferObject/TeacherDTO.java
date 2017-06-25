package com.veraplan.teacherWishlist.DataTransferObject;

import java.io.Serializable;
import java.util.List;

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
