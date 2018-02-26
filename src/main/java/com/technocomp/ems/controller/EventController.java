package com.technocomp.ems.controller;

import com.technocomp.ems.model.Event;
import com.technocomp.ems.model.EventsMaster;
import com.technocomp.ems.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.Valid;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;

@Controller
public class EventController {

    @Autowired
    EventService eventService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy h:mm");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    public String getUserdetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = "";
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
            return currentUserName;
        }
        return currentUserName;
    }

    @RequestMapping(value = "/event", method = RequestMethod.GET)
    public ModelAndView event() {
        ModelAndView modelAndView = new ModelAndView();
        Event event = new Event();
        modelAndView.addObject("event", event);
        modelAndView.addObject("events", eventService.getEvents(getUserdetails()));
        modelAndView.addObject("eventsList", eventService.getEventsMaster());
        modelAndView.setViewName("event");
        return modelAndView;
    }
    
    @RequestMapping(value = "/maptest", method = RequestMethod.GET)
    public ModelAndView maptest() {
        ModelAndView modelAndView = new ModelAndView();
         modelAndView.setViewName("maptest");
        return modelAndView;
    }

    @RequestMapping(value = "/eventMaster", method = RequestMethod.GET)
    public ModelAndView eventMaster() {
        ModelAndView modelAndView = new ModelAndView();
        EventsMaster eventsMaster = new EventsMaster();
        modelAndView.addObject("eventMaster", eventsMaster);
        modelAndView.addObject("eventsMaster", eventService.getEventsMaster());
        modelAndView.setViewName("eventMaster");
        return modelAndView;
    }

    @RequestMapping(value = "/event", method = RequestMethod.POST)
    public ModelAndView createNewEvent(@Valid Event event, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        EventsMaster eventsMaster = eventService.findEventeMasterByID(Integer.parseInt(event.getEventName()));
        String eventName = eventsMaster.getEventName() + " for " + eventsMaster.getEventCategory();
        Event eventExists = eventService.findEventByDateAndEmailAndEventName(eventName, event.getDateOfEvent(), getUserdetails());
        if (eventExists != null) {
            bindingResult
                    .rejectValue("dateOfEvent", "error.event",
                            "There is already an event registered with the same name on this date and time");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("event");
        } else {
            event.setEmail(getUserdetails());
            eventService.addEvent(event, eventName);
            eventService.sendHtmlMail(event.getEmail(), event.getGuestsEmails(), event.getEventName());
            modelAndView.addObject("successMessage", "Event has been registered successfully");
            modelAndView.addObject("event", new Event());
        }
        
        modelAndView.setViewName("event");
        modelAndView.addObject("events", eventService.getEvents(getUserdetails()));
        modelAndView.addObject("eventsList", eventService.getEventsMaster());
        return modelAndView;
    }

    @RequestMapping(value = "/eventMaster", method = RequestMethod.POST)
    public ModelAndView eventMaster(@Valid EventsMaster eventsMaster, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        EventsMaster eventExists = eventService.findEventMasterByEventNameAndCategory(eventsMaster.getEventName(), eventsMaster.getEventCategory());
        if (eventExists != null) {
            bindingResult
                    .rejectValue("eventName", "error.eventMaster",
                            "There is already an event registered with the same name and category");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("eventMaster");
        } else {
            eventService.addEventMaster(eventsMaster);
            modelAndView.addObject("successMessage", "Event type has been registered successfully");
            modelAndView.addObject("eventMaster", new EventsMaster());
            modelAndView.setViewName("eventMaster");
            modelAndView.addObject("eventsMaster", eventService.getEventsMaster());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/event/delete/{id}", method = RequestMethod.POST)
    public ModelAndView deleteEvent(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Event event = eventService.findEventByID(id);
        boolean delete = eventService.deleteEvent(event);
        if (delete) {
            event.setEmail(getUserdetails());
            modelAndView.addObject("deleteMessage", "Event has been deleted successfully");

        } else {
            modelAndView.addObject("deleteMessage", "Event deletion failed");
        }
        modelAndView.addObject("event", event);
        modelAndView.addObject("events", eventService.getEvents(getUserdetails()));
        modelAndView.addObject("eventsList", eventService.getEventsMaster());
        modelAndView.setViewName("event");

        return modelAndView;
    }

    @RequestMapping(value = "/eventMaster/delete/{id}", method = RequestMethod.POST)
    public ModelAndView deleteEventMaster(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        EventsMaster eventsMaster = eventService.findEventeMasterByID(id);
        boolean delete = eventService.deleteEventMaster(eventsMaster);
        if (delete) {
            modelAndView.addObject("deleteMessage", "Event has been deleted successfully");
        } else {
            modelAndView.addObject("deleteMessage", "Event deletion failed");
        }
        modelAndView.addObject("eventMaster", eventsMaster);
        modelAndView.addObject("eventsMaster", eventService.getEventsMaster());
        modelAndView.setViewName("eventMaster");

        return modelAndView;
    }
}
