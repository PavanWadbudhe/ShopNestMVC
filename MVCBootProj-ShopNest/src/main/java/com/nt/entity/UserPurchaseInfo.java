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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USER_PURCHASE")
@Setter
@Getter
@NoArgsConstructor
public class UserPurchaseInfo implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)   //automatic generated id value
	@Column(name="PURCHASEID")
	private Integer purchaseId;
	@Column(name="PURCHASEDATE")
	private LocalDate date;
	@Column(name="TOTALUNIT")
	private Integer totalUnit;
	
	//OneToOne association mapping with child classes
	@OneToOne( targetEntity = ProductInfo.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTID", referencedColumnName = "productId")
	private ProductInfo productId;
	
	@OneToOne(targetEntity = UserInfo.class,  fetch = FetchType.LAZY)
	@JoinColumn(name = "PURCHASEBYUSER", referencedColumnName = "userId")
	private UserInfo purchaseByUser;
	
	@OneToOne(targetEntity = UserInfo.class,   fetch = FetchType.LAZY)
	@JoinColumn(name = "PURCHASEFROMUSER", referencedColumnName = "userId")
	private UserInfo purchaseFromUser;

	//toString()
	@Override
	public String toString() {
		return "UserPurchaseInfo [purchaseId=" + purchaseId + ", date=" + date + ", totalUnit=" + totalUnit + "]";
	}

}
