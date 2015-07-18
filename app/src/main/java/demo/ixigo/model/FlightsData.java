package demo.ixigo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vipinsahu on 17/7/15.
 */
public class FlightsData implements Parcelable {
    String originCode;
    String destinationCode;
    String takeoffTime;
    String landingTime;
    String price;
    String airlineCode;
    @SerializedName("class")
    String airlineClass;


    public String getOriginCode() {
        return originCode;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public String getTakeoffTime() {
        return takeoffTime;
    }

    public String getLandingTime() {
        return landingTime;
    }

    public String getPrice() {
        return price;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public String getAirlineClass() {
        return airlineClass;
    }

    public static Creator<FlightsData> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.originCode);
        dest.writeString(this.destinationCode);
        dest.writeString(this.takeoffTime);
        dest.writeString(this.landingTime);
        dest.writeString(this.price);
        dest.writeString(this.airlineCode);
        dest.writeString(this.airlineClass);
    }

    public FlightsData() {
    }

    protected FlightsData(Parcel in) {
        this.originCode = in.readString();
        this.destinationCode = in.readString();
        this.takeoffTime = in.readString();
        this.landingTime = in.readString();
        this.price = in.readString();
        this.airlineCode = in.readString();
        this.airlineClass = in.readString();
    }

    public static final Parcelable.Creator<FlightsData> CREATOR = new Parcelable.Creator<FlightsData>() {
        public FlightsData createFromParcel(Parcel source) {
            return new FlightsData(source);
        }

        public FlightsData[] newArray(int size) {
            return new FlightsData[size];
        }
    };
}
