package demo.db.main.persistence.domain;

import java.sql.Timestamp;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TIMESLOT")
public class TimeslotDAO {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer timeslotId;
	
	@Column(name="ROOMID")
	private String roomId;	

    @Column(name = "DAY_TYPE")
    private String dayType;  
    
	@Column(name="START_TIME")
	private LocalTime startTime;
	
	@Column(name="END_TIME")
	private LocalTime endTime;

    @Column(name = "interval_minutes")
    private Integer intervalMinutes;

	public Integer getTimeslotId() {
		return timeslotId;
	}

	public void setTimeslotId(Integer timeslotId) {
		this.timeslotId = timeslotId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getdayType() {
		return dayType;
	}

	public void setDayTime(String dayType) {
		this.dayType = dayType;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public Integer getIntervalMinutes() {
		return intervalMinutes;
	}

	public void setIntervalMinutes(Integer intervalMinutes) {
		this.intervalMinutes = intervalMinutes;
	}


    
    

 
}