package com.springboot.scm.employeeController;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.scm.employeeEntitis.EmployeeDetails;
import com.springboot.scm.employeeServices.EmployeeService;
import com.springboot.scm.entitis.Complain;
import com.springboot.scm.entitis.Opening;
import com.springboot.scm.entitis.Podcast;
import com.springboot.scm.entitis.Slider;
import com.springboot.scm.forms.ComplainForm;
import com.springboot.scm.forms.EmployeeSearchForm;
import com.springboot.scm.forms.OpeningForm;
import com.springboot.scm.forms.PodcastForm;
import com.springboot.scm.forms.SliderForm;
import com.springboot.scm.helpers.AppConstants;
import com.springboot.scm.helpers.Message;
import com.springboot.scm.helpers.MessageType;
import com.springboot.scm.helpers.ResourceNotFoundException;
import com.springboot.scm.helpers.UserAlreadyExistsException;
import com.springboot.scm.services.ComplainService;
import com.springboot.scm.services.ImageService;
import com.springboot.scm.services.OpeningService;
import com.springboot.scm.services.PodcastService;
import com.springboot.scm.services.SliderService;
import com.springboot.scm.validator.CreateGroup;
import com.springboot.scm.validator.UpdateGroup;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/hr")
public class HrController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private OpeningService openingService;
	
	@Autowired
	private ImageService cloudinaryService;
	
	
	@Autowired
	private ComplainService complainService;
	
	@Autowired
	private PodcastService podcastService;
	
	Page<EmployeeDetails> employeePage;
	
	@RequestMapping("/dashboard")
	public String hrDashboard(Model model) {
		List<Opening> opening=openingService.getAllOpening();
		
		model.addAttribute("openingPage",opening);
	//	   model.addAttribute("employeePage",employeePage);
	
	return "hr/dashboard";
	}
	
	   @RequestMapping("/employeeList")
	public String totalEmployee(@RequestParam(value="page",defaultValue="0") int page,
	@RequestParam(value="size",defaultValue=AppConstants.PAGE_SIZE) int size,
	@RequestParam(value="sortBy",defaultValue="name") String sortBy,
	@RequestParam(value="direction",defaultValue="asc") String direction,
			
			
			Model model) {
	   
	   EmployeeSearchForm employeeSearchForm=new EmployeeSearchForm();
	  
	   employeePage=  employeeService.getAll(size,page,sortBy,direction);
		if (employeePage == null) {
		    employeePage = Page.empty();
		}
		
		 if (page < 0) {
		        page = 0;
		    }
	
	 System.out.println("employeeData "+employeePage);
	 model.addAttribute("employeeSearchForm",employeeSearchForm);
	 model.addAttribute("pageSize",AppConstants.PAGE_SIZE);
	model.addAttribute("employeePage", employeePage);	
	return "hr/employees";
		    }
		   
	
		   
	@GetMapping("/searchEmployee")
	public String searchEmployee(
	        @ModelAttribute EmployeeSearchForm employeeSearchForm,
	        Model model) {
	
	    String field = employeeSearchForm.getField();
	    String value = employeeSearchForm.getValue();
	
	    // Validation
	if ((field == null || field.isBlank()) ||
	    (value == null || value.isBlank())) {
	
	    return "redirect:/hr/employeeList";
	}
	
	System.out.println("employee field " + field);
	System.out.println("employee value " + value);
	
	// Always use List
	List<EmployeeDetails> employeeList = new ArrayList<>();
	
	try {
	
	    // Employee ID -> Optional<EmployeeDetails>
	if ("employeeId".equalsIgnoreCase(field)) {
	
	    employeeService.getByEmployeeId(value)
	            .ifPresent(employeeList::add);
	}
	
	// Name -> List<EmployeeDetails>
	else if ("name".equalsIgnoreCase(field)) {
	
	    employeeList = employeeService.getEmployeeByName(value);
	}
	
	// Email -> Optional<EmployeeDetails>
	else if ("email".equalsIgnoreCase(field)) {
	
	    employeeService.getEmployeeByEmail(value)
	            .ifPresent(employeeList::add);
	}
	
	// Role -> List<EmployeeDetails>
	else if ("role".equalsIgnoreCase(field)) {
	
	employeeList = employeeService.getByRole("role_" + value);
	}
	
	// Phone -> Optional<EmployeeDetails>
	else if ("phoneNumber".equalsIgnoreCase(field)) {
	
	        employeeService.getByPhoneNumber(value)
	                .ifPresent(employeeList::add);
	    }
	
	} catch (Exception e) {
	
	    model.addAttribute("errorMessage", e.getMessage());
	    e.printStackTrace();
	}
	
	System.out.println(employeeList);
	model.addAttribute("employeeList", employeeList);
	model.addAttribute("employeeSearchForm", employeeSearchForm);
	
	return "hr/employeeSearch";
	}
	
	
	
	//View all openings
	@RequestMapping("/viewOpening")
	public String viewOpening(Model model) {
		model.addAttribute("openingPage",openingService.getAllOpening());
	
	
	return "hr/view_opening";
	}
	
	@RequestMapping(value="/openings/save",method=RequestMethod.POST)
	public String processNewOpening(
						@Validated(CreateGroup.class)
						@ModelAttribute OpeningForm openingForm,
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
	
	return "hr/add_opening";
	}
	
	Opening opening=new Opening();
	
	opening.setRole(openingForm.getRole());
	opening.setLink(openingForm.getLink());
	opening.setStartDate(openingForm.getStartDate());
	opening.setEndDate(openingForm.getEndDate());
	opening.setAbout(openingForm.getAbout());
	
	try {
	openingService.saveOpening(opening);
	
	System.out.println(opening);
	
	Message message = Message.builder()
	        .content("Opening Added Successfully")
	        .type(MessageType.green)
	        .build();
	
	session.setAttribute("message", message);
	
	
	return "redirect:/hr/viwOpening";
	}catch(Exception e) {
		
		model.addAttribute("openingForm",openingForm);
	model.addAttribute("error",e.getMessage());
	
	return "hr/add_opening";
	    }
	}
	
	@RequestMapping("opening")
	public String addNewOpening(Model model) {
		
		OpeningForm openingForm= new OpeningForm();
		
		model.addAttribute("openingForm", openingForm);
	
	return"hr/add_opening";
	}
	@RequestMapping("/opening/delete/{id}")
	public String deleteOpening( @PathVariable String id,HttpSession session) {
		
		openingService.deleteOpening(id);
		
		session.setAttribute("message", Message.builder()
	.content("Opening Deleted Successfully")
	    .type(MessageType.green)
	    .build());
	
	return "redirect:/hr/viewOpening"; // or employee list page
		
	}
	
	@GetMapping("/opening/update/{id}")
	public String updateOpening(Model model,@PathVariable String id) {
		OpeningForm openingForm=new OpeningForm();
		
		Opening opening= openingService.getById(id).orElseThrow(()-> new RuntimeException("Opening Not Found"));
	
	openingForm.setRole(opening.getRole());
	openingForm.setAbout(opening.getAbout());
	openingForm.setStartDate(opening.getStartDate());
	openingForm.setEndDate(opening.getEndDate());
	openingForm.setLink(opening.getLink());
	
	model.addAttribute("openingForm",openingForm);
	model.addAttribute("id",id);
	
	
	return "hr/update_opening";
		
	}
	
	@RequestMapping(value="/update_openings/{id}",method=RequestMethod.POST)
	public String updateOpening(@Valid 
								@ModelAttribute OpeningForm openingForm,
								BindingResult bindingResult,HttpSession session,
								@PathVariable String id,
								Model model) {
	    // Validation
	if (bindingResult.hasErrors()) {
		session.setAttribute("message",Message.builder()
	.content("Please correct the following errors")
			.type(MessageType.red)
			.build());
	
	return "hr/update_opening";
	}
		Opening opening= openingService.getById(id).orElseThrow(()-> new RuntimeException("Opening Not Found"));
		
		opening.setId(id);
		opening.setRole(openingForm.getRole());
		opening.setAbout(openingForm.getAbout());
		opening.setStartDate(openingForm.getStartDate());
		opening.setEndDate(openingForm.getEndDate());
		opening.setLink(openingForm.getLink());
	try {
		openingService.updateOpening(opening);
	    Message message = Message.builder()
	            .content("Updated Successfully")
	        .type(MessageType.green)
	        .build();
	
	session.setAttribute("message", message);
	
	return "redirect:/hr/viewOpening";
	    
	}catch(UserAlreadyExistsException e) {
		
		model.addAttribute("openingForm",openingForm);
	model.addAttribute("error",e.getMessage());
	
	return "hr/update_opening";
		}
	    
		
	}

	@RequestMapping("/complainLinks/save")
	public String saveComplain(Model model) {
		
		ComplainForm complainForm=new ComplainForm();
		
		model.addAttribute("complainForm",complainForm);
	
	List<Complain>complain=complainService.getAllComplain();
	
	model.addAttribute("complainPage",complain);
	
	return"hr/complain_form";
	}
	
	@PostMapping(value="/complainLinks/add")
	public String processComplain(
			@Valid
			@ModelAttribute ComplainForm complainForm,
	        BindingResult bindingResult,
	        HttpSession session,
	        Model model) {
		
	if(bindingResult.hasErrors()) {
			
			session.setAttribute("message", Message.builder()
	.content("Correct folowing errors")
			.type(MessageType.red)
			.build());
	List<Complain>complain1=complainService.getAllComplain();
	
	model.addAttribute("complainPage",complain1);
	return"hr/complain_form";
		}
		
	Complain complain =new Complain();
	
	String id=UUID.randomUUID().toString();
	
	complain.setComplainId(id);
	
	complain.setTitle(complainForm.getTitle());
	complain.setComplainUrl(complainForm.getComplainUrl());
	complain.setComplainResultUrl(complainForm.getComplainResultUrl());
			try {
				
				complainService.saveCmplainFormUrl(complain);
				
				Message message=Message.builder()
						.content("Slide submited successfully")
			.type(MessageType.green)
			.build();
	
	session.setAttribute("message",message);
	
	return"redirect:/hr/complainLinks/save";
		
	}catch(Exception e) {
		e.getStackTrace();
		model.addAttribute("error",e.getMessage());
	
	model.addAttribute("complainForm",complainForm);
	
	return"hr/complain_form";
			}
	}

	
	@RequestMapping("/complainLinks/delete/{complainId}")
	public String viewComplainLinks(@PathVariable String complainId,
			                          HttpSession session) {
		try {
			
			complainService.deleteComplainFormUrl(complainId);
			session.setAttribute("message", Message.builder()
	.content(" Form link Deleted Successfully")
			    .type(MessageType.green)
			    .build());
		
	}catch(Exception e) {
		e.getStackTrace();
	}
	
	return "redirect:/hr/complainLinks/save";
	}

