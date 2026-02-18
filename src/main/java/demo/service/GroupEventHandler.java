package demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import demo.db.main.persistence.domain.GroupDAO;
import demo.db.main.persistence.repository.GroupRepository;

public class GroupEventHandler implements GroupService {
	
	@Autowired
	private  GroupRepository groupRepository ;

	@Override
	public String createGroup(String name, String description) {
		return null;
	}

	@Override
	public List<GroupDAO> getAllGroups() {
		return groupRepository.findAllGroups();
	}


}
