/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.springmvctuts.court.web;

import com.springmvctuts.court.domain.Reservation;
import com.springmvctuts.court.service.ReservationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Petricioiu
 */

@Controller
@RequestMapping ("/reservationQuery")
public class ReservationQueryController {
    private ReservationService reservationService;
    
    @Autowired
    public ReservationQueryController (ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    
    @RequestMapping (method = RequestMethod.GET)
    public void setupForm() {
         
   }
    
    @RequestMapping (method = RequestMethod.POST)
    public String submitForm(@RequestParam("courtName") String courtName, Model model) {
        List<Reservation> reservations = java.util.Collections.emptyList();
        if (courtName != null) {
            reservations = reservationService.query(courtName);
        }
        
        model.addAttribute("reservations", reservations);
        return "reservationQuery";
    }
}
