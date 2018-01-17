package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;

public class WeatherAdaptor implements WeatherInterface{

	public Forecast getWeather(){
		Forecaster forecaster = new Forecaster();
		Forecast forecast = forecaster.forecastFor(Region.EDINBURGH, Day.MONDAY);

		return forecast;
	}
}
