package demo.ixigo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by vipinsahu on 17/7/15.
 */
public class Flight implements Parcelable {
    HashMap<String,String> airlineMap;
    HashMap<String,String> airportMap;
    ArrayList<FlightsData> flightsData;

    public HashMap<String, String> getAirlineMap() {
        return airlineMap;
    }

    public HashMap<String, String> getAirportMap() {
        return airportMap;
    }

    public ArrayList<FlightsData> getFlightsData() {
        return flightsData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (airlineMap == null) {
            dest.writeInt(0);
        } else {
            writeToParcel(dest, airlineMap);
        }

        if (airportMap == null) {
            dest.writeInt(0);
        } else {
            writeToParcel(dest, airportMap);
        }

        dest.writeSerializable(this.airlineMap);
        dest.writeSerializable(this.airportMap);
        dest.writeTypedList(flightsData);
    }

    public Flight() {
    }

    protected Flight(Parcel in) {
        this.airlineMap = (HashMap<String, String>) in.readSerializable();
        this.airportMap = (HashMap<String, String>) in.readSerializable();

        airlineMap = new HashMap<String, String>();
        airlineMap = readFromParcel(in, airlineMap);

        airportMap = new HashMap<String, String>();
        airportMap = readFromParcel(in, airportMap);

        in.readTypedList(flightsData, FlightsData.CREATOR);
    }

    public static final Parcelable.Creator<Flight> CREATOR = new Parcelable.Creator<Flight>() {
        public Flight createFromParcel(Parcel source) {
            return new Flight(source);
        }

        public Flight[] newArray(int size) {
            return new Flight[size];
        }
    };
    public HashMap<String, String> readFromParcel(Parcel in, HashMap<String, String> map) {
        int count = in.readInt();
        for (int i = 0; i < count; i++) {
            map.put(in.readString(), in.readString());
        }
        return map;
    }

    public void writeToParcel(Parcel dest, HashMap<String, String> map) {
        Set<String> entries = map.keySet();
        dest.writeInt(entries.size());
        for (String e : entries) {
            dest.writeString(e);
            dest.writeString(map.get(e));

        }
    }
}
