/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.springmvctuts.court.service;

import com.springmvctuts.court.domain.Player;
import com.springmvctuts.court.domain.Reservation;
import com.springmvctuts.court.domain.SportType;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Petricioiu
 */
public class ReservationServiceImpl implements ReservationService{

    public static final SportType TENNIS = new SportType(1, "Tennis");
    public static final SportType SOCCER = new SportType(1, "Soccer");
    
    public List<Reservation> reservations;

    public ReservationServiceImpl() {
        reservations = new ArrayList<>();
        reservations.add(new Reservation("Tennis", new GregorianCalendar(2014, 0, 14).getTime(), 16,
                                         new Player("Roger","N/A"), TENNIS));
    }
    
    @Override
    public List<Reservation> query(String courtName) {
        List<Reservation> result = new ArrayList<>();
        for(Reservation reservation : reservations) {
            if (reservation.getCourtName().equals(courtName)) {
                result.add(reservation);
            }
        }
        if (result.isEmpty())
            throw new ReservationNotAvailableException();
        
        return result;
    }
    
}
