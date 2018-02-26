package com.technocomp.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technocomp.ems.model.Role;

/**
 * Created by Ravi Varma Yarakaraj on 12/28/2017.
 */
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer>{
	Role findByRole(String role);

}
