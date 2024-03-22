

function doValidations(frm){
	//empty form validation error message 
	document.getElementById("fnameErr").innerHTML="";
	document.getElementById("lnameErr").innerHTML="";
	document.getElementById("dobErr").innerHTML="";
	document.getElementById("emailErr").innerHTML="";
	document.getElementById("phoneErr").innerHTML="";
	document.getElementById("amountErr").innerHTML="";
	document.getElementById("pwordErr").innerHTML="";
	document.getElementById("addrsErr").innerHTML="";
	document.getElementById("roleErr").innerHTML="";
	document.getElementById("descErr").innerHTML="";
	
	//read from comp values
	let fname=frm.firstName.value;
	let lname=frm.lastName.value;
	let birthDate=frm.dob.value;
	let mail=frm.emailId.value
	let phone=frm.phno.value;
	let balance=frm.amount.value;
	let pword=frm.pword.value;
	let addr=frm.addrs.value;
	
	let urole=document.getElementById("rname");
	let role=urole.value;
	let rdesc=document.getElementById("rdesc");
	let desc=rdesc.value;
	let isValid=true;
	
	
	
	//write clint side form validation logic
	if(fname==""){
		document.getElementById("fnameErr").innerHTML="User first name must be required";
		isValid=false;
	}else if(fname.length<=2 || fname.length>20){
		document.getElementById("fnameErr").innerHTML="First name must contain >2 chars and <=20 chars ";
		isValid=false;
	}
	
	if(lname==""){
		document.getElementById("lnameErr").innerHTML="User last name must required";
		isValid=false;
	}else if(lname.length<=2 || lname.length>20){
		document.getElementById("lnameErr").innerHTML="Last name must contain >2 chars and <=20 chars ";
		isValid=false;
	}
	
	if(birthDate==""){
		document.getElementById("dobErr").innerHTML="Date of Birth is required";
		isValid=false;
	}
	
	if(mail==""){
		document.getElementById("emailErr").innerHTML="User email is required";
		isValid=false;
	}else if(!(mail.includes("@") && mail.includes("."))){
		document.getElementById("emailErr").innerHTML="Invalid Email format";
		isValid=false;
	}
	
	if(phone==""){
		document.getElementById("phoneErr").innerHTML="Phone no is required";
		isValid=false;
	}else if(phone.length<10 ||phone.length>20){
		document.getElementById("phoneErr").innerHTML="Phone must be >10 and <20 letters";
		isValid=false;
	}
	
	if(balance==""){
		document.getElementById("amountErr").innerHTML="User Amount is required";
		isValid=false;
	}else if(isNan(balance)){
		document.getElementById("amountErr").innerHTML="User amount must be numeric value";
		isValid=false;
	}
	
	if(pword==""){
		document.getElementById("pwordErr").innerHTML="PassWord is required";
		isValid=false;
	}else if(pword.length<6 || pword.length>20){
		document.getElementById("pwordErr").innerHTML="Password must be >5 and <21 chars";
		isValid=false;
	}
	
	if(addr==""){
		document.getElementById("addrsErr").innerHTML="User address is required";
		isValid=false;
	}else if(addr.length>20){
		document.getElementById("addrsErr").innerHTML="Address must be<=20 chars";
		isValid=false;
	}
	
	if(role==""){
		document.getElementById("roleErr").innerHTML="User role is required";
		isValid=false;
	}else if(role.length>20){
		document.getElementById("roleErr").innerHTML="Role must have <20 chars";
		isValid=false;
	}
	
	if(desc==""){
		document.getElementById("descErr").innerHTML="Description is required";
		isValid=false;
	}else if(desc.length>40){
		document.getElementById("descErr").innerHTML="Description must have <40  chars";
		isValid=false;
	}
	alert("clint side form validation is done");
	//pasing signal to server side form validation
	frm.vFlag.value="yes";
	
	return isValid;
	
}