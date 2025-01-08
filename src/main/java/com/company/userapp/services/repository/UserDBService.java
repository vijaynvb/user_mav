package com.company.userapp.services.repository;

import java.util.List;
import java.util.Optional;

import com.company.userapp.models.User;
import com.company.userapp.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.userapp.repository.UserJPARepository;
import com.company.userapp.services.IUserService;
@Service
public class UserDBService implements IUserService{
	// h2
	
	@Autowired
	private UserJPARepository UserDbRepo;
	
	@Override
	public List<User> getAllUsers() {
		// User Auto-generated method stub
		var Users = UserDbRepo.findAll();
		return Users;
	}

	@Override
	public User getById(int id) {
		// User Auto-generated method stub
		var Users = UserDbRepo.findById(id);
		return Users.get();
	}

	@Override
	public User addUser(User user) {
		// User Auto-generated method stub
		return UserDbRepo.save(user);
	}

	@Override
	public User updateUser(int id, User user) {
		// User Auto-generated method stub
		Optional<User> existingUser = UserDbRepo.findById(id);
		if(existingUser.isPresent()) {
			User updateUser = existingUser.get();
			updateUser.setTitle(user.getTitle());
			return UserDbRepo.save(user);
		}
		return null;
		
	}

	@Override
	public User deleteUser(int id) {
		// User Auto-generated method stub
		Optional<User> existingUser = UserDbRepo.findById(id);
		if(existingUser.isPresent()) {
			UserDbRepo.deleteById(id);
			return existingUser.get();
		}
		return null;
	}

	@Override
	public List<User> getByTitle(String title) {
		// User Auto-generated method stub
				var Users = UserDbRepo.findByTitle(title);
				return Users;
	}

}
