package com.technocomp.ems.controller;

import com.technocomp.ems.model.EnroleNotice;
import com.technocomp.ems.model.Notices;
import com.technocomp.ems.model.WhiteBoard;
import com.technocomp.ems.service.EnroleNoticeService;
import com.technocomp.ems.service.WhiteBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;

@Controller
public class WhiteBoardController {

    @Autowired
    WhiteBoardService whiteBoardService;

    @Autowired
    EnroleNoticeService enroleNoticeService;

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

    @RequestMapping(value = "/myWhiteBoard", method = RequestMethod.GET)
    public ModelAndView whiteBoard() {
        ModelAndView modelAndView = new ModelAndView();
        WhiteBoard whiteBoard = new WhiteBoard();
        modelAndView.addObject("whiteBoard", whiteBoard);
        modelAndView.addObject("notices", new Notices());
        modelAndView.addObject("whiteBoardNotices", whiteBoardService.getNoticesByUserID(getUserdetails()));
        modelAndView.setViewName("myWhiteBoard");
        return modelAndView;
    }

    @RequestMapping(value = "/myWhiteBoard", method = RequestMethod.POST)
    public ModelAndView createNewNotice(@Valid WhiteBoard whiteBoard, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("myWhiteBoard");
        } else {
            whiteBoard.setEmail(getUserdetails());
            whiteBoardService.addNotice(whiteBoard);
            modelAndView.addObject("successMessage", "Item  has been registered successfully");
            modelAndView.addObject("whiteBoard", new WhiteBoard());

        }
        modelAndView.addObject("notices", new Notices());
        modelAndView.setViewName("myWhiteBoard");
        modelAndView.addObject("whiteBoardNotices", whiteBoardService.getNoticesByUserID(getUserdetails()));
        return modelAndView;
    }

    @RequestMapping(value = "/whiteBoard/delete/{id}", method = RequestMethod.POST)
    public ModelAndView deleteNotice(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        WhiteBoard whiteBoard = whiteBoardService.findWhiteBoardByID(id);
        boolean delete = whiteBoardService.deleteWhiteBoard(whiteBoard);
        if (delete) {
            modelAndView.addObject("deleteMessage", "Notice has been deleted successfully");
        } else {
            modelAndView.addObject("deleteMessage", "Notice deletion failed");
        }
        modelAndView.addObject("whiteBoard", whiteBoard);
        modelAndView.addObject("notices", new Notices());
        modelAndView.addObject("whiteBoardNotices", whiteBoardService.getNoticesByUserID(getUserdetails()));
        modelAndView.setViewName("myWhiteBoard");

        return modelAndView;
    }

    @RequestMapping(value = "/notices", method = RequestMethod.POST)
    public ModelAndView listOfNoticesToEnrole(HttpSession httpSession, HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "latitudefornotice") String latitudefornotice,
            @RequestParam(value = "longitudefornotice") String longitudefornotice,
            @RequestParam(value = "maxRadius") int maxRadius) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("notices", new Notices());
        httpSession.setAttribute("latitudefornotice", latitudefornotice);
        httpSession.setAttribute("longitudefornotice", longitudefornotice);
        httpSession.setAttribute("maxRadius", maxRadius);
        modelAndView.addObject("noticesAlreadyEnrolled",
                enroleNoticeService.getNoticesAlreadyEnroled(getUserdetails()));
        modelAndView.addObject("noticesNearByLocation",
                whiteBoardService.getNoticesByLocation(latitudefornotice, longitudefornotice, maxRadius, getUserdetails()));
        modelAndView.setViewName("enroleNotices");

        return modelAndView;
    }

    @RequestMapping(value = "/enroleNotices/enrole/{id}", method = RequestMethod.POST)
    public ModelAndView enrollNotice(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        EnroleNotice enroleNotice;
        WhiteBoard whiteBoard = whiteBoardService.findWhiteBoardByID(id);
        HttpSession httpSession = request.getSession();
        String latitudefornotice = httpSession.getAttribute("latitudefornotice").toString();
        String longitudefornotice = httpSession.getAttribute("longitudefornotice").toString();
        int maxRadius = (int) httpSession.getAttribute("maxRadius");
        enroleNotice = enroleNoticeService.findEnroleNoticeByItemIdAndEmail(id, getUserdetails());

        if (whiteBoard.getTotalEnrolled() != whiteBoard.getMaxPratispents()) {
            if (enroleNotice == null) {
                enroleNotice = new EnroleNotice();
                enroleNotice.setItemId(whiteBoard.getId());
                enroleNotice.setItemTitle(whiteBoard.getItemTitle());
                enroleNotice.setItemDescription(whiteBoard.getItemDescription());
                enroleNotice.setEnrolled(true);
                enroleNotice.setEmail(getUserdetails());
                whiteBoard.setTotalEnrolled(whiteBoard.getTotalEnrolled() + 1);
                whiteBoardService.addNotice(whiteBoard);
                enroleNoticeService.save(enroleNotice);
                modelAndView.addObject("enroleMessage", "Notice has been enroled successfully");
            } else {
                modelAndView.addObject("enroleMessage", "You already enrolled this event ");
            }
        } else {
            modelAndView.addObject("enroleMessage", "Max paricipeants reached! ");
        }

        modelAndView.addObject("noticesAlreadyEnrolled",
                enroleNoticeService.getNoticesAlreadyEnroled(getUserdetails()));
        modelAndView.addObject("noticesNearByLocation", whiteBoardService.getNoticesByLocation(latitudefornotice,
                longitudefornotice, maxRadius, getUserdetails()));
        modelAndView.setViewName("enroleNotices");
        return modelAndView;
    }

}
