package com.company.userapp.services;

import java.util.List;

import com.company.userapp.models.User;

public interface IUserService {

	
	public List<User> getAllUsers();
	
	public User getById(int id);
	
	public List<User> getByTitle(String title);
	
	public User addUser(User User);
	
	public User updateUser(int id, User User);
	
	public User deleteUser(int id);
	
}
