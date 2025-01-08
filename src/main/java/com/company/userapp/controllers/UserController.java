package com.company.userapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.company.userapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.company.userapp.exceptions.UserNotFoundException;
import com.company.userapp.services.IUserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	private static final int User = 0;
	@Autowired
	IUserService UserSvc;
	
	@GetMapping()
	public List<User> getAllUsers(){
		 
		var Users = UserSvc.getAllUsers();
		if(Users.size()==0)
			throw new UserNotFoundException("User Not Found");
		else {
			for(User user : Users) {
				int UserId = user.getId();
				Link selfLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(UserId).withRel("self");
				user.add(selfLink);
				Link deleteLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(UserId).withRel("Delete");
				user.add(deleteLink);
				Link updateLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(UserId).withRel("update");
				user.add(updateLink);
			}
			
			return Users;
		}
	}
	
	@GetMapping("/{id}")
	public User getByUserId(@PathVariable int id){
		var User = UserSvc.getById(id);
		if(User == null)
			throw new UserNotFoundException("User Not Found");
		else
			return User;
	}
	
	@PostMapping()
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {	// modelbinding ? spring validation framework
		
		var newUser = UserSvc.addUser(user);
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable int id, @Valid @RequestBody User user) {	// modelbinding ? spring validation framework
		var newUser = UserSvc.updateUser(id, user);
		
		if(newUser != null)
			return new ResponseEntity<User>(newUser, HttpStatus.OK);
		else
			//return (ResponseEntity<com.company.Userapp.models.User>) ResponseEntity.notFound();
			throw new UserNotFoundException("User Not Found");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable int id) {	// modelbinding ? spring validation framework
		var User = UserSvc.deleteUser(id);
		if(User != null)
			return new ResponseEntity<User>(User, HttpStatus.OK);
		else
			//return (ResponseEntity<com.company.Userapp.models.User>) ResponseEntity.notFound();
			throw new UserNotFoundException("User Not Found");
	}
	
	// MethodArgumentNotValidException
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String,String> handleValidationExceptions(MethodArgumentNotValidException ex){
		Map<String,String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}


