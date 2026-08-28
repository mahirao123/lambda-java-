package com.springboot.scm.employeeController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.scm.employeeEntities.EmployeeDetails;
import com.springboot.scm.employeeServices.EmployeeService;
import com.springboot.scm.entities.SocialMediaUrls;
import com.springboot.scm.forms.EmployeeForm;
import com.springboot.scm.forms.EmployeeSearchForm;
import com.springboot.scm.forms.PasswordResetForm;
import com.springboot.scm.helpers.AppConstants;
import com.springboot.scm.helpers.EmployeeHelper;
import com.springboot.scm.helpers.Message;
import com.springboot.scm.helpers.MessageType;
import com.springboot.scm.helpers.UserAlreadyExistsException;
import com.springboot.scm.services.ImageService;
import com.springboot.scm.services.SocialMediaUrlsService;
import com.springboot.scm.validator.CreateGroup;
import com.springboot.scm.validator.UpdateGroup;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
    @Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ImageService imageService;

    @Autowired
    private EmployeeService employeeService;
    
    
    // Open register page
    @RequestMapping("/signup")
    public String employeesignup(Model model) {
        EmployeeForm employeeForm = new EmployeeForm();

        model.addAttribute("employee", employeeForm);


        return "employeeRegister";
    }

    //login employee
    @RequestMapping("/login")
    public String employeeLogin(Model model) {
    	
        EmployeeForm employeeForm = new EmployeeForm();

        model.addAttribute("employee", employeeForm);
    	
    	return "employeeFile/login";
    }
    
 
    
    @RequestMapping("/customerCare/dashboard")
    public String customerCareDashboaed() {
    	
    	return "customer_care/dashboard";
    }
    
    @RequestMapping("/manager/dashboard")
    public String managerDashboaed() {
    	
    	return "manager/dashboard";
    }
    
    @RequestMapping("/management/dashboard")
    public String managementDashboaed() {
    	
    	return "management/dashboard";
    }
    
