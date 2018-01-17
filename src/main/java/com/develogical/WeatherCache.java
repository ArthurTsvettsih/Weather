package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;

public class WeatherCache implements WeatherInterface{
	private final WeatherInterface weatherInterface;
	private Forecast latestWeather;

//	public static void main(String[] args) {
//		// This is just an example of using the 3rd party API - delete this class before submission.
//		Forecaster forecaster = new Forecaster();
//
//		Forecast londonForecast = forecaster.forecastFor(Region.LONDON, Day.MONDAY);
//
//		System.out.println("London outlook: " + londonForecast.summary());
//		System.out.println("London temperature: " + londonForecast.temperature());
//
//		Forecast edinburghForecast = forecaster.forecastFor(Region.EDINBURGH, Day.MONDAY);
//
//		System.out.println("Edinburgh outlook: " + edinburghForecast.summary());
//		System.out.println("Edinburgh temperature: " + edinburghForecast.temperature());
//	}


	public WeatherCache(WeatherInterface weatherInterface) {
		this.weatherInterface = weatherInterface;
	}

	@Override
	public Forecast getWeather() {
		if(latestWeather == null) {
			latestWeather = weatherInterface.getWeather();
		}
		return latestWeather;
	}
}
