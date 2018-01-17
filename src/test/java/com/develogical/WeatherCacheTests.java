package com.develogical;

import com.weather.Forecast;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;

public class WeatherCacheTests {
	@Test
	public void callingGetWeatherOnTheCacheCallesGetWeatherOnTheAdaptor() {
		WeatherInterface mockWeatherInterface = mock(WeatherInterface.class);
		WeatherCache weatherCache = new WeatherCache(mockWeatherInterface, 1);
		Forecast expectedWeather = new Forecast("London", 10);
		when(mockWeatherInterface.getWeather("London")).thenReturn(expectedWeather);

		Forecast actualWeather = weatherCache.getWeather("London");
		assertThat(actualWeather, equalTo(expectedWeather));
		verify(mockWeatherInterface, times(1)).getWeather("London");
	}

	@Test
	public void shouldGoToProviderOnlyOnce() {
		WeatherInterface mockWeatherInterface = mock(WeatherInterface.class);
		WeatherCache weatherCache = new WeatherCache(mockWeatherInterface, 1);
		Forecast expectedWeather = new Forecast("London", 10);
		when(mockWeatherInterface.getWeather("London")).thenReturn(expectedWeather);

		Forecast actualWeather = weatherCache.getWeather("London");
		Forecast actualWeather2 = weatherCache.getWeather("London");
		Forecast actualWeather3 = weatherCache.getWeather("London");

		assertThat(actualWeather, equalTo(expectedWeather));
		verify(mockWeatherInterface, atMost(1)).getWeather("London");
	}

	@Test
	public void weCanStoreAndRetrieve2Forecasts(){
		WeatherInterface mockWeatherInterface = mock(WeatherInterface.class);
		WeatherCache weatherCache = new WeatherCache(mockWeatherInterface, 1);
		Forecast expectedWeather1 = new Forecast("London", 10);
		Forecast expectedWeather2 = new Forecast("Berlin", 27);

		when(mockWeatherInterface.getWeather("London")).thenReturn(expectedWeather1);
		Forecast actualWeather1 = weatherCache.getWeather("London");

		when(mockWeatherInterface.getWeather("Berlin")).thenReturn(expectedWeather2);
		Forecast actualWeather2 = weatherCache.getWeather("Berlin");

		assertThat(actualWeather1, equalTo(expectedWeather1));
		assertThat(actualWeather2, equalTo(expectedWeather2));
	}

	@Test
	public void shouldNotAllowMoreCacheEntriesThanOne()
	{
		WeatherInterface mockWeatherInterface = mock(WeatherInterface.class);
		WeatherCache weatherCache = new WeatherCache(mockWeatherInterface, 1);
		Forecast expectedWeather = new Forecast("London", 10);
		when(mockWeatherInterface.getWeather("London")).thenReturn(expectedWeather);

		Forecast actualWeather = weatherCache.getWeather("London");
		Forecast actualWeather2 = weatherCache.getWeather("London");
		Forecast actualWeather3 = weatherCache.getWeather("Berlin");
		Forecast actualWeather4= weatherCache.getWeather("Berlin");

		assertThat(actualWeather, equalTo(expectedWeather));
		verify(mockWeatherInterface, times(1)).getWeather("London");
		verify(mockWeatherInterface, times(1)).getWeather("Berlin");
	}


}
