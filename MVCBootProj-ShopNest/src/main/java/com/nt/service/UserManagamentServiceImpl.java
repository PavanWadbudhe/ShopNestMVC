package com.nt.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.entity.ProductInfo;
import com.nt.entity.UserInfo;
import com.nt.entity.UserPurchaseInfo;
import com.nt.entity.UserRoleInfo;
import com.nt.repository.IProductInfoRepository;
import com.nt.repository.IUserInfoRepository;
import com.nt.repository.IUserPurchseInfoRepository;
import com.nt.repository.IUserRoleInfoRepository;

@Service("userService")
public class UserManagamentServiceImpl implements IUserManagementService {
	@Autowired
	IUserInfoRepository userInfoRepo;
	@Autowired
	IUserRoleInfoRepository userRoleInfoRepo;
	@Autowired
	IProductInfoRepository prodInfoRepo;
	@Autowired
	IUserPurchseInfoRepository purchaseInfoRepo;
	
	private UserInfo userInfo=null;
	private ProductInfo product=null;
	
	@Override
	public String userRegistration(UserInfo uinfo) {
		Optional<UserInfo> opt=userInfoRepo.findByEmailId(uinfo.getEmailId());
		if(opt.isEmpty()) {
			//uinfo.setUserRole(roleInfo);
			//System.out.println(roleInfo.toString()+"============");
			System.out.println(uinfo.toString()+"==============");
			System.out.println(uinfo.getUserRole().toString()+"==============");
			int uid=userInfoRepo.save(uinfo).getUserId();
			return "User is register with id value ::"+uid;
		}
		else
			return uinfo.getEmailId()+" User already registered with emailId ";
	}

	@Override
	public String userLogin(UserInfo uinfo) {
		Optional<UserInfo> opt=userInfoRepo.findByEmailId(uinfo.getEmailId());
		if(opt.isPresent()) {
			 userInfo=opt.get();
			if(userInfo.getPword().equalsIgnoreCase(uinfo.getPword())) {
				UserRoleInfo roleInfo=userInfo.getUserRole();
				String roleName=roleInfo.getRoleName();
				return roleName;
			}else 
				return "Invalid password !";
		}else
		    return "Invalid emailId !";
	}

	@Override
	public String addProduct(ProductInfo pinfo) {
		pinfo.setCreatedByUser(userInfo);

		return "Product are added with product id :: "+prodInfoRepo.save(pinfo).getProductId();
	}

	@Override
	public List<ProductInfo> fetchAllProducts() {
		//use repo
		List<ProductInfo> prodList=prodInfoRepo.findAll();
		//sort the product base on id
		prodList.sort((prod1,prod2)-> prod1.getProductId()-prod2.getProductId());
		return prodList;
	}

	@Override
	public ProductInfo getProductById(int id) {
		//use productInfoRepo
		product= prodInfoRepo.findById(id).orElseThrow(()-> new IllegalArgumentException());
		return product;
	}

	@Override
	public String updateProduct(ProductInfo prod) {
		//user productInfoRepo
		Optional<ProductInfo> opt=prodInfoRepo.findById(prod.getProductId());
		if(opt.isPresent()) {
			prod.setCreatedByUser(userInfo);
			int prodId=prodInfoRepo.save(prod).getProductId();
			return prodId+" Product is updated...";
		}
		 return prod.getProductId()+" Product not found...";
	}

	@Override
	public String deleteProduct(int id) {
		Optional<ProductInfo> opt=prodInfoRepo.findById(id);
		if(opt.isPresent()) {
			prodInfoRepo.deleteById(id);
			return id+" Product is deleted...";
		}
		return id+" product not found...";
	}

	@Override
	public String purchaseProduct(int quantity) {
		//total product amount
		double totalPrice=product.getSellPrice() * quantity;
		//get the product item stock
		int itemStock=product.getStockUnit();
		//get the buyer amount
		double buyerAmount=userInfo.getAmount();
		//get the seller amount
		double sellerAmount=product.getCreatedByUser().getAmount();
		
		//get the user who created product (seller)
		UserInfo user=product.getCreatedByUser();
		
		if(itemStock >= quantity) {
			if(buyerAmount >= totalPrice) {
				//update the product stock
				product.setStockUnit(itemStock - quantity);
				
				prodInfoRepo.save(product);
				//update the buyer amount
				userInfo.setAmount(buyerAmount - totalPrice);
				userInfoRepo.save(userInfo);
				//update the seller amount
				user.setAmount(sellerAmount + totalPrice);
				userInfoRepo.save(user);
				
				//add the product purchase info
				UserPurchaseInfo userPurchase=new UserPurchaseInfo();
				userPurchase.setTotalUnit(quantity);
				userPurchase.setDate(LocalDate.now());
				userPurchase.setPurchaseByUser(userInfo);
				userPurchase.setPurchaseFromUser(user);
				userPurchase.setProductId(product);
				
				purchaseInfoRepo.save(userPurchase);
				
				return product.getProductId()+" product buyed successfully with amount ::"+totalPrice;
			}else
				return "Insufficient Amount !";
		}else
			return "Quantity should be less than Stock Unit";
	}

	@Override
	public List<UserInfo> sellerInfo() {
		//repo
		List<UserInfo> userList=userInfoRepo.findAll();
		//filter the user base on role name using stream
		List<UserInfo> list=userList.stream().filter((user)-> user.getUserRole().getRoleName().equalsIgnoreCase("seller")).collect(Collectors.toList());
		//sort the user base on userId
		list.sort((user1,user2)-> user1.getUserId()-user2.getUserId());
		//return user list
		return list;
	}

	@Override
	public List<UserInfo> buyerInfo() {
		//repo
		List<UserInfo> userList=userInfoRepo.findAll();
		//filter the user base on role name using stream
		List<UserInfo> list=userList.stream().filter((user)-> user.getUserRole().getRoleName().equalsIgnoreCase("buyer")).collect(Collectors.toList());
		//sort the user base on userId
		list.sort((user1,user2)-> user1.getUserId()-user2.getUserId());
		//return user list
		return list;
	}

	@Override
	public List<UserPurchaseInfo> purchaseInfo() {
		//repo
		List<UserPurchaseInfo> pruchaseList=purchaseInfoRepo.findAll();
		//sort the buyerinfo base on purchaseId
		pruchaseList.sort((buyer1,buyer2)-> buyer1.getPurchaseId()-buyer2.getPurchaseId());
		//return user list
		return pruchaseList;
	}
	
}
