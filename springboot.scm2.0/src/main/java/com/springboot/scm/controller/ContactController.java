package com.springboot.scm.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
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

import com.springboot.scm.entitis.Contact;
import com.springboot.scm.entitis.User;
import com.springboot.scm.forms.ContactForm;
import com.springboot.scm.forms.ContactSearchForm;
import com.springboot.scm.helpers.AppConstants;
import com.springboot.scm.helpers.Helper;
import com.springboot.scm.helpers.Message;
import com.springboot.scm.helpers.MessageType;
import com.springboot.scm.services.ContactService;
import com.springboot.scm.services.ImageService;
import com.springboot.scm.services.UserService;
import com.springboot.scm.validator.CreateGroup;
import com.springboot.scm.validator.UpdateGroup;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
	
	private Logger logger=org.slf4j.LoggerFactory.getLogger(ContactController.class);
	
	@Autowired
	private ImageService imageService;
	
	@Autowired 
	private ContactService contactService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/add")
	public String addContactView(Model model) {
		
		ContactForm contactForm = new ContactForm();
//		contactForm.setName("Raju");
		model.addAttribute("contactForm",contactForm);
		
		return "/user/add_contact";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String saveContact( @Validated(CreateGroup.class) @ModelAttribute ContactForm contactForm, BindingResult result ,Authentication authentication,HttpSession session) {
		
		//Process the form data
		
		
		Contact contact = new Contact();
		
		String username=Helper.getEmailLoggedInUser(authentication);//find user name by authentication in Helper class
		
		String filename=UUID.randomUUID().toString();
		
//		Validation logic
		if(result.hasErrors()) {
			session.setAttribute("message",Message.builder()
					.content("Please correct the following errors")
					.type(MessageType.red)
					.build());
			return "/user/add_contact";
		}
		
		//Form to==> contact
	    User user=userService.getUserByEmail(username);//email hi username hai
		
	    
//	    image process
	    
//	    logger.info("file information {}", contactForm.getContactImage().getOriginalFilename());
	    String fileURL=imageService.uploadImage(contactForm.getContactImage(),filename);
	    
	    
		contact.setName(contactForm.getName());
		contact.setAddress(contactForm.getAddress());
		contact.setPhoneNumber(contactForm.getPhoneNumber());
		contact.setEmail(contactForm.getEmail());
		
		contact.setPicture(fileURL);
		contact.setCloudinaryImagePublicId(filename);
		contact.setDescription(contactForm.getDescription());
		contact.setFavorite(contactForm.isFavorite());
		contact.setLinkedInLink(contactForm.getLinkedInLink());
		contact.setWebsiteLink(contactForm.getWebsiteLink());
		contact.setUser(user);
		
		
		contactService.save(contact);
		 
//		System.out.println(contactForm);
		Message message=Message.builder()
				.content("Contact submited successfully")
				.type(MessageType.green)
				.build();
		
		session.setAttribute("message",message);
		
		return "redirect:/user/contacts/add";
		
		
		
	}

	
	@RequestMapping
	public String viewContacts(@RequestParam(value="page",defaultValue="0") int page,
			                   @RequestParam(value="size",defaultValue=AppConstants.PAGE_SIZE) int size,
			                   @RequestParam(value="sortBy",defaultValue="name") String sortBy,
			                   @RequestParam(value="direction",defaultValue="asc") String direction,
			                   Model model,Authentication authentication) {
		ContactSearchForm contactSearchForm= new ContactSearchForm();
//		if(contactSearchForm.getField()==null ||contactSearchForm.getValue()==null) {
//			return "user/contacts";
//		}
		
		String username=Helper.getEmailLoggedInUser(authentication);
		User user= userService.getUserByEmail(username);
		
		Page<Contact> pageContacts=contactService.getByUser(user,page,size,sortBy,direction);
		if (pageContacts == null) {
		    pageContacts = Page.empty();
		}
		
		 if (page < 0) {
		        page = 0;
		    }
		 
		model.addAttribute("pageContacts",pageContacts);
		model.addAttribute("pageSize",AppConstants.PAGE_SIZE);
		model.addAttribute("contactSearchForm",contactSearchForm);
		return "user/contacts";
		
	}
	
//	Search Controller
	
	@RequestMapping("/search")
	public String searchHandler(
	        @ModelAttribute ContactSearchForm contactSearchForm,
	        @RequestParam(value="page",defaultValue="0") int page,
	        @RequestParam(value="size",defaultValue=AppConstants.PAGE_SIZE) int size,
	        @RequestParam(value="sortBy",defaultValue="name") String sortBy,
	        @RequestParam(value="direction",defaultValue="asc") String direction,
	        Model model,
	        Authentication authentication
	) {

	    String field = contactSearchForm.getField();
	    String value = contactSearchForm.getValue();

	    if (field == null || field.isEmpty() || value == null || value.isEmpty()) {
	        return "redirect:/user/contacts";   // better redirect
	    }
	    else {
	    	
	    	 if (page < 0) {
	 	        page = 0;
	 	    }

	    String username = Helper.getEmailLoggedInUser(authentication);
	    User user = userService.getUserByEmail(username);

	    logger.info("field {}, keyword {}", field, value);

	    Page<Contact> pageContacts = Page.empty();

	    if ("name".equalsIgnoreCase(field)) {
	        pageContacts = contactService.searchByName(value, page, size, sortBy, direction, user);
	    } else if ("email".equalsIgnoreCase(field)) {
	        pageContacts = contactService.searchByEmail(value, page, size, sortBy, direction, user);
	    } else if ("phone".equalsIgnoreCase(field)) {
	        pageContacts = contactService.searchByPhoneNumber(value, page, size, sortBy, direction, user);
	    }

	    model.addAttribute("contactSearchForm", contactSearchForm);
	    model.addAttribute("pageContacts", pageContacts);
	    model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

	    return "user/search";
	}
	 }
	
	@RequestMapping("/delete/{contactId}")
	public String deleteContact(@PathVariable("contactId") String contactId, HttpSession session) {
		contactService.delete(contactId);
		session.setAttribute("message",
				Message.builder()
				.content("Contact deleted successfully ! ")
				.type(MessageType.green)
				.build());
		return"redirect:/user/contacts";
		
	}
	@GetMapping("/view_contact/{contactId}")
	public String viewContactForm(@PathVariable("contactId") String contactId,Model model) {
//		var contact=contactService.getById(contactId);	
//		model.addAttribute("contactDetails",contact);
//		model.addAttribute("contactId",contactId);
		
		return"redirect:/user/contacts";
	}
	
	@GetMapping("/update_contact/{contactId}")
	public String updateContactForm(@PathVariable("contactId") String contactId,Model model) {
		var contact=contactService.getById(contactId);
		ContactForm contactForm = new ContactForm();
		contactForm.setName(contact.getName());
		contactForm.setEmail(contact.getEmail());
		contactForm.setPhoneNumber(contact.getPhoneNumber());
		contactForm.setAddress(contact.getAddress());
		contactForm.setDescription(contact.getDescription());
		contactForm.setWebsiteLink(contact.getWebsiteLink());
		contactForm.setLinkedInLink(contact.getLinkedInLink());
		contactForm.setFavorite(contact.isFavorite()); // boolean
		contactForm.setPicture(contact.getPicture());
//		contactForm.setContactImage(contact.getPicture());
		
		model.addAttribute("contactForm",contactForm);
		model.addAttribute("contactId",contactId);
		
		return "user/update_contact";
	}
	
	@RequestMapping(value="/update/{contactId}",method=RequestMethod.POST)
	public String updateContactForm(@Validated(UpdateGroup.class) @ModelAttribute ContactForm contactForm,
			BindingResult result,
			@PathVariable("contactId") String contactId,
			HttpSession session) {
		
		//Process the form data
		
		
		Contact contact =contactService.getById(contactId);
		String oldPicture=contactService.getById(contactId).getPicture();
		
//		String username=Helper.getEmailLoggedInUser(authentication);//find user name by authentication in Helper class
		
		
		
//		Validation logic

		if (result.hasErrors()) {
		    session.setAttribute("message", Message.builder()
		            .content("Please correct the following errors")
		            .type(MessageType.red)
		            .build());

		    return "user/update_contact";
		}
		//Form to==> contact
//	    User user=userService.getUserByEmail(username);//email hi username hai
		    
//	    image process
	   
	    
	    contact.setId(contactId);
		contact.setName(contactForm.getName());
		contact.setAddress(contactForm.getAddress());
		contact.setPhoneNumber(contactForm.getPhoneNumber());
		contact.setEmail(contactForm.getEmail());				
		contact.setDescription(contactForm.getDescription());
		contact.setFavorite(contactForm.isFavorite());
		contact.setLinkedInLink(contactForm.getLinkedInLink());
		contact.setWebsiteLink(contactForm.getWebsiteLink());
//		contact.setUser(user);	
		
	    if(contactForm.getContactImage()!=null && !contactForm.getContactImage().isEmpty()) {
	    	
	    	String filename=UUID.randomUUID().toString();
	    	String fileURL=imageService.uploadImage(contactForm.getContactImage(),filename);
			contact.setPicture(fileURL);
		    contactForm.setPicture(fileURL);
			contact.setCloudinaryImagePublicId(filename);
	    	
	    }
	    else {
	    	contact.setPicture(oldPicture);
	    }
		contactService.update(contact);
		
		
		
		Message message=Message.builder()
				.content("Contact updated successfully")
				.type(MessageType.green)
				.build();
		
		session.setAttribute("message",message);
		
		return "redirect:/user/contacts";
		
		
		
	}

}
