package com.develogical;

import com.develogical.Interfaces.Clock;

public class SystemClock implements Clock {

	@Override
	public Long getTime() {
		return System.currentTimeMillis();
	}
}
