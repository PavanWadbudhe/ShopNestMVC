package com.nt.service;

import java.util.List;

import com.nt.entity.ProductInfo;
import com.nt.entity.UserInfo;
import com.nt.entity.UserPurchaseInfo;
import com.nt.entity.UserRoleInfo;

public interface IUserManagementService {
	public String userRegistration(UserInfo uinfo);
	public String userLogin(UserInfo uinfo);
	public String addProduct(ProductInfo pinfo);
	public List<ProductInfo> fetchAllProducts();
	public ProductInfo getProductById(int id);
	public String updateProduct(ProductInfo prod);
	public String deleteProduct(int id);
	public String purchaseProduct(int quantity);
	public List<UserInfo> sellerInfo();
	public List<UserInfo> buyerInfo();
	public List<UserPurchaseInfo> purchaseInfo();

}
