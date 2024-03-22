package com.nt.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "USER_ROLE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class UserRoleInfo implements Serializable {
	@Id
	@SequenceGenerator(name = "GEN1",sequenceName = "USER_ROLE_SEQ", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(generator = "GEN1" , strategy = GenerationType.SEQUENCE)
	@Column(name = "ROLEID")
	private Integer roleId;
	@Column(length = 20,name = "ROLENAME")
	@NonNull
	private String roleName;
	@Column(name = "ROLEDESCRIPTION", length = 40)
	@NonNull
	private String roleDesc;

}
