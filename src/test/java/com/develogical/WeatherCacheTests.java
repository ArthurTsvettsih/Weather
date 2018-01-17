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
        WeatherCache weatherCache = new WeatherCache(mockWeatherInterface);
        Forecast expectedWeather = new Forecast("London", 10);
        when(mockWeatherInterface.getWeather()).thenReturn(expectedWeather);

        Forecast actualWeather = weatherCache.getWeather();
        assertThat(actualWeather, equalTo(expectedWeather));
        verify(mockWeatherInterface, times(1)).getWeather();
    }

    @Test
    public void shouldGoToProviderOnlyOnce() {
        WeatherInterface mockWeatherInterface = mock(WeatherInterface.class);
        WeatherCache weatherCache = new WeatherCache(mockWeatherInterface);
        Forecast expectedWeather = new Forecast("London", 10);
        when(mockWeatherInterface.getWeather()).thenReturn(expectedWeather);

        Forecast actualWeather  = weatherCache.getWeather();
        Forecast actualWeather2 = weatherCache.getWeather();
        Forecast actualWeather3 = weatherCache.getWeather();

        assertThat(actualWeather, equalTo(expectedWeather));
        verify(mockWeatherInterface, atMost(1)).getWeather();
    }


}
