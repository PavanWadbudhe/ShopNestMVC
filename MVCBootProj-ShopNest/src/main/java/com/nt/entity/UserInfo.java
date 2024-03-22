package com.nt.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USER_TABLE")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class UserInfo implements Serializable {
	@Id
	@SequenceGenerator(name = "SEQ1", sequenceName = "USER_SEQ", initialValue = 1000, allocationSize = 5)
	@GeneratedValue(generator = "SEQ1", strategy = GenerationType.SEQUENCE)
	@Column(name="USERID")
	private Integer userId;
	@Column(length = 20,name = "FIRSTNAME")
	@NonNull
	private String firstName;
	@Column(length = 20, name = "LASTNAME")
	@NonNull
	private String lastName;
	@Column(name = "DATEOFBIRTH")
	//@NonNull
	private LocalDate dob=null;
	@Column(length = 50, name="EMAILID")
	@NonNull
	private String emailId;
	@Column(name = "MOBILENO", length = 20)
	@NonNull
	private String phno;
	@Column(name="BALANCE")
	//@NonNull
	private Double amount;
	@Column(name = "PASSWORD", length = 20)
	@NonNull
	private String pword;
	@Column(name = "ADDRESS", length = 20)
	@NonNull
	private String addrs;
	private String vFlag="no";
	
	//unidirectional association mapping
	@OneToOne(targetEntity = UserRoleInfo.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JoinColumn(name = "USERROLE", referencedColumnName = "roleId")  //FK column
	private UserRoleInfo userRole;

	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob
				+ ", emailId=" + emailId + ", phno=" + phno + ", amount=" + amount + ", pword=" + pword + ", addrs="
				+ addrs + "]";
	}

}
