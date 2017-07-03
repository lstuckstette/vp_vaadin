package com.veraplan.teacherWishlist.PersistenceManagement;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.google.gson.Gson;
import com.vaadin.cdi.UIScoped;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.veraplan.teacherWishlist.DataTransferObject.AbsenceDTO;
import com.veraplan.teacherWishlist.DataTransferObject.PeriodicabsencetimeslotDTO;
import com.veraplan.teacherWishlist.DataTransferObject.TeacherDTO;
import com.veraplan.teacherWishlist.DataTransferObject.TeacherwishlistDTO;
import com.veraplan.teacherWishlist.Entities.Absence;
import com.veraplan.teacherWishlist.Entities.Periodicabsencetimeslot;
import com.veraplan.teacherWishlist.Entities.Teacherwishlist;

/**
 * EvaluationExportManager manages the export of database entries
 * @author Lukas Stuckstette
 */
@UIScoped
public class EvaluationExportManager {

	//EntitiyManager for database operations
	private EntityManager entityManager;

	public EvaluationExportManager() {
		//fetch a EntityManager object
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqlconn");
		entityManager = emf.createEntityManager();
	}

	/**
	 * fetches a list of all 'Teacherwishlist'Entities and return them in a List
	 * @return a list of all 'Teacherwishlist' Entities
	 */
	private List<Teacherwishlist> getTeacherwishlists() {
		TypedQuery<Teacherwishlist> twlQuery = entityManager.createQuery("SELECT twl FROM Teacherwishlist twl",
				Teacherwishlist.class);
		return twlQuery.getResultList();
	}

	/**
	 * generates a JSON-String representing all 'Teacherwishlist' entities 
	 * and embedds it in a StreamResource for ease of downloading it.
	 * @return a StreamResource containing a JSON-String representing all 'Teacherwishlist' entities
	 */
	@SuppressWarnings("serial")
	public StreamResource getEvaluationBatchJSONData() { 

		StreamSource source = new StreamSource() {

			@Override
			public InputStream getStream() {
				Gson gson = new Gson();
				//gather all Entities
				List<Teacherwishlist> twlEList = getTeacherwishlists();
				//transform to DTOs in oder to generate JSON-String
				List<TeacherDTO> tDTOList = getTeacherWishlistDTOCollection(twlEList);
				//generate JSON-String
				String data = gson.toJson(tDTOList);
				//return InputStream containing JSON-String
				return new ByteArrayInputStream(data.getBytes());
			}

		};
		//return a StreamResource that provides a 'evaluation-batch.json' file containing the JSON-String
		StreamResource resource = new StreamResource(source, "evaluation-batch.json");
		return resource;
	}

	/**
	 * converts a List of 'Teacherwishlist' Entites to a List of 'TeacherDTO' elements containing 'TeacherwishlistDTO's
	 * @param entityList List of Teacherwishlist Entities
	 * @return returns a list of 'TeacherDTO' elements containing 'TeacherwishlistDTO's
	 */
	private List<TeacherDTO> getTeacherWishlistDTOCollection(List<Teacherwishlist> entityList) {

		ArrayList<TeacherDTO> teacherDTOList = new ArrayList<>();

		for (Teacherwishlist twlE : entityList) {

			TeacherwishlistDTO twlDTO = new TeacherwishlistDTO();
			ArrayList<AbsenceDTO> tmpAbsenceDtoList = new ArrayList<AbsenceDTO>();
			ArrayList<PeriodicabsencetimeslotDTO> tmpPatsDTOList = new ArrayList<PeriodicabsencetimeslotDTO>();
			// VacationElements
			for (Absence aE : twlE.getAbsences()) {
				AbsenceDTO newAbsence = new AbsenceDTO();
				newAbsence.setStart(aE.getStart());
				newAbsence.setEnd(aE.getEnd());
				newAbsence.setComment(aE.getComment());
				newAbsence.setId(aE.getIdAbsence());
				tmpAbsenceDtoList.add(newAbsence);

			}
			// PeriodicAbsenceElements:
			for (Periodicabsencetimeslot patsE : twlE.getPeriodicabsencetimeslots()) {
				PeriodicabsencetimeslotDTO patsDTO = new PeriodicabsencetimeslotDTO();
				patsDTO.setId(patsE.getIdPeriodicAbsenceTimeslot());
				patsDTO.setTimeslotnumber(patsE.getTimeSlotNumber());
				patsDTO.setWeekday(patsE.getWeekday());
				tmpPatsDTOList.add(patsDTO);
			}
			twlDTO.setAbsenceList(tmpAbsenceDtoList);
			twlDTO.setPeriodicList(tmpPatsDTOList);
			twlDTO.setTimestamp(twlE.getDate());
			twlDTO.setPeriodicabsencecomment(twlE.getPeriodicAbsenceComment());

			// check weather Teacher exists
			boolean teacherFound = false;
			for (TeacherDTO tDTO : teacherDTOList) {
				if (tDTO.getIdTeacher() == twlE.getTeacher().getIdTeacher()) {
					// teacher found! add current TeacherWishlist to existing
					// collection:
					teacherFound = true;
					List<TeacherwishlistDTO> tmpTwlDTOList = tDTO.getTeacherWishlists();
					tmpTwlDTOList.add(twlDTO);
					tDTO.setTeacherWishlists(tmpTwlDTOList);
				}
			}
			if (!teacherFound) {
				//teacher not found, creating a new one and adding current TeacherwishlistDTO
				TeacherDTO tDTO = new TeacherDTO();
				tDTO.setIdTeacher(twlE.getTeacher().getIdTeacher());
				ArrayList<TeacherwishlistDTO> twlDTOtmpList = new ArrayList<>();
				twlDTOtmpList.add(twlDTO);
				tDTO.setTeacherWishlists(twlDTOtmpList);
				teacherDTOList.add(tDTO);
			}
		}

		return teacherDTOList;

	}
}
