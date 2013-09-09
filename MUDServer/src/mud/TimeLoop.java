package mud;

import mud.utils.Message;

/*
 * Copyright (c) 2012 Jeremy N. Harton
 * 
 * Released under the MIT License:
 * LICENSE.txt, http://opensource.org/licenses/MIT
 * 
 * NOTE: license provided with code controls, if any
 * changes are made to the one referred to.
 */

/**
 * A runnable time loop to supply game time and changes in date according to it.
 * 
 * @author Jeremy
 *
 */
public class TimeLoop implements Runnable
{
    private MoonPhase moonPhase = MoonPhase.FULL_MOON;

    private int second;
    private int minute;
    private int hour;
    private int day;
    private int month;
    private int year;

    private TimeOfDay timeOfDay = TimeOfDay.MIDNIGHT;

    private boolean isDay = false;
    private String celestialBody = "moon";

    private boolean paused = false;
    private boolean running = true;

    private int ms_per_second = 166;       // 166 ms (1/6 s), runs 6x normal time
    private int ms_per_minute = 10 * 1000; // 10k ms (10 s) , runs 6x normal time
    
    final private MUDServer server;
    final private int[] DAYS;

    /**
     * This uses a 6:1 timescale. That is, 6 minutes of game time is equal to 1 minute
     * of real time. To adjust that simply change the ms_per_second variable to the desired
     * number of milliseconds/second to adjust how much real time (in seconds) is equal to
     * 1 minute of game time.
     * 
     * Modifications: x:1 timescale
     */

    public TimeLoop(final MUDServer server, final int[] DAYS, final int year, final int month, final int day, final int hour, final int min) {
        this.DAYS = DAYS;
        this.server = server;
        this.minute = min;  // the initial minute (start time)
        this.hour = hour;   // the initial hour (start time)
        this.day = day;       // the initial day (start day)
        this.month = month;   // the initial month (start month)
        this.year = year;     // the initial year (start year)
    }

    // message sending with specifics needs a loginCheck(client), but it needs to not cause the game to crash
    @Override
    public void run() {
        while (running) {
            try {
                //Thread.sleep(ms_per_minute);
            	Thread.sleep(ms_per_second);
            }
            catch(InterruptedException ie) {
                ie.printStackTrace();
            }
            if (!paused)    doLoop();
        }
    }
    
    private void doLoop() {
    	incrementSecond();
    	//incrementMinute();
    }
    
    private void incrementSecond() {
    	second += 1;
    	if(second > 59) {
    		second = 0;
    		incrementMinute();
    	}
    	server.checkTimers();
    }
    
    private void incrementMinute() {
        minute += 1;
        if (minute > 59) {
            minute = 0;
            incrementHour();
        }
        server.debug("Time loop: " + hour + ":" + minute);
        server.handleMovement();
    }

    private void incrementHour() {
        hour += 1;
        if (hour > 23) {
            hour = 0;
            incrementDay();
        }
        server.onHourIncrement();

        if (minute != 0) {
            return;
        }

        if (this.hour == 5) {
            setTimeOfDay(TimeOfDay.BEFORE_DAWN, new Message("It is now just before dawn.", 0));
            server.debug("It is now just before dawn.");
        }
        else if (this.hour == 6) {
            setTimeOfDay(TimeOfDay.DAWN, new Message("It is now dawn.", 0));
            server.debug("It is now dawn.");
        }
        else if (this.hour == 7) {
            setTimeOfDay(TimeOfDay.MORNING, new Message("It is now morning.", 0));
            server.debug("It is now morning.");
        }
        else if (this.hour == 12) {
            setTimeOfDay(TimeOfDay.MIDDAY, new Message("It is now midday.", 0));
            server.debug("It is now midday.");
        }
        else if (this.hour == 13) {
            setTimeOfDay(TimeOfDay.AFTERNOON, new Message("It is now afternoon.", 0));
            server.debug("It is now afternoon.");
        }
        else if (this.hour == 18) {
            setTimeOfDay(TimeOfDay.DUSK, new Message("It is now dusk.", 0));
            server.debug("It is now dusk.");
        }
        else if (this.hour == 19) {
            setTimeOfDay(TimeOfDay.NIGHT, new Message("It is now night.", 0));
            server.debug("It is now night.");
        }
        else if (this.hour == 0) {
            setTimeOfDay(TimeOfDay.MIDNIGHT, new Message("It is now midnight.", 0));
            server.debug("It is now midnight.");
        }
    }

    private void incrementDay() {
        day += 1;
        if (day >= DAYS[month]) {
            day = 0;
            incrementMonth();
        }
        
        server.onDayIncrement();
    }

    private void incrementMonth() {
        month += 1;
        if (month >= DAYS.length) {
            month = 0;
            incrementYear();
        }
    }
    
    private void incrementYear() {
    	year += 1;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    private void setTimeOfDay(final TimeOfDay tod, final Message msg) {
        server.addMessage(msg);
        timeOfDay = tod;
        if (TimeOfDay.DAWN.equals(tod)) {
            celestialBody = "sun";
            isDay = true;
        }
        else if (TimeOfDay.NIGHT.equals(tod)) {
            celestialBody = "moon";
            isDay = false;
        }
    }

    public void pauseLoop() {
        paused = true;
    }

    public void unpauseLoop() {
        paused = false;
    }

    public void killLoop() {
        running = false;
    }

    public void setHours(int hour) {
        this.hour = hour;
        this.minute = 0;
    }

    public int getHours() {
        return this.hour;
    }

    public void setMinutes(int minute) {
        this.minute = minute;
    }

    public int getMinutes() {
        return this.minute;
    }
    
    public void setSeconds(int second) {
    	this.second = second;
    }
    public int getSeconds() {
    	return this.second;
    }

    public void setScale(int ms) {
        this.ms_per_minute = ms;
    }

    public int getScale() {
        return ms_per_minute;
    }

    public String getCelestialBody() {
        return celestialBody;
    }

    public boolean isDaytime() {
        return isDay;
    }
    
    public MoonPhase getMoonPhase() {
        return moonPhase;
    }
}