package com.develogical;

import com.develogical.Interfaces.WeatherInterface;
import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;

public class WeatherAdaptor implements WeatherInterface {

	public Forecast getWeather(String location){

		Region region = null;

		if (location.equals("London"))
				region = Region.LONDON;

		if (location.equals("Berlin"))
				region = Region.EDINBURGH;

		Forecaster forecaster = new Forecaster();
		Forecast forecast = forecaster.forecastFor(region, Day.MONDAY);

		return forecast;
	}
}