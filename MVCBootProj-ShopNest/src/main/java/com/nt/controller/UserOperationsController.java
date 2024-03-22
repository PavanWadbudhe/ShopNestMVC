package com.nt.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nt.entity.ProductInfo;
import com.nt.entity.UserInfo;
import com.nt.entity.UserPurchaseInfo;
import com.nt.entity.UserRoleInfo;
import com.nt.service.IUserManagementService;
import com.nt.validator.ProductValidator;
import com.nt.validator.UserValidator;

import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/user")
public class UserOperationsController {
	@Autowired
	private IUserManagementService userService;
	@Autowired
	private UserValidator userValidator;
	@Autowired
	private ProductValidator prodValidator;
	
	
	@GetMapping("/register")
	public String showRegisterUserPage(@ModelAttribute("user") UserInfo uinfo, @ModelAttribute("role") UserRoleInfo roleInfo ) {
		//return  LVN
		return "register_user";
	}
	
	@PostMapping("/register")
	@Transactional
	public String registerUser( @ModelAttribute("user") UserInfo uinfo,			                                         
			                                          RedirectAttributes attrs, 
			                                          BindingResult errors) {
		//enable server side form validation only when client side form validation not done
		if(uinfo.getVFlag().equalsIgnoreCase("no")) {
			//use validator
			if(userValidator.supports(UserInfo.class)) {
				userValidator.validate(uinfo, errors);
				if(errors.hasErrors()) {
					return "register_user";
				}					
			}
		}
		
		//user Service
		String msg=userService.userRegistration(uinfo);
		//add result to redirect scope as flash attribute
		attrs.addFlashAttribute("result", msg);
		//return LVN
		return "redirect:register";
	}
	
	@GetMapping("/login")
	public String showLoginUserPage() {
		//return LVN
		return "login_user";
	}
	
	@PostMapping("/loginuser")
	public String loginUser(RedirectAttributes attrs,Map<String, Object> map, @ModelAttribute("user") UserInfo uinfo) {
		//use Service
		String msg=userService.userLogin(uinfo);
		//set result to shared memory as model attribute
		attrs.addFlashAttribute("result", msg);
		//return LVN
		if(msg.equalsIgnoreCase("seller")) 
			return "redirect:displayseller";
		else if(msg.equalsIgnoreCase("buyer")) 
			return "redirect:displaybuyer";
		else
		    return "redirect:login";
	}
	
	@GetMapping("/add")
	public String showAddProductPage(@ModelAttribute("prod") ProductInfo product) {
		//return LVN
		return "add_product";
	}
	
	@PostMapping("/add")
	@Transactional
	public String addProduct(//@RequestParam("prodName") String prodName,
			                                        //@RequestParam("desc") String desc,
			                                        @RequestParam("img") MultipartFile image,
			                                       // @RequestParam("sellPrice") Double sellPrice,
			                                        //@RequestParam("costPrice") Double costPrice,
			                                        //@RequestParam("stockUnit") Integer stockUnit,
			                                      //  @RequestParam("date") LocalDate date, 	
			                                        @ModelAttribute("prod") ProductInfo product,
			                                        BindingResult errors ,
			                                        RedirectAttributes attrs ) {
		
		System.out.println("=====================1");
		
		/*
		//use Validator
		if(prodValidator.supports(ProductInfo.class)) {
			prodValidator.validate(product, errors);
			if(errors.hasErrors())
				return "add_product";
		}
		*/
	      System.out.println(product.toString());
		
		System.out.println("=====================2");		
	
			if(! image.isEmpty()) {
				try {
					String uploadDir="D://images_dir/";
					
					if(! Files.exists(Paths.get(uploadDir))) {
						Files.createDirectories(Paths.get(uploadDir));
					}
					String fileName=image.getOriginalFilename();
					Path filePath=Paths.get(uploadDir + fileName);
					Files.write(filePath, image.getBytes());
				
				
					product.setImg(filePath.toString());
					
					System.out.println(product.toString());
				
					System.out.println("=====================3");
		
					//use Service
						String msg=userService.addProduct(product);
						//add result to Redirect Attribute as Flash attribute having redirect scope
						attrs.addFlashAttribute("resultMsg", msg);
				    	//return LVN
					return"redirect:displayseller";
				}catch(Exception e) {
					String msg="Problem while uploading the image ! " +e.getMessage();
					attrs.addFlashAttribute("resultMsg", msg);
					return "redirect:add";
				}
			}else {
				String msg="Image not selected !";
				attrs.addFlashAttribute("resultMsg", msg);
				return "redirect:add";
			}
	}
	