@RequestMapping("/podcast/save")
public String savePodcast(Model model) {
	PodcastForm podcastForm=new PodcastForm();
	model.addAttribute("podcastForm",podcastForm);
	
	return "hr/addPodcast";
	
}

@RequestMapping(value="/podcast/add",method=RequestMethod.POST)
public String processPodcast(@Valid
							@ModelAttribute PodcastForm podcastForm,
							BindingResult bindingResult,
							HttpSession session,
							Model model) {
	
    // Validation
if (bindingResult.hasErrors()) {
	session.setAttribute("message",Message.builder()
.content("Please correct the following errors")
		.type(MessageType.red)
		.build());

return "hr/addPodcast";
}

Podcast podcast=new Podcast();

podcast.setEnable("1");
podcast.setHostName(podcastForm.getHostName());
podcast.setGuestName(podcastForm.getGuestName());
podcast.setDate(podcastForm.getDate());
podcast.setTime(podcastForm.getTime());
podcast.setDuration(podcastForm.getDuration());
podcast.setRemark(podcastForm.getRemark());
podcast.setPodcastUrl(podcastForm.getPodcastUrl());

try {
	podcastService.savePodcast(podcast);
	Message message = Message.builder()
	        .content("Podcast Added Successfully")
	        .type(MessageType.green)
	        .build();
	
	session.setAttribute("message", message);
	
	
	return "redirect:/hr/podcast/view";
}catch(Exception e) {
	
	model.addAttribute("podcastForm",podcastForm);
	model.addAttribute("error",e.getMessage());
	return "hr/addPodcast";
}

}

