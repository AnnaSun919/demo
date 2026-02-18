package demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import demo.db.main.persistence.domain.RoomDAO;
import demo.db.main.persistence.repository.RoomRepository;

public class RoomEventHandler implements RoomService {
	
	@Autowired
	private  RoomRepository roomRepository ;

	@Override
	public String createRoom(String name, String description, String capacity, String groupId) {
		return null;
	}


	@Override
	public List<RoomDAO> getRooms() {
		return roomRepository.findAll();
	}



	

}
