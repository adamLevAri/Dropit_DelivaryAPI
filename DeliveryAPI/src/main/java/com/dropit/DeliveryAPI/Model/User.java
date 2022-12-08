package com.dropit.DeliveryAPI.Model;

import java.util.List;

import javax.persistence.*;

import com.sun.istack.NotNull;

import lombok.Data;

@Entity
@Table(name="user")

public class User {
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String name;
	
	@Column(name="last_name")
	private String lastName;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	
	
}
