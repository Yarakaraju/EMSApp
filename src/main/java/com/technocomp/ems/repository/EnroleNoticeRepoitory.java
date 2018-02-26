/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.technocomp.ems.repository;

import com.technocomp.ems.model.EnroleNotice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ravi Varma Yarakaraj
 */
@Repository
public interface EnroleNoticeRepoitory extends JpaRepository<EnroleNotice, Integer> {

    public EnroleNotice findEnroleNoticeByItemId(Integer id);

    public EnroleNotice findEnroleNoticeByItemIdAndEmail(Integer id, String userdetails);

    public List<EnroleNotice> findEnroleNoticeByEmail(String userdetails);
    
}
