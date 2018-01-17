package com.develogical;

import com.develogical.Interfaces.Clock;
import com.develogical.Interfaces.WeatherInterface;
import com.weather.Forecast;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;

public class WeatherCacheTests {
	WeatherInterface mockWeatherInterface;
	Clock mockClock;
	WeatherCache weatherCache;

	@Before
	public void before()
	{
		mockWeatherInterface = mock(WeatherInterface.class);
		mockClock = mock(Clock.class);
		weatherCache = new WeatherCache(mockWeatherInterface, 1, 3600000, mockClock);
	}

	@Test
	public void callingGetWeatherOnTheCacheCallesGetWeatherOnTheAdaptor() {
		Forecast expectedWeather = new Forecast("London", 10);
		when(mockWeatherInterface.getWeather("London")).thenReturn(expectedWeather);

		Forecast actualWeather = weatherCache.getWeather("London");
		assertThat(actualWeather, equalTo(expectedWeather));
		verify(mockWeatherInterface, times(1)).getWeather("London");
	}

	@Test
	public void shouldGoToProviderOnlyOnce() {
		Forecast expectedWeather = new Forecast("London", 10);
		when(mockWeatherInterface.getWeather("London")).thenReturn(expectedWeather);

		Forecast actualWeather = weatherCache.getWeather("London");
		Forecast actualWeather2 = weatherCache.getWeather("London");
		Forecast actualWeather3 = weatherCache.getWeather("London");

		assertThat(actualWeather, equalTo(expectedWeather));
		verify(mockWeatherInterface, atMost(1)).getWeather("London");
	}

	@Test
	public void weCanStoreAndRetrieve2Forecasts() {
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
	public void shouldNotAllowMoreCacheEntriesThanOne() {
		Forecast expectedWeather = new Forecast("London", 10);
		when(mockWeatherInterface.getWeather("London")).thenReturn(expectedWeather);

		Forecast actualWeather = weatherCache.getWeather("London");
		Forecast actualWeather2 = weatherCache.getWeather("London");
		Forecast actualWeather3 = weatherCache.getWeather("Berlin");
		Forecast actualWeather4 = weatherCache.getWeather("Berlin");

		assertThat(actualWeather, equalTo(expectedWeather));
		verify(mockWeatherInterface, times(1)).getWeather("London");
		verify(mockWeatherInterface, times(1)).getWeather("Berlin");
	}

	@Test
	public void recordsGetDeletedFromTheCacheAfter1hour() {
		Forecast expectedWeather = new Forecast("London", 10);
		when(mockWeatherInterface.getWeather("London")).thenReturn(expectedWeather);

		when(mockClock.getTime()).thenReturn(new Long(0));
		Forecast actualWeather = weatherCache.getWeather("London");

		when(mockClock.getTime()).thenReturn(new Long(3600000+1));
		Forecast actualWeather2 = weatherCache.getWeather("London");

		verify(mockWeatherInterface, times(2)).getWeather("London");
	}

}