	@GetMapping("/displayseller")
	public String displayAllProductsToSeller(Map<String, Object> map) {
		//use service
		List<ProductInfo> list=userService.fetchAllProducts();
		//store data in shared memory as req scope (BindingAwareModelMap) as shared memory
		map.put("prodList", list);
		return "display_seller_product";
	}
	
	@GetMapping("/displaybuyer")
	public String displayAllProductsToBuyer(Map<String, Object> map) {
		//use userService
		List<ProductInfo> list=userService.fetchAllProducts();
		//keep the result to shared memory i.e.BindingAwareModelMap having req scope
		map.put("prodList", list);
		//return LVN
		return "display_buyer_product";
	}
	
	@GetMapping("/edit")
	public String showEditProductFormPage(@RequestParam("no") int id, @ModelAttribute("prod") ProductInfo prod) {
		//use service
		ProductInfo prodInfo=userService.getProductById(id);
		//copy product data to to model class obj using predefined copy method
		BeanUtils.copyProperties(prodInfo, prod);
		//return LVN
		return "update_product";
	}
	
	@PostMapping("/edit")
	@Transactional
	public String updateProduct(@ModelAttribute("prod") ProductInfo prod, RedirectAttributes attrs, BindingResult errors) {
		//use Validator
		if(prodValidator.supports(ProductInfo.class)) {
			prodValidator.validate(prod, errors);
			if(errors.hasErrors())
				return "update_product";
		}
		
		//use Service
		String result=userService.updateProduct(prod);
		//keep the result in redirect attrs as flash attrs having redirect scope
		attrs.addFlashAttribute("resultMsg", result);
		//return LVN
		return "redirect:displayseller";
	}
	
	@GetMapping("/delete")
	@Transactional
	public String deleteProduct(@RequestParam("no") int id, RedirectAttributes attrs) {
		//use Service
		String result=userService.deleteProduct(id);
		//keep the result in redirect attrs as flash attrs having redirect scope
		attrs.addFlashAttribute("resultMsg", result);
		//return LVN
		return "redirect:displayseller";
	}
	
	@GetMapping("/addtocart")
	public String addToCart(@RequestParam("no") int id, Map<String, Object> map) {
		//use userService
		ProductInfo prod=userService.getProductById(id);
		//keep data into shared memory i.e.BindingAwareModelMap having req scope
		map.put("product", prod);
		//return LVN
		return "display_cart";
	}
	
	@GetMapping("/buy")
	@Transactional
	public String buyProduct(@RequestParam("quantity") int quantity, RedirectAttributes attrs) {
		   //use userService
		String msg=userService.purchaseProduct(quantity);
		//keep the result in redirect attrs as flash attrs having redirect scope
		attrs.addFlashAttribute("resultMsg", msg);
		//return LVN
		return "redirect:displaybuyer";
	}
	
	@GetMapping("/sellerinfo")
	public String displaySellerInfo(Map<String, Object> map) {
		//use service
		List<UserInfo> list=userService.sellerInfo();
		//keep result in the shared memory
		map.put("sellerList", list);
		//return LVN
		return "seller_info";
	}
	
	@GetMapping("/buyerinfo")
	public String displayBuyerInfo(Map<String, Object> map) {
		//user service
		List<UserInfo> buyerList=userService.buyerInfo();
		List<UserPurchaseInfo> purchaseList=userService.purchaseInfo();
		//keep result in the shared memory
		map.put("buyerList", buyerList);
		map.put("purchaseList", purchaseList);
		//return LVN
		return "buyer_info";
	}

}