@RequestMapping("/podcast/view")
public String podcastForPublicView(@RequestParam(defaultValue="") String keyword,
									@RequestParam(value = "date", required = false) String dateParam,
									@RequestParam(value="page",defaultValue="0") int page,
									@RequestParam(value="size",defaultValue="10") int size,
									@RequestParam(value="sortBy",defaultValue="date") String sortBy,
									@RequestParam(value="direction",defaultValue="desc") String direction,
									Model model) {
	
	Page<Podcast> 	podcast=podcastService.getAllPodcast(size,page,sortBy,direction);
	
	var search = keyword == null ? "" : keyword.trim().toLowerCase();
	
	 if (page < 0) {
	        page = 0;
	    }

	if(podcast==null) {
		podcast=Page.empty();
	}
   
	//Search by fields

    Date date = null;

    if (dateParam != null && !dateParam.isBlank()) {
        date = Date.valueOf(dateParam);
    }
    
	if(keyword==null || keyword.isBlank()) {
		model.addAttribute("podcast",podcast);
	}
	
	else {
		Page<Podcast> 	 searchPodcast=podcastService.searchByFieldsName(search, search, date, search, page, size, sortBy, direction);
		model.addAttribute("podcast",searchPodcast);
		
	}
	model.addAttribute("pageSize","10");
	return"hr/viewPodcast";
}


