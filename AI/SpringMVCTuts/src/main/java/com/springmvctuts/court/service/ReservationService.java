/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.springmvctuts.court.service;

import com.springmvctuts.court.domain.Reservation;
import java.util.List;

/**
 *
 * @author Petricioiu
 */
public interface ReservationService {
    public List<Reservation> query(String courtName);
}
