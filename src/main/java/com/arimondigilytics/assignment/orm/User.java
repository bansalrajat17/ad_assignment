package com.arimondigilytics.assignment.orm;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "USER")
public class User implements Serializable {

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "ROLE_ID") })
	private Set<Role> roleSet = new HashSet<>();

	@Id
	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "EMAIL_ADDRESS")
	private String emailAddress;
	
	@Column(name = "NAME")
	private String name;

	@Column(name = "PASSWORD")
	private String password;

}