//    @RequestMapping("/developer/dashboard")
//    public String developerDashboaed() {
//    	
//    	return "developer_department/dashboard";
//    }
    
    @RequestMapping("/sales/dashboard")
    public String salesDashboaed() {
    	
    	return "sales_department/dashboard";
    }
    
    @RequestMapping("/finance/dashboard")
    public String financeDashboaed() {
    	
    	return "finance/dashboard";
    }
    
    @RequestMapping("/productionTeam/dashboard")
    public String supportDashboaed() {
    	
    	return "productionTeam/dashboard";
    }
    

    
    @RequestMapping("/socialMeadia/dashboard")
    public String socialMediaDashboaed() {
    	
    	return "social_media/dashboard";
    }
    
    // Register employee
    @RequestMapping(
            value = "/do-employeeRegister",
            method = RequestMethod.POST
    )
    public String processEmployeeRegister(
    		@Validated(CreateGroup.class)
    		@ModelAttribute("employee") EmployeeForm employeeForm,
            BindingResult bindingResult,
            HttpSession session,
            Model model
    ) {

        // Validation
        if (bindingResult.hasErrors()) {
			session.setAttribute("message",Message.builder()
					.content("Please correct the following errors")
					.type(MessageType.red)
					.build());

            return "employeeRegister";
        }

        EmployeeDetails employeeDetails = new EmployeeDetails();

        String filename = UUID.randomUUID().toString();

        // Upload files safely
        String profileUrl = null;
        String panUrl = null;
        String accountUrl = null;
        String adharUrl = null;
        String degreeUrl = null;
        String experienceLetterUrl = null;

        // Profile Picture
        if (employeeForm.getProfilePic() != null
                && !employeeForm.getProfilePic().isEmpty()) {

            profileUrl = imageService.uploadFile(
                    employeeForm.getProfilePic(),
                    "profile_" + filename
            );
            employeeDetails.setProCloudinaryId("profile_" + filename);
            
        }

        // PAN
        if (employeeForm.getPanPic() != null
                && !employeeForm.getPanPic().isEmpty()) {

            panUrl = imageService.uploadFile(
                    employeeForm.getPanPic(),
                    "pan_" + filename
            );
            
            employeeDetails.setPanCloudinaryId("pan_" + filename);
        }

        // Bank Account Pic
        if (employeeForm.getBankAccountPic() != null
                && !employeeForm.getBankAccountPic().isEmpty()) {

            accountUrl = imageService.uploadFile(
                    employeeForm.getBankAccountPic(),
                    "bank_" + filename
            );
            employeeDetails.setBanCloudinaryId("bank_" + filename);
        }

        // Aadhaar PDF
        if (employeeForm.getAdharPdf() != null
                && !employeeForm.getAdharPdf().isEmpty()) {

            adharUrl = imageService.uploadFile(
                    employeeForm.getAdharPdf(),
                    "adhar_" + filename
            );
            employeeDetails.setAdhCloudinaryId("adhar_" + filename);
        }

        // Degree PDF
        if (employeeForm.getDegreePdf() != null
                && !employeeForm.getDegreePdf().isEmpty()) {

            degreeUrl = imageService.uploadFile(
                    employeeForm.getDegreePdf(),
                    "degree_" + filename
            );
            
            employeeDetails.setDegCloudinaryId("degree_" + filename);
        }

        // Experience Letter (Optional)
        if (employeeForm.getExperienceLetterPdf() != null
                && !employeeForm.getExperienceLetterPdf().isEmpty()) {

            experienceLetterUrl = imageService.uploadFile(
                    employeeForm.getExperienceLetterPdf(),
                    "experience_" + filename
            );
            employeeDetails.setExpCloudinaryId("experience_" + filename);
            employeeDetails.setExperienceLetterPdf(experienceLetterUrl);
        }

        // Set basic details
        employeeDetails.setName(employeeForm.getName());
        employeeDetails.setEmail(employeeForm.getEmail());
        employeeDetails.setPassword(employeeForm.getPassword());
        employeeDetails.setPhoneNumber(employeeForm.getPhoneNumber());
        employeeDetails.setJoiningDate(employeeForm.getJoiningDate());
        employeeDetails.setRole(employeeForm.getRole());

        // Set uploaded URLs
        employeeDetails.setProfilePic(profileUrl);
        employeeDetails.setBankAccountPic(accountUrl);
        employeeDetails.setPanPic(panUrl);
        employeeDetails.setDegreePdf(degreeUrl);
        employeeDetails.setAdharPdf(adharUrl);
        

        try {

            EmployeeDetails savedEmployee =
                    employeeService.saveEmployeeDetails(employeeDetails);

            System.out.println(savedEmployee);

            Message message = Message.builder()
                    .content("Registration Successful")
                    .type(MessageType.green)
                    .build();

            session.setAttribute("message", message);

            return "redirect:/employee/signup";

        } catch (UserAlreadyExistsException e) {

            model.addAttribute("employee", employeeForm);
            model.addAttribute("error", e.getMessage());

            return "employeeRegister";
        }
    }
    
    @GetMapping("/update_employee/{employeeId}")
    public String updateEmployeeDetails(@PathVariable("employeeId") String employeeId,
    									Model model
    		
    		
    		) {
    	
    	EmployeeForm employeeForm=new EmployeeForm();
    	
    	EmployeeDetails employee =
    			employeeService.getByEmployeeId(employeeId)
    	        .orElseThrow(() -> new RuntimeException("Employee not found"));
    	
    
    	
    	employeeForm.setName(employee.getName());
    	employeeForm.setEmail(employee.getEmail());
    	employeeForm.setPhoneNumber(employee.getPhoneNumber());

    	
    	employeeForm.setRole(employee.getRole());
    	employeeForm.setJoiningDate(employee.getJoiningDate());
    	
    	model.addAttribute("employee",employeeForm);
    	model.addAttribute("employeeId",employeeId);
    	
    	
				return"employeeUpdate" ;
    	
    }
    
	@RequestMapping(value = "/update/{employeeId}", method = RequestMethod.POST)
	public String updateEmployeedetails(
			@Validated(UpdateGroup.class) @ModelAttribute("employee") EmployeeForm employeeForm,
			BindingResult bindingResult, @PathVariable("employeeId") String employeeId, HttpSession session,
			Model model) throws IOException {
		// Validation
		if (bindingResult.hasErrors()) {
			session.setAttribute("message",
					Message.builder().content("Please correct the following errors").type(MessageType.red).build());

			return "employeeUpdate";
		}

		// cloudinaryId
		String filename = UUID.randomUUID().toString();
		String proCloudinaryId = "profile_" + filename;
		String panCloudinaryId = "pan_" + filename;
		String banCloudinaryId = "bank_" + filename;
		String adhCloudinaryId = "adhar_" + filename;
		String degCloudinaryId = "degree_" + filename;
		String expCloudinaryId = "experience_" + filename;

		// Fetch existing employee from DB
		EmployeeDetails employeeDetails = employeeService.getByEmployeeId(employeeId)
				.orElseThrow(() -> new RuntimeException("Employee not found"));

		// Find all file cloudinaryId
		// MediaType IMAGE
		String pro = employeeDetails.getProCloudinaryId();
		String pan = employeeDetails.getPanCloudinaryId();
		String ban = employeeDetails.getBanCloudinaryId();

		// MediaType PDF
		String adh = employeeDetails.getAdhCloudinaryId();
		String deg = employeeDetails.getDegCloudinaryId();
		String exp = employeeDetails.getExpCloudinaryId();

		// Set basic details
		employeeDetails.setEmployeeId(employeeId);
		employeeDetails.setName(employeeForm.getName());
		employeeDetails.setEmail(employeeForm.getEmail());

		employeeDetails.setPhoneNumber(employeeForm.getPhoneNumber());

		employeeDetails.setRole(employeeForm.getRole());

		if (employeeForm.getPassword() != null && !employeeForm.getPassword().trim().isEmpty()) {

			employeeDetails.setPassword(passwordEncoder.encode(employeeForm.getPassword()));

		}

		if (employeeForm.getJoiningDate() != null) {
			employeeDetails.setJoiningDate(employeeForm.getJoiningDate());
		}

		// Profile Picture
if (employeeForm.getProfilePic() != null
        && !employeeForm.getProfilePic().isEmpty()) {

    if (pro != null && !pro.trim().isEmpty()) {
        imageService.deleteCloudinaryFile(pro, "image");
    }

    String profileUrl =
            imageService.uploadFile(
                    employeeForm.getProfilePic(),
                    proCloudinaryId
            );

    employeeDetails.setProfilePic(profileUrl);
    employeeDetails.setProCloudinaryId(proCloudinaryId);
}
		// PAN
if (employeeForm.getPanPic() != null
        && !employeeForm.getPanPic().isEmpty()) {

    if (pan != null && !pan.trim().isEmpty()) {
        imageService.deleteCloudinaryFile(pan, "image");
    }

    String panUrl =
            imageService.uploadFile(
                    employeeForm.getPanPic(),
                    panCloudinaryId
            );

    employeeDetails.setPanPic(panUrl);
    employeeDetails.setPanCloudinaryId(panCloudinaryId);
}
		// Bank Account Pic
if (employeeForm.getBankAccountPic() != null
        && !employeeForm.getBankAccountPic().isEmpty()) {

    if (ban != null && !ban.trim().isEmpty()) {
        imageService.deleteCloudinaryFile(ban, "image");
    }

    String accountUrl =
            imageService.uploadFile(
                    employeeForm.getBankAccountPic(),
                    banCloudinaryId
            );

    employeeDetails.setBankAccountPic(accountUrl);
    employeeDetails.setBanCloudinaryId(banCloudinaryId);
}
		// Aadhaar PDF
if (employeeForm.getAdharPdf() != null
        && !employeeForm.getAdharPdf().isEmpty()) {

    if (adh != null && !adh.trim().isEmpty()) {
        imageService.deleteCloudinaryFile(adh, "PDF");
    }

    String adharUrl =
            imageService.uploadFile(
                    employeeForm.getAdharPdf(),
                    adhCloudinaryId
            );

    employeeDetails.setAdharPdf(adharUrl);
    employeeDetails.setAdhCloudinaryId(adhCloudinaryId);
}
		// Degree PDF
if (employeeForm.getDegreePdf() != null
        && !employeeForm.getDegreePdf().isEmpty()) {

    if (deg != null && !deg.trim().isEmpty()) {
        imageService.deleteCloudinaryFile(deg, "PDF");
    }

    String degreeUrl =
            imageService.uploadFile(
                    employeeForm.getDegreePdf(),
                    degCloudinaryId
            );

    employeeDetails.setDegreePdf(degreeUrl);
    employeeDetails.setDegCloudinaryId(degCloudinaryId);
}
		// Experience Letter (Optional)
if (employeeForm.getExperienceLetterPdf() != null
        && !employeeForm.getExperienceLetterPdf().isEmpty()) {

    if (exp != null && !exp.trim().isEmpty()) {
        imageService.deleteCloudinaryFile(exp, "PDF");
    }

    String experienceLetterUrl =
            imageService.uploadFile(
                    employeeForm.getExperienceLetterPdf(),
                    expCloudinaryId
            );

    employeeDetails.setExperienceLetterPdf(experienceLetterUrl);
    employeeDetails.setExpCloudinaryId(expCloudinaryId);
}
		try {

			EmployeeDetails savedEmployee = employeeService.updateEmployeeDetails(employeeDetails);

			Message message = Message.builder().content("Updated Successfully").type(MessageType.green).build();

			session.setAttribute("message", message);

			return "redirect:/hr/employeeList";

		} catch (UserAlreadyExistsException e) {

			model.addAttribute("employee", employeeForm);
			model.addAttribute("error", e.getMessage());

			return "employeeUpdate";
		}
	}
    
    @GetMapping("/view_employee/{employeeId}")
    public String getEmployeeWithId(@PathVariable("employeeId") String employeeId,Model model) {
    	EmployeeDetails employee =
    			employeeService.getByEmployeeId(employeeId)
    	        .orElseThrow(() -> new RuntimeException("Employee not found"));
    	
    	return "hr/employees";
    	
    }
    
    @GetMapping("/delete/{employeeId}")
    public String deleteEmployee(
            @PathVariable("employeeId") String employeeId,
            HttpSession session,
            Model model
    ) {
		// Fetch existing employee from DB
		EmployeeDetails employeeDetails = employeeService.getByEmployeeId(employeeId)
				.orElseThrow(() -> new RuntimeException("Employee not found"));
		
		//Find all file cloudinaryId
		//MediaType IMAGE
		String pro=employeeDetails.getProCloudinaryId();
		String pan=employeeDetails.getPanCloudinaryId();
		String ban=employeeDetails.getBanCloudinaryId();
		
		//MediaType PDF
		String adh=employeeDetails.getAdhCloudinaryId();
		String deg=employeeDetails.getDegCloudinaryId();
		String exp=employeeDetails.getExpCloudinaryId();
try{
		imageService.deleteCloudinaryFile(pro, "IMAGE");
		imageService.deleteCloudinaryFile(pan, "IMAGE");
		imageService.deleteCloudinaryFile(ban, "IMAGE");

		imageService.deleteCloudinaryFile(adh, "PDF");
		imageService.deleteCloudinaryFile(deg, "PDF");
		imageService.deleteCloudinaryFile(exp, "PDF");
		
		
        employeeService.deleteEmployeeDetails(employeeId);
        
        session.setAttribute("message",
                Message.builder()
                        .content("Employee Deleted Successfully")
                        .type(MessageType.green)
                        .build());

        return "redirect:/hr/employeeList"; // or employee list page
        
}catch(Exception e) {
	model.addAttribute("error", e.getMessage());
	return "redirect:/hr/employeeList";
}

    }
    
	@GetMapping("/profile")
	public String employeeProfile(Model model, Authentication authentication) {

		String email = authentication.getName();

		Optional<EmployeeDetails> employee = employeeService.getEmployeeByEmail(email);

		if (employee.isPresent()) {
			model.addAttribute("employeeProfile", employee.get());
		} else {
			model.addAttribute("employeeProfile", null);
		}

		return "hr/profile";
	}
    
