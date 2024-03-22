package com.nt.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
@Table(name="PRODUCT_TABLE")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE PRODUCT_TABLE SET ISDELETED='1' WHERE PRODUCTID=?")
@SQLRestriction("ISDELETED  <>  '1'  ")
public class ProductInfo implements Serializable {
	@Id
	@Column(name="PRODUCTID")
	@SequenceGenerator(name = "SEQ1", sequenceName = "PROD_SEQ2", initialValue = 500, allocationSize = 10)
	@GeneratedValue(generator = "SEQ1", strategy = GenerationType.SEQUENCE)
	private Integer productId;
	@Column(name="PRODUCTNAME", length = 20)
	@NonNull
	private String prodName;
	@Column(name="PRODUCTDESCRIPTION", length = 40)
	@NonNull
	private String desc;
	@Column(name="PRODUCTIMG")
	@NonNull
	private String img;
	@Column(name="PRODUCTSELLPRICE")
	//@NonNull
	private Double sellPrice;
	@Column(name="PRODUCTCOSTPRICE")
	//@NonNull
	private Double costPrice;
	@Column(name="STOCKUNIT")
	//@NonNull
	private Integer stockUnit;
	@Column(name="CREATEDATE")
	//@NonNull
	private LocalDate date;
	@Column(name="ISDELETED")
	private boolean isDelated=false ;
	
	//association mapping 
	@OneToOne(targetEntity = UserInfo.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATEDBYUSER", referencedColumnName ="userId" )  //FK mapping
	private UserInfo createdByUser;

	//toString() method
	@Override
	public String toString() {
		return "ProductInfo [productId=" + productId + ", prodName=" + prodName + ", desc=" + desc + ", img=" + img
				+ ", sellPrice=" + sellPrice + ", costPrice=" + costPrice + ", stockUnit=" + stockUnit + ", date="
				+ date + ", isDelated=" + isDelated + "]";
	}

}
