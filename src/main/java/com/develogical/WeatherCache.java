package com.develogical;

import com.weather.Forecast;

import java.util.LinkedList;

public class WeatherCache implements WeatherInterface{
	private final WeatherInterface weatherInterface;
	private LinkedList<Pair<String, Forecast>> weathers;
	private int MaxCacheSize;

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


	public WeatherCache(WeatherInterface weatherInterface, int cacheSize) {
		this.weatherInterface = weatherInterface;
		this.weathers = new LinkedList<>();
		this.MaxCacheSize = cacheSize;
	}

	@Override
	public Forecast getWeather(String location) {

		for (Pair weather : weathers)
			if (weather.getL().equals(location))
				return (Forecast) weather.getR();

		Forecast weather = weatherInterface.getWeather(location);

		weathers.addFirst(new Pair<>(location, weather));

		if(weathers.size() > MaxCacheSize){
			weathers.removeLast();
		}

		return weather;
	}
}
