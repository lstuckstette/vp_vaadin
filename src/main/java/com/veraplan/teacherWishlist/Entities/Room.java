package com.veraplan.teacherWishlist.Entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the room database table.
 * 
 */
@Entity
@NamedQuery(name="Room.findAll", query="SELECT r FROM Room r")
public class Room implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idRoom;

	private String abbreviation;

	private String name;

	//bi-directional many-to-one association to Lesson
	@OneToMany(mappedBy="room")
	private List<Lesson> lessons;

	//bi-directional many-to-one association to RoomFeature
	@OneToMany(mappedBy="room")
	private List<RoomFeature> roomFeatures;

	public Room() {
	}

	public int getIdRoom() {
		return this.idRoom;
	}

	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}

	public String getAbbreviation() {
		return this.abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Lesson> getLessons() {
		return this.lessons;
	}

	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}

	public Lesson addLesson(Lesson lesson) {
		getLessons().add(lesson);
		lesson.setRoom(this);

		return lesson;
	}

	public Lesson removeLesson(Lesson lesson) {
		getLessons().remove(lesson);
		lesson.setRoom(null);

		return lesson;
	}

	public List<RoomFeature> getRoomFeatures() {
		return this.roomFeatures;
	}

	public void setRoomFeatures(List<RoomFeature> roomFeatures) {
		this.roomFeatures = roomFeatures;
	}

	public RoomFeature addRoomFeature(RoomFeature roomFeature) {
		getRoomFeatures().add(roomFeature);
		roomFeature.setRoom(this);

		return roomFeature;
	}

	public RoomFeature removeRoomFeature(RoomFeature roomFeature) {
		getRoomFeatures().remove(roomFeature);
		roomFeature.setRoom(null);

		return roomFeature;
	}

}