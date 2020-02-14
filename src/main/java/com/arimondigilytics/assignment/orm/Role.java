package com.arimondigilytics.assignment.orm;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "ROLE")
public class Role implements Serializable {

	@ManyToMany(mappedBy = "roleSet")
	private Set<User> userSet = new HashSet<>();

	@Id
	@Column(name = "ROLE_ID")
	private String roleId;

	@Column(name = "ROLE_NAME")
	private String roleName;
}
