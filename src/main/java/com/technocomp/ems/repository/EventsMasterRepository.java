package com.technocomp.ems.repository;

import com.technocomp.ems.model.EventsMaster;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsMasterRepository extends JpaRepository<EventsMaster, Integer> {

    public List<EventsMaster> findAllByOrderByEventCategory();
    
    public EventsMaster findEventMasterByEventNameAndEventCategory(String eventName, String eventCategory);

    public EventsMaster findEventMasterByEventName(String eventName);

}
