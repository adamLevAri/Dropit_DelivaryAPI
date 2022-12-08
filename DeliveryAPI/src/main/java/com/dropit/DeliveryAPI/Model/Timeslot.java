package com.dropit.DeliveryAPI.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;

@Entity
@Table(name="timeslot")

public class Timeslot {
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="start_time")
	private Date startTime;
	
	@Column(name="end_time")
	private Date endTime;
	
	@Transient
	List<String> supportedAddresses;
	
	@Column
	private int capacity;
	
	public Timeslot(Date date) {
		this.supportedAddresses = new ArrayList<>();
		this.capacity = 2;
		this.startTime = date;
		this.endTime = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<String> getSupportedAddresses() {
		return supportedAddresses;
	}

	public void setSupportedAddresses(List<String> supportedAddresses) {
		this.supportedAddresses = supportedAddresses;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	
	
}
