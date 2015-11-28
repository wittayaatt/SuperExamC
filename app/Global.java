import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.Hotel;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import utils.HotelStore;


public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {
		File hotelFile = app.getFile("conf/hotel.csv");
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(hotelFile));
			List<Hotel> hotels = new ArrayList<Hotel>();
			String line;
			int i=0;
			while((line=reader.readLine()) != null){
				if(i>0){
					String[] data = line.split(",");
					Hotel hotel = new Hotel(data[0],Long.parseLong(data[1]),data[2],Integer.parseInt(data[3]));
					hotels.add(hotel);
				}
				i++;
			}
			Collections.sort(hotels);
			
			HotelStore hotelStore = HotelStore.getInstance();
			hotelStore.setHotels(hotels);
			hotelStore.setDirection("asc");
			
		}catch(Exception ex){
			Logger.error(ex.toString());
		}finally{
			try{reader.close();}catch(Exception ex){};
		}
		
		super.onStart(app);
	}
	
}
