package com.technocomp.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technocomp.ems.model.User;
/**
 * Created by Ravi Varma Yarakaraj on 12/28/2017.
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	 User findByEmail(String email);
}
