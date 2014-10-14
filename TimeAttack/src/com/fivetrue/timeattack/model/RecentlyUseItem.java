package com.fivetrue.timeattack.model;

import com.api.google.directions.entry.DirectionsEntry;
import com.api.google.place.entry.PlacesEntry;
import com.api.seoul.subway.entry.SubwayInfoEntry;

public class RecentlyUseItem {
	
	private DirectionsEntry direction = null;
	
	private SubwayInfoEntry subwayInfo = null;
	
	private PlacesEntry place = null;

	public DirectionsEntry getDirection() {
		return direction;
	}
	public void setDirection(DirectionsEntry direction) {
		this.direction = direction;
	}
	public SubwayInfoEntry getSubwayInfo() {
		return subwayInfo;
	}
	public void setSubwayInfo(SubwayInfoEntry subwayInfo) {
		this.subwayInfo = subwayInfo;
	}
	public PlacesEntry getPlace() {
		return place;
	}
	public void setPlace(PlacesEntry place) {
		this.place = place;
	}
	
}
