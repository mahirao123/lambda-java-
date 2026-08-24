package com.springboot.scm.employeeController;

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

import com.springboot.scm.employeeEntitis.EmployeeDetails;
import com.springboot.scm.employeeServices.EmployeeService;
import com.springboot.scm.entitis.SocialMediaUrls;
import com.springboot.scm.forms.EmployeeForm;
import com.springboot.scm.forms.EmployeeSearchForm;
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
        employeeDetails.setExperienceLetterPdf(experienceLetterUrl);

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
    	
    	System.out.println("Joining Date "+employee.getJoiningDate());
    	
    	employeeForm.setName(employee.getName());
    	employeeForm.setEmail(employee.getEmail());
    	employeeForm.setPhoneNumber(employee.getPhoneNumber());
//    	employeeForm.setEnable(employee.getEnable());
    	employeeForm.setPassword(employee.getPassword());
    	employeeForm.setRole(employee.getRole());
    	employeeForm.setJoiningDate(employee.getJoiningDate());
    	
    	model.addAttribute("employee",employeeForm);
    	model.addAttribute("employeeId",employeeId);
    	
    	
				return"employeeUpdate" ;
    	
    }
    
    @RequestMapping(value="/update/{employeeId}",method=RequestMethod.POST)
    public String updateEmployeedetails(
    		@Validated(UpdateGroup.class)
    		@ModelAttribute("employee") EmployeeForm employeeForm,
    		BindingResult bindingResult,
    		@PathVariable("employeeId") String employeeId,
    		HttpSession session,
    		Model model
    ) {
        // Validation
        if (bindingResult.hasErrors()) {
			session.setAttribute("message",Message.builder()
					.content("Please correct the following errors")
					.type(MessageType.red)
					.build());

            return "employeeUpdate";
        }
        
        // Fetch existing employee from DB
        EmployeeDetails employeeDetails =
                employeeService.getByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
     // Set basic details
        employeeDetails.setEmployeeId(employeeId);
        employeeDetails.setName(employeeForm.getName());
        employeeDetails.setEmail(employeeForm.getEmail());
        
        employeeDetails.setPhoneNumber(employeeForm.getPhoneNumber());
        
        employeeDetails.setRole(employeeForm.getRole());
        
        String filename = UUID.randomUUID().toString();
        System.out.println("Employee password "+employeeForm.getPassword());
        
        if(employeeForm.getPassword() != null &&
        		   !employeeForm.getPassword().trim().isEmpty()) {

        	  employeeDetails.setPassword(passwordEncoder.encode(employeeForm.getPassword()));
        		}

        		if(employeeForm.getJoiningDate() != null) {
        		    employeeDetails.setJoiningDate(employeeForm.getJoiningDate());
        		}

        // Profile Picture
        if (employeeForm.getProfilePic() != null
                && !employeeForm.getProfilePic().isEmpty()) {

           String profileUrl = imageService.uploadFile(
                    employeeForm.getProfilePic(),
                    "profile_" + filename
            );
            
            employeeDetails.setProfilePic(profileUrl);
        }

        // PAN
        if (employeeForm.getPanPic() != null
                && !employeeForm.getPanPic().isEmpty()) {

         String   panUrl = imageService.uploadFile(
                    employeeForm.getPanPic(),
                    "pan_" + filename
            );
            
            employeeDetails.setPanPic(panUrl);
        }

        // Bank Account Pic
        if (employeeForm.getBankAccountPic() != null
                && !employeeForm.getBankAccountPic().isEmpty()) {

           String  accountUrl = imageService.uploadFile(
                    employeeForm.getBankAccountPic(),
                    "bank_" + filename
            );
            employeeDetails.setBankAccountPic(accountUrl);
        }

        // Aadhaar PDF
        if (employeeForm.getAdharPdf() != null
                && !employeeForm.getAdharPdf().isEmpty()) {

          String  adharUrl = imageService.uploadFile(
                    employeeForm.getAdharPdf(),
                    "adhar_" + filename
            );
            
            employeeDetails.setAdharPdf(adharUrl);
        }

        // Degree PDF
        if (employeeForm.getDegreePdf() != null
                && !employeeForm.getDegreePdf().isEmpty()) {

           String  degreeUrl = imageService.uploadFile(
                    employeeForm.getDegreePdf(),
                    "degree_" + filename
            );
            
            employeeDetails.setDegreePdf(degreeUrl);
        }

        // Experience Letter (Optional)
        if (employeeForm.getExperienceLetterPdf() != null
                && !employeeForm.getExperienceLetterPdf().isEmpty()) {

         String   experienceLetterUrl = imageService.uploadFile(
                    employeeForm.getExperienceLetterPdf(),
                    "experience_" + filename
            );
            
            employeeDetails.setExperienceLetterPdf(experienceLetterUrl);
        }
       
        
        
        
        
       
       

        try {

            EmployeeDetails savedEmployee =
                    employeeService.updateEmployeeDetails(employeeDetails);


            Message message = Message.builder()
                    .content("Updated Successfully")
                    .type(MessageType.green)
                    .build();

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
            HttpSession session
    ) {

        employeeService.deleteEmployeeDetails(employeeId);

        session.setAttribute("message",
                Message.builder()
                        .content("Employee Deleted Successfully")
                        .type(MessageType.green)
                        .build());

        return "redirect:/hr/employeeList"; // or employee list page
    }
    
    @GetMapping("/profile")
    public String employeeProfile(Model model, Authentication authentication) {

        String email = authentication.getName();

        Optional<EmployeeDetails> employee =
                employeeService.getEmployeeByEmail(email);

        if (employee.isPresent()) {
            model.addAttribute("employeeProfile", employee.get());
        } else {
            model.addAttribute("employeeProfile", null);
        }

        return "hr/profile";
    }
    

} 
