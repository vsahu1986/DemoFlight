package demo.ixigo.ui.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TimeZone;

import demo.ixigo.model.FlightsData;

/**
 * Created by vipinsahu on 18/7/15.
 */
public final class Utils {
    private Utils() {
    }

    public static ArrayList<FlightsData> sortByPriceAsc
            (ArrayList<FlightsData> flightsData) {
        Collections.sort(flightsData, new Comparator<FlightsData>() {
            @Override
            public int compare(FlightsData lhs, FlightsData rhs) {
                return (Integer.valueOf(lhs.getPrice()) >= Integer.valueOf(rhs.getPrice()) ? 1 : -1);
            }
        });
        return flightsData;
    }

    public static ArrayList<FlightsData> sortByDurationAsc
            (ArrayList<FlightsData> flightsData) {
        Collections.sort(flightsData, new Comparator<FlightsData>() {
            @Override
            public int compare(FlightsData lhs, FlightsData rhs) {
                return (Long.valueOf(lhs.getLandingTime()) - Long.valueOf(lhs.getTakeoffTime()) >= (Long.valueOf(rhs.getLandingTime()) - Long.valueOf(rhs.getTakeoffTime()))) ? 1 : -1;
            }
        });
        return flightsData;
    }

    public static ArrayList<FlightsData> sortByTimeAsc
            (ArrayList<FlightsData> flightsData) {
        Collections.sort(flightsData, new Comparator<FlightsData>() {
            @Override
            public int compare(FlightsData lhs, FlightsData rhs) {
                return ((Long.valueOf(lhs.getTakeoffTime())/1000*60*60)%24)>=(Long.valueOf(rhs.getTakeoffTime())/1000*60*60)%24?1:-1;
            }
        });
        return flightsData;
    }
    public static ArrayList<FlightsData> sortByPriceDesc
            (ArrayList<FlightsData> flightsData) {
        Collections.sort(flightsData, new Comparator<FlightsData>() {
            @Override
            public int compare(FlightsData lhs, FlightsData rhs) {
                return (Integer.valueOf(lhs.getPrice()) >= Integer.valueOf(rhs.getPrice()) ? -1 : 1);
            }
        });
        return flightsData;
    }

    public static ArrayList<FlightsData> sortByDurationDesc
            (ArrayList<FlightsData> flightsData) {
        Collections.sort(flightsData, new Comparator<FlightsData>() {
            @Override
            public int compare(FlightsData lhs, FlightsData rhs) {
                return (Long.valueOf(lhs.getLandingTime()) - Long.valueOf(lhs.getTakeoffTime()) >= (Long.valueOf(rhs.getLandingTime()) - Long.valueOf(rhs.getTakeoffTime()))) ? -1 : 1;
            }
        });
        return flightsData;
    }

    public static ArrayList<FlightsData> sortByTimeDesc
            (ArrayList<FlightsData> flightsData) {
        Collections.sort(flightsData, new Comparator<FlightsData>() {
            @Override
            public int compare(FlightsData lhs, FlightsData rhs) {
                return ((Long.valueOf(lhs.getTakeoffTime())/1000*60*60)%24)>=(Long.valueOf(rhs.getTakeoffTime())/1000*60*60)%24?-1:1;
            }
        });
        return flightsData;
    }

    /**
     * @param durationTime
     * @return
     */
    public static String getDurationInHrsAndMin(long durationTime) {
        if (durationTime < 0)
            durationTime *= -1;

        String durationStr = "";

        // calculating hours
        long hrs = durationTime / (60 * 60 * 1000);
        if (hrs == 10)
            durationStr = "";
        else if (hrs < 10)
            durationStr += "0" + hrs + "h ";
        else
            durationStr += hrs + "h ";

        // calculating minutes
        long min = (durationTime % (60 * 60 * 1000)) / (60 * 1000);
        if (!(min > 0))
            return durationStr;
        if (min < 10)
            durationStr += "0" + min + "m";
        else
            durationStr += min + "m";

        return durationStr;
    }

    final static SimpleDateFormat df = new SimpleDateFormat("HH:MM");

    public static String getTimeHM(long millis) {

        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        String result = df.format(millis);
        return result;
    }

}
