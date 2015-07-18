package demo.ixigo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import demo.ixigo.R;
import demo.ixigo.model.FlightsData;
import demo.ixigo.ui.utils.Utils;

public class FlightLisitingAdapter extends RecyclerView.Adapter<FlightLisitingAdapter.ViewHolder> {

    HashMap<String, String> airlineMap;
    HashMap<String, String> airportMap;
    List<FlightsData> flightsData;


    // Provide a suitable constructor (depends on the kind of dataset)
    public void setFlightLisitingAdapter(HashMap<String, String> airlineMap,
                                 HashMap<String, String> airportMap,
                                 List<FlightsData> flightsData) {
        this.flightsData = flightsData;
        this.airlineMap = airlineMap;
        this.airportMap = airportMap;
        notifyItemRangeChanged(0,getItemCount());
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_flight, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FlightsData mflightData = flightsData.get(position);
        holder.getAirlineNameTextView().setText(airlineMap.get(mflightData.getAirlineCode()));
        holder.getFilghtPriceTextView().setText("Rs " + mflightData.getPrice());
        holder.getAirlineClassTextView().setText(mflightData.getAirlineClass());
        holder.getDepartTimeTextView().setText(Utils.getTimeHM(Long.valueOf(mflightData.getTakeoffTime())));
        holder.getDurationTextView().setText(Utils.getDurationInHrsAndMin(Long.valueOf(mflightData.getLandingTime()) - Long.valueOf(mflightData.getTakeoffTime())));
        holder.getArrivalTimeTextView().setText(Utils.getTimeHM(Long.valueOf(mflightData.getLandingTime())));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return flightsData != null ? flightsData.size() : 0;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView getAirlineNameTextView() {
            return airlineNameTextView;
        }

        public TextView getAirlineClassTextView() {
            return airlineClassTextView;
        }

        public TextView getDepartTimeTextView() {
            return departTimeTextView;
        }

        public TextView getArrivalTimeTextView() {
            return arrivalTimeTextView;
        }

        public TextView getFilghtPriceTextView() {
            return filghtPriceTextView;
        }

        public TextView getDurationTextView() {
            return durationTextView;
        }

        final TextView airlineNameTextView, airlineClassTextView, departTimeTextView, arrivalTimeTextView, filghtPriceTextView, durationTextView;

        public ViewHolder(View view) {
            super(view);
            airlineNameTextView = (TextView) view.findViewById(R.id.tv_airline);
            airlineClassTextView = (TextView) view.findViewById(R.id.tv_flight_class);
            departTimeTextView = (TextView) view.findViewById(R.id.tv_starttime);
            arrivalTimeTextView = (TextView) view.findViewById(R.id.tv_endtime);
            filghtPriceTextView = (TextView) view.findViewById(R.id.tv_price);
            durationTextView = (TextView) view.findViewById(R.id.tv_duration);

        }


    }


}

