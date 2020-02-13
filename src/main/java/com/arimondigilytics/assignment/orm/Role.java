package com.arimondigilytics.assignment.orm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ROLE")
public class Role implements Serializable {
		@Column(name = "ROLE_ID")
		private String roleId;

		@Column(name = "ROLE_NAME")
		private String roleName;
	}
}
