package demo.ixigo.event;

import demo.ixigo.model.Flight;

/**
 * Created by vipinsahu on 16/7/15.
 */
public class MainPageEvent {
    public final Flight flight;

    public MainPageEvent(Flight flight) {
        this.flight = flight;
    }
}
