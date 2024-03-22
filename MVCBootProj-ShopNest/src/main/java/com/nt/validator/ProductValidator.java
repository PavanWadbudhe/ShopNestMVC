package com.nt.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.nt.entity.ProductInfo;

@Component
public class ProductValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(ProductInfo.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//type casting with Model class
		ProductInfo pinfo=(ProductInfo) target;
		
		System.out.println(pinfo.toString());
		
		//Form validation logic and place the error in error obj
		if(pinfo.getProdName()==null || pinfo.getProdName().equalsIgnoreCase("") || pinfo.getProdName().length()==0)
			errors.rejectValue("prodName", "product.name.required");
		else if(pinfo.getProdName().length() <= 2 || pinfo.getProdName().length() > 20)
			errors.rejectValue("prodName", "product.name.length");
		
		if(pinfo.getDesc()==null || pinfo.getDesc().equalsIgnoreCase("") || pinfo.getDesc().length()==0)
			errors.rejectValue("desc", "product.desc.required");
		else if(pinfo.getDesc().length() <= 2 || pinfo.getDesc().length() > 40)
			errors.rejectValue("desc", "product.desc.length");
		
		if(pinfo.getImg()==null || pinfo.getImg().isEmpty())
			errors.rejectValue("img", "product.image.required");
		 
		if(pinfo.getSellPrice()==null || pinfo.getSellPrice().equals(""))
			errors.rejectValue("sellPrice", "product.sell.required");
		
		if(pinfo.getCostPrice()==null || pinfo.getCostPrice().equals(""))
			errors.rejectValue("costPrice","product.cost.required");
		
		if(pinfo.getStockUnit()==null || pinfo.getStockUnit().equals(""))
			errors.rejectValue("stockUnit", "product.stock.required");
		
		if(pinfo.getDate()==null || pinfo.getDate().equals(""))
			errors.rejectValue("date", "product.date.required");
	}

}
