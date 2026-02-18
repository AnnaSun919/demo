package demo.service;
import java.util.List;

import demo.db.main.persistence.domain.GroupDAO;

public interface GroupService {

	public String createGroup(String name, String description);	
	
	public List<GroupDAO> getAllGroups();

}
