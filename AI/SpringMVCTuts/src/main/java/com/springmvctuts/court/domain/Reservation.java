/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.springmvctuts.court.domain;

import java.util.Date;

/**
 *
 * @author Petricioiu
 */
public class Reservation {
    
    private String courtName;
    private Date date;
    private int hour;
    private Player player;
    private SportType sportType;

    public Reservation(String courtName, Date date, int hour, Player player, SportType sportType) {
        setCourtName(courtName);
        setDate(date);
        setHour(hour);
        setPlayer(player);
        setSportType(sportType);
    }
    
    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public SportType getSportType() {
        return sportType;
    }

    public void setSportType(SportType sportType) {
        this.sportType = sportType;
    }
    
}
