package com.dev.aircraft_positions.domain;

import java.time.Instant;


import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Aircraft {
	@Id	
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String callsign, squawk, reg, flightno, route, type, category;

    private int altitude, heading, speed;
    @JsonProperty("vert_rate")
    private int vertRate;
    @JsonProperty("selected_altitude")
    private int selectedAltitude;

    private double lat, lon, barometer;
    @JsonProperty("polar_distance")
    private double polarDistance;
    @JsonProperty("polar_bearing")
    private double polarBearing;

    @JsonProperty("is_adsb")
    private boolean isADSB;
    @JsonProperty("is_on_ground")
    private boolean isOnGround;

    @JsonProperty("last_seen_time")
    private Instant lastSeenTime;
    @JsonProperty("pos_update_time")
    private Instant posUpdateTime;
    @JsonProperty("bds40_seen_time")
    private Instant bds40SeenTime;
    
    public Aircraft() {
    	
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCallsign() {
		return callsign;
	}

	public void setCallsign(String callsign) {
		this.callsign = callsign;
	}

	public String getSquawk() {
		return squawk;
	}

	public void setSquawk(String squawk) {
		this.squawk = squawk;
	}

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public String getFlightno() {
		return flightno;
	}

	public void setFlightno(String flightno) {
		this.flightno = flightno;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getAltitude() {
		return altitude;
	}

	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	public int getHeading() {
		return heading;
	}

	public void setHeading(int heading) {
		this.heading = heading;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getVertRate() {
		return vertRate;
	}

	public void setVertRate(int vertRate) {
		this.vertRate = vertRate;
	}

	public int getSelectedAltitude() {
		return selectedAltitude;
	}

	public void setSelectedAltitude(int selectedAltitude) {
		this.selectedAltitude = selectedAltitude;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getBarometer() {
		return barometer;
	}

	public void setBarometer(double barometer) {
		this.barometer = barometer;
	}

	public double getPolarDistance() {
		return polarDistance;
	}

	public void setPolarDistance(double polarDistance) {
		this.polarDistance = polarDistance;
	}

	public double getPolarBearing() {
		return polarBearing;
	}

	public void setPolarBearing(double polarBearing) {
		this.polarBearing = polarBearing;
	}

	public boolean isADSB() {
		return isADSB;
	}

	public void setADSB(boolean isADSB) {
		this.isADSB = isADSB;
	}

	public boolean isOnGround() {
		return isOnGround;
	}

	public void setOnGround(boolean isOnGround) {
		this.isOnGround = isOnGround;
	}

	public Instant getLastSeenTime() {
		return lastSeenTime;
	}

	public void setLastSeenTime(Instant lastSeenTime) {
		this.lastSeenTime = lastSeenTime;
	}

	public Instant getPosUpdateTime() {
		return posUpdateTime;
	}

	public void setPosUpdateTime(Instant posUpdateTime) {
		this.posUpdateTime = posUpdateTime;
	}

	public Instant getBds40SeenTime() {
		return bds40SeenTime;
	}

	public void setBds40SeenTime(Instant bds40SeenTime) {
		this.bds40SeenTime = bds40SeenTime;
	}

	@Override
	public String toString() {
		return "Aircraft [id=" + id + ", callsign=" + callsign + ", squawk=" + squawk + ", reg=" + reg + ", flightno="
				+ flightno + ", route=" + route + ", type=" + type + ", category=" + category + ", altitude=" + altitude
				+ ", heading=" + heading + ", speed=" + speed + ", vertRate=" + vertRate + ", selectedAltitude="
				+ selectedAltitude + ", lat=" + lat + ", lon=" + lon + ", barometer=" + barometer + ", polarDistance="
				+ polarDistance + ", polarBearing=" + polarBearing + ", isADSB=" + isADSB + ", isOnGround=" + isOnGround
				+ ", lastSeenTime=" + lastSeenTime + ", posUpdateTime=" + posUpdateTime + ", bds40SeenTime="
				+ bds40SeenTime + "]";
	}
    
   
    
    
}
