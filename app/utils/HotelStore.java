package utils;

import java.util.List;

import models.Hotel;

public class HotelStore {
	private List<Hotel> hotels;
	private static HotelStore instance = null;
	private HotelStore(){}
	
	public static HotelStore getInstance(){
		if(instance==null){
			instance = new HotelStore();
		}
		return instance;
	}
	
	private String direction;
	public String getDirection() {
		return direction;
	}


	public void setDirection(String direction) {
		this.direction = direction;
	}

	public List<Hotel> getHotels() {
		return hotels;
	}

	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
	}

}