@RequestMapping("/password/reset/{employeeId}")
public String updateEmployeePassword(
        @PathVariable String employeeId,
        Model model) {

    EmployeeDetails employeeDetails =
            employeeService
                    .getByEmployeeId(employeeId)
                    .orElseThrow(() ->
                            new RuntimeException("Employee Not Found"));

    PasswordResetForm passwordResetForm = new PasswordResetForm();

    passwordResetForm.setEmail(employeeDetails.getEmail());

    model.addAttribute("passwordResetForm", passwordResetForm);
    model.addAttribute("employeeId", employeeId);

    return "employeeFile/passwordReset";
}

@RequestMapping(value = "/password/do-reset/{employeeId}", method = RequestMethod.POST)
public String processPasswordReset(

		@Validated(UpdateGroup.class) @ModelAttribute("passwordResetForm") PasswordResetForm passwordResetForm,

		BindingResult bindingResult,

		@PathVariable("employeeId") String employeeId,

		HttpSession session,

		Model model) {

	// =========================
	// Validation
	// =========================

	if (bindingResult.hasErrors()) {

		session.setAttribute("message",
				Message.builder().content("Please correct the following errors").type(MessageType.red).build());

		model.addAttribute("employeeId", employeeId);

		return "employeeFile/passwordReset";
	}

	try {

		// =========================
		// Find Employee
		// =========================

		EmployeeDetails employeeDetails = employeeService.getByEmployeeId(employeeId)
				.orElseThrow(() -> new RuntimeException("Employee Not Found"));

		// =========================
		// Get Form Values
		// =========================

		String email = passwordResetForm.getEmail();
		String password = passwordResetForm.getPassword();
		String confirmPassword = passwordResetForm.getConfirmPassword();

		// =========================
		// Check Email
		// =========================

		if (email == null || email.isBlank()) {

			model.addAttribute("error", "Email is required.");

			model.addAttribute("employeeId", employeeId);

			return "employeeFile/passwordReset";
		}

		if (!employeeDetails.getEmail().equalsIgnoreCase(email.trim())) {

			model.addAttribute("error", "Email does not match the employee record.");

			model.addAttribute("employeeId", employeeId);

			return "employeeFile/passwordReset";
		}

		// =========================
		// Check Password
		// =========================

		if (password == null || password.isBlank()) {

			model.addAttribute("error", "Password is required.");

			model.addAttribute("employeeId", employeeId);

			return "employeeFile/passwordReset";
		}

		// =========================
		// Check Confirm Password
		// =========================

		if (confirmPassword == null || confirmPassword.isBlank()) {

			model.addAttribute("error", "Confirm password is required.");

			model.addAttribute("employeeId", employeeId);

			return "employeeFile/passwordReset";
		}

		// =========================
		// Compare Password
		// =========================

		if (!password.equals(confirmPassword)) {

			model.addAttribute("error", "Password and confirm password do not match.");

			model.addAttribute("employeeId", employeeId);

			return "employeeFile/passwordReset";
		}

		// =========================
		// Password Matched
		// =========================



		employeeDetails.setPassword(passwordEncoder.encode(password));

		employeeService.updateEmployeeDetails(employeeDetails);

		// =========================
		// Success Message
		// =========================

		Message message = Message.builder().content("Password updated successfully").type(MessageType.green).build();

		session.setAttribute("message", message);

		// =========================
		// Redirect
		// =========================

		return "redirect:/employee/password/reset/" + employeeId;

	} catch (Exception e) {

		e.printStackTrace();

		model.addAttribute("error", e.getMessage());

		model.addAttribute("passwordResetForm", passwordResetForm);

		model.addAttribute("employeeId", employeeId);

		return "employeeFile/passwordReset";
	}
}


} 
