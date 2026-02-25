package demo.db.main.persistence.domain;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TIMESLOT")
public class TimeslotDAO {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer timeslotId;

	@Column(name = "ROOM_ID")
	private String roomId;

	@Column(name = "DAY_TYPE")
	private String dayType;

	@JsonFormat(pattern = "HH:mm")
	@Column(name = "START_TIME")
	private LocalTime startTime;

	@JsonFormat(pattern = "HH:mm")
	@Column(name = "END_TIME")
	private LocalTime endTime;

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

	public String getDayType() {
		return dayType;
	}

	public void setDayType(String dayType) {
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

}