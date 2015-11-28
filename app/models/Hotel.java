package models;

public class Hotel implements Comparable<Hotel> {
	private String city;
	private long hotelId;
	private String room;
	private Integer price;
	public Hotel next;
	
	public Hotel(String city, long hotelId, String room, Integer price) {
		super();
		this.city = city;
		this.hotelId = hotelId;
		this.room = room;
		this.price = price;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public long getHotelId() {
		return hotelId;
	}
	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Hotel [city=" + city + ", hotelId=" + hotelId + ", room="
				+ room + ", price=" + price + "]";
	}
	@Override
	public int compareTo(Hotel o) {
		return o.getPrice().compareTo(this.getPrice());
	}
	
	
}
