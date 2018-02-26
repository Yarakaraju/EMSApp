package com.technocomp.ems.service;

import com.technocomp.ems.model.User;

/**
 * Created by Ravi Varma Yarakaraj on 12/28/2017.
 */

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
	public User addUser(User user);
}
