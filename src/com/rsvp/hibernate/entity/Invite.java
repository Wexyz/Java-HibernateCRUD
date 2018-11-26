package com.rsvp.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//import org.apache.commons.lang3.RandomStringUtils;

@Entity
@Table(name="Invite")
public class Invite {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "invite_id")
	private int invite_id;
	
	@Column(name = "invite_code", unique=true)
	private String invite_code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name="status")
	private int status;

	public String getInvite_code() {
		return invite_code;
	}

	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return invite_id;
	}

	public void setId(int id) {
		this.invite_id = id;
	}

	public Invite(){

	}

	//CONSTRUCTOR
	public Invite(String name) {
		super();
		//this.invite_code = RandomStringUtils.randomAlphanumeric(7);
		this.name = name;
		this.status = 0;
	}

	@Override
	public String toString() {
		return "Invite [invite_code=" + invite_code + ", name=" + name + ", status=" + status + "]";
	}
	
	
}
