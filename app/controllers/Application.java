package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import models.Hotel;
import annotations.RequiredAuthentication;

import com.fasterxml.jackson.databind.JsonNode;

import play.*;
import play.libs.Json;
import play.mvc.*;
import utils.HotelStore;
import utils.RateLimitAgent;
import views.html.*;

public class Application extends Controller {
	public Result index() {
        return ok();
    }
	
	
    public Result authenticate() {
    	String token = RateLimitAgent.newToken();
    	response().setHeader("x-access-token", token);
        return ok();
    }

    @With(RequiredAuthentication.class)
    public Result searchHotel(String city,String sort) {
    	if(sort==null){
    		sort = "asc";
    	}
    	HotelStore hotelStore = HotelStore.getInstance();
    	List<Hotel> hotels = hotelStore.getHotels();
    	List<Hotel> hotelMetCondition = new ArrayList<Hotel>();
    	for(int i=0;i<hotels.size();i++){
    		Hotel hotel = hotels.get(i);
    		if(hotel.getCity().trim().toLowerCase().equals(city.trim().toLowerCase())){
    			hotelMetCondition.add(hotel);
    		}
    	}
    	
    	if(!sort.trim().toLowerCase().equals("desc")){
    		Collections.reverse(hotelMetCondition);
    	}
    	
    	JsonNode json = Json.toJson(hotelMetCondition);
        return ok(json);
    }
}
