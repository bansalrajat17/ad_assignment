package com.arimondigilytics.assignment.orm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "USER")
public class User implements Serializable {

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "EMAIL_ADDRESS")
	private String emailAddress;

	@Column(name = "PASSWORD")
	private String password;

}
