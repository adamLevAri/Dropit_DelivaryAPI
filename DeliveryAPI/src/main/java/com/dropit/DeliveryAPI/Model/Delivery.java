package com.dropit.DeliveryAPI.Model;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name="delivery")
public class Delivery {

	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Transient
	private User user;
	
	@Column(name="timeslot_id")
	private long timeslotId;
	
	@Column
	private boolean status;

	public Delivery(User user, long timeslotId) {
		this.user = user;
		this.timeslotId = timeslotId;
		this.status = false;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getTimeslotId() {
		return timeslotId;
	}

	public void setTimeslotId(long timeslotId) {
		this.timeslotId = timeslotId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	
	
}
