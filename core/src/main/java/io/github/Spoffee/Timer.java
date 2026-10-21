package io.github.Spoffee;

/**
 * A timer which counts down from a given number of miliseconds (will go negetive).
 */
public class Timer {

    private long startOfTimer;
    private long duration;
    private long timeOfPause;
    private boolean isTimerPaused;

    /**
     * Initialises a Timer to count down a given number of miliseconds.
     * @param countDownFrom Number of miliseconds the timer should count down from.
     */
    public Timer(long countDownFrom){
        restartTimer(countDownFrom);
    }

    private long getTimeMilliseconds(){
        if (isTimerPaused){
            return (duration - (timeOfPause - startOfTimer));
        //Limits timer to non-negetive values.
        } else if (duration < (System.currentTimeMillis() - startOfTimer)){
            return(0);
        } else {
            return (duration - (System.currentTimeMillis() - startOfTimer));
        }
    }

    private long getTimeSeconds(){
        return (getTimeMilliseconds() / 1000);
    }

    private long getTimeMinutes(){
        return (getTimeMilliseconds() / 60000);
    }

    /**
     * Gets the number of minutes and seconds remaining.
     * @return Number of minutes and seconds left on the timer, seperated with a colon.
     */
    public String getTime(){
        return String.format("%d:%02d", getTimeMinutes(), getTimeSeconds() % 60);
    } 

    public String toString(){
        return getTime();
    } 

    /**
     * Pauses the timer.
     * @return true if timer has been paused, otherwise false.
     */
    public boolean pause(){
        //Check timer is not already paused.
        if (isTimerPaused){
            return false;
        } else {
            timeOfPause = System.currentTimeMillis();
            isTimerPaused = true;
            return true;
        }
    }

    /**
     * Resumes the timer.
     * @return true if the timer has ressumed, otherwise false.
     */
    public boolean resume(){
        //Check timer is already paused.
        if (isTimerPaused){
            long newDuration = duration - (timeOfPause - startOfTimer);
            restartTimer(newDuration);
            return true;
        } else {
            return false;
        }
        
    }

    /**
     * Restarts the timer.
     * @param countDownFrom Number of miliseconds the timer should count down from.
     */
    public void restartTimer(long countDownFrom){
        this.duration = countDownFrom;
        startOfTimer = System.currentTimeMillis();
        isTimerPaused = false;
    }
}