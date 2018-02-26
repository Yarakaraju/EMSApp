/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.technocomp.ems.service;

import com.technocomp.ems.model.EnroleNotice;
import com.technocomp.ems.repository.EnroleNoticeRepoitory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ravi Varma Yarakaraj
 */
@Service
public class EnroleNoticeService {

    @Autowired
    EnroleNoticeRepoitory enroleNoticeRepoitory;
    
    
    public void save(EnroleNotice enroleNotice) {
        enroleNoticeRepoitory.save(enroleNotice);
    }

    public EnroleNotice findEnroleNoticeByItemId(Integer id) {
        return enroleNoticeRepoitory.findEnroleNoticeByItemId(id);
    }

    public EnroleNotice findEnroleNoticeByItemIdAndEmail(Integer id, String userdetails) {
        return enroleNoticeRepoitory.findEnroleNoticeByItemIdAndEmail(id, userdetails);
    }

    public List <EnroleNotice> getNoticesAlreadyEnroled(String userdetails) {
       return enroleNoticeRepoitory.findEnroleNoticeByEmail(userdetails);
    }
    
}