@RequestMapping("/podcast/delete/{id}")
public String deletePodcast(@PathVariable Long id,HttpSession session,Model model) {
	
	podcastService.deletePodcast(id);
	session.setAttribute("message", Message.builder()
			.content(" Podcast Deleted Successfully")
					    .type(MessageType.green)
					    .build());
	
	return "redirect:/hr/podcast/view";
}

@RequestMapping("podcast/update/{id}")
public String updatePodcast(@PathVariable Long id,Model model) {
	PodcastForm podcastForm=new PodcastForm();
	
	Podcast podcast=podcastService.getById(id).orElseThrow(()->new RuntimeException("Podcast not found"));
	podcastForm.setHostName(podcast.getHostName());
	podcastForm.setGuestName(podcast.getGuestName());
	podcastForm.setDate(podcast.getDate());
	podcastForm.setTime(podcast.getTime());
	podcastForm.setDuration(podcast.getDuration());
	podcastForm.setRemark(podcast.getRemark());
	podcastForm.setPodcastUrl(podcast.getPodcastUrl());
	
	model.addAttribute("id", id);
	model.addAttribute("podcastForm", podcastForm);
	
	
	return"hr/updatePodcast";
}

@RequestMapping(value="/podcast/update/{id}",method=RequestMethod.POST)
public String processUpdatePodcast(@Valid 
								   @ModelAttribute PodcastForm podcastForm,
								   BindingResult bindingResult,HttpSession session,
								   @PathVariable Long id,
								   Model model) {
	
// Validation
if (bindingResult.hasErrors()) {
	session.setAttribute("message",Message.builder()
.content("Please correct the following errors")
		.type(MessageType.red)
		.build());

return "hr/updatePodcast";
}
	
Podcast podcast=podcastService.getById(id).orElseThrow(()-> new RuntimeException("Podcast not found"));
podcast.setHostName(podcastForm.getHostName());
podcast.setGuestName(podcastForm.getGuestName());
podcast.setDate(podcastForm.getDate());
podcast.setTime(podcastForm.getTime());
podcast.setDuration(podcastForm.getDuration());
podcast.setRemark(podcastForm.getRemark());
podcast.setPodcastUrl(podcastForm.getPodcastUrl());

try {
	    podcastService.updatePodcast(podcast);
	    Message message = Message.builder()
	            .content("Updated Successfully")
	        .type(MessageType.green)
	        .build();
	
	session.setAttribute("message", message);
	
	return "redirect:/hr/podcast/view";
	    
}catch(Exception e) {
	model.addAttribute("podcastForm",podcastForm);
	model.addAttribute("error",e.getMessage());
	
	return"hr/updatePodcast";
}

}



}
