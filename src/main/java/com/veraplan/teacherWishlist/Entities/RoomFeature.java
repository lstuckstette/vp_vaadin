package com.veraplan.teacherWishlist.Entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the room_feature database table.
 * 
 */
@Entity
@Table(name="room_feature")
@NamedQuery(name="RoomFeature.findAll", query="SELECT r FROM RoomFeature r")
public class RoomFeature implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idRoom_Feature;

	private String feature;

	//bi-directional many-to-one association to Room
	@ManyToOne
	private Room room;

	public RoomFeature() {
	}

	public int getIdRoom_Feature() {
		return this.idRoom_Feature;
	}

	public void setIdRoom_Feature(int idRoom_Feature) {
		this.idRoom_Feature = idRoom_Feature;
	}

	public String getFeature() {
		return this.feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public Room getRoom() {
		return this.room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

}