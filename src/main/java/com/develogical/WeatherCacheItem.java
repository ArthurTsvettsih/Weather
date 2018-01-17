package com.develogical;

import com.weather.Forecast;

import java.util.Date;

public class WeatherCacheItem {
	public Forecast Weather;
	public Long TimeRetrieved;
	public String Location;

	public WeatherCacheItem(Forecast weather, Long timeRetrieved, String location) {
		Weather = weather;
		TimeRetrieved = timeRetrieved;
		Location = location;
	}
}
