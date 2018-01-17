package com.develogical;

import com.develogical.Interfaces.Clock;
import com.develogical.Interfaces.WeatherInterface;
import com.weather.Forecast;

import java.util.Date;
import java.util.LinkedList;

public class WeatherCache implements WeatherInterface {
	private final WeatherInterface weatherInterface;
	private LinkedList<WeatherCacheItem> weathers;
	private int maxCacheSize;
	private long itemLifeSpan;
	private Clock clock;

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

	public WeatherCache(WeatherInterface weatherInterface, int maxCacheSize, long itemLifespan, Clock _clock) {
		this.weatherInterface = weatherInterface;
		this.maxCacheSize = maxCacheSize;
		this.itemLifeSpan = itemLifespan;
		this.clock = _clock;

		weathers = new LinkedList<>();
	}

	@Override
	public Forecast getWeather(String location) {
		removeExpired();

		for (WeatherCacheItem weather : weathers)
			if (weather.Location.equals(location))
				return (Forecast) weather.Weather;

		Forecast weather = weatherInterface.getWeather(location);

		weathers.addFirst(new WeatherCacheItem(weather, clock.getTime(), location));

		if (weathers.size() > maxCacheSize) {
			weathers.removeLast();
		}

		return weather;
	}

	public void removeExpired() {
		Long currentTime = clock.getTime();

		LinkedList<WeatherCacheItem> listCopy = weathers;
		for (WeatherCacheItem weather : weathers) {
			if (currentTime - weather.TimeRetrieved >= itemLifeSpan) {
				listCopy.remove(weather);
			}
		}

		weathers = listCopy;
	}
}
