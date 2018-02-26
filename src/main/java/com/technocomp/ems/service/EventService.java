package com.technocomp.ems.service;

import com.technocomp.ems.util.email.EmailService;
import com.technocomp.ems.model.Event;
import com.technocomp.ems.model.EventsMaster;
import com.technocomp.ems.repository.EventRepository;
import com.technocomp.ems.repository.EventsMasterRepository;
import com.technocomp.ems.util.email.Email;
import com.technocomp.ems.util.email.EmailTemplate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EventsMasterRepository eventsMasterRepository;

    @Autowired
    EmailService emailService;

    public void sendTextMail() {

        String from = "";
        String to = "";
        String subject = "Java Mail with Spring Boot - Plain Text";

        EmailTemplate template = new EmailTemplate("eventMailPlain.txt");

        Map<String, String> replacements = new HashMap<String, String>();
        replacements.put("user", "ravi");
        replacements.put("today", String.valueOf(new Date()));

        String message = template.getTemplate(replacements);

        Email email = new Email(from, to, subject, message);

        emailService.send(email);
    }

    public void sendHtmlMail(String from, String to, String subject) {

        EmailTemplate template = new EmailTemplate("eventMail.html");
        System.out.println("After email templateinit ");
        Map<String, String> replacements = new HashMap<String, String>();
        replacements.put("user", to);
        replacements.put("today", String.valueOf(new Date()));
        System.out.println("After replacement params  ");
        String message = template.getTemplate(replacements);
        System.out.println("After getting template with params  ");
        Email email = new Email(from, to, subject, message);
        System.out.println("After email object creation  ");
        email.setHtml(true);
        emailService.send(email);
        System.out.println("After send email template with params  ");
    }

    public Event addEvent(Event event) {
        eventRepository.save(event);
        return event;
    }
    
    public Event addEvent(Event event, String eventName) {
        event.setEventName(eventName);
        eventRepository.save(event);
        return event;
    }

    public Iterable<Event> getEvents(String email) {
        return eventRepository.findEventsByEmail(email);
    }

    public Event findEventByDate(Date dateOfEvent) {
        return eventRepository.findEventByDateOfEvent(dateOfEvent);
    }

    public boolean deleteEvent(Event event) {
        try {
            eventRepository.delete(event);
            return true;
        } catch (Exception e) {
            System.out.println(" Exception is : " + e);
            return false;
        }
    }

    public Event findEventByID(int eventNumber) {
        return eventRepository.findOne(eventNumber);
    }

    public Event findEventByDateAndEmail(Date dateOfEvent, String email) {
        return eventRepository.findEventByEmailAndDateOfEvent(email, dateOfEvent);
    }

    public Event findEventByDateAndEmailAndEventName(String eventName, Date dateOfEvent, String email) {
        return eventRepository.findEventByEventNameAndEmailAndDateOfEvent(eventName, email, dateOfEvent);
    }

    public EventsMaster findEventMasterByEventName(String eventName) {
        return eventsMasterRepository.findEventMasterByEventName(eventName);
    }

    public EventsMaster addEventMaster(EventsMaster eventsMaster) {
        return eventsMasterRepository.save(eventsMaster);
    }

    public List<EventsMaster> getEventsMaster() {
        return eventsMasterRepository.findAllByOrderByEventCategory();
    }

    public EventsMaster findEventMasterByEventNameAndCategory(String eventName, String eventCategory) {
        return eventsMasterRepository.findEventMasterByEventNameAndEventCategory(eventName, eventCategory);
    }

    public EventsMaster findEventeMasterByID(Integer id) {
        return eventsMasterRepository.findOne(id);
    }

    public boolean deleteEventMaster(EventsMaster eventsMaster) {
        try {
            eventsMasterRepository.delete(eventsMaster);
            return true;
        } catch (Exception e) {
            System.out.println(" Exception is : " + e);
            return false;
        }
    }
}
