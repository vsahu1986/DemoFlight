package demo.ixigo.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;

import de.greenrobot.event.EventBus;
import demo.ixigo.R;
import demo.ixigo.event.MainPageEvent;
import demo.ixigo.model.Flight;
import demo.ixigo.model.FlightsData;
import demo.ixigo.network.NetworkRunnable;
import demo.ixigo.ui.adapter.FlightLisitingAdapter;
import demo.ixigo.ui.adapter.SimpleDividerItemDecoration;
import demo.ixigo.ui.utils.Utils;
import demo.ixigo.ui.widget.CheckedRegularTextView;


public class FlightListingFragment extends Fragment implements View.OnClickListener {
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    RecyclerView recyclerView;
    Flight flight;
    boolean DEPART_ASC_DESC;
    boolean DURAION_ASC_DESC;
    boolean PRICE_ASC_DESC;
    CheckedRegularTextView departTextView,durationTextView,priceTextView;
    FlightLisitingAdapter adapter;

    public FlightListingFragment() {
    }

    public static FlightListingFragment newInstance() {
        FlightListingFragment fragment = new FlightListingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        departTextView =(CheckedRegularTextView)rootView.findViewById(R.id.tv_depart);
        durationTextView =(CheckedRegularTextView)rootView.findViewById(R.id.tv_duration);
        priceTextView =(CheckedRegularTextView)rootView.findViewById(R.id.tv_price);
        departTextView.setOnClickListener(this);
        durationTextView.setOnClickListener(this);
        priceTextView.setOnClickListener(this);
        priceTextView.setChecked(true);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (flight != null)
            outState.putParcelable("flight", flight);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        if (savedInstanceState != null) {
            flight = (Flight) savedInstanceState.getParcelable("flight");
            if (flight != null) {
                updateUI(flight);
            } else {
                Executors.newSingleThreadExecutor().execute(new NetworkRunnable());
            }
        } else {
            Executors.newSingleThreadExecutor().execute(new NetworkRunnable());
        }
        adapter =new FlightLisitingAdapter();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onEventMainThread(MainPageEvent o) {
        if (o.flight != null) {
            this.flight = o.flight;
            updateUI(flight);
        }

    }

    private void updateUI(Flight flight) {
        HashMap<String, String> airlineMap = flight.getAirlineMap();
        HashMap<String, String> airportMap = flight.getAirportMap();
        List<FlightsData> flightsData = flight.getFlightsData();
        adapter.setFlightLisitingAdapter(airlineMap, airportMap, flightsData);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_depart: {
                if(DEPART_ASC_DESC){
                    Utils.sortByTimeAsc(flight.getFlightsData());
                    DEPART_ASC_DESC=false;
                    adapter.setFlightLisitingAdapter(flight.getAirlineMap(), flight.getAirportMap(), flight.getFlightsData());
                }else {
                    DEPART_ASC_DESC=true;
                    Utils.sortByTimeDesc(flight.getFlightsData());
                    adapter.setFlightLisitingAdapter(flight.getAirlineMap(), flight.getAirportMap(), flight.getFlightsData());
                }
                priceTextView.setChecked(false);
                durationTextView.setChecked(false);
                departTextView.setChecked(true);
                break;
            }
            case R.id.tv_duration: {
                if(DURAION_ASC_DESC){
                    Utils.sortByDurationAsc((flight.getFlightsData()));
                    DURAION_ASC_DESC=false;
                    adapter.setFlightLisitingAdapter(flight.getAirlineMap(), flight.getAirportMap(), flight.getFlightsData());
                }else {
                    DURAION_ASC_DESC=true;
                    Utils.sortByDurationDesc(flight.getFlightsData());
                    adapter.setFlightLisitingAdapter(flight.getAirlineMap(), flight.getAirportMap(), flight.getFlightsData());
                }
                priceTextView.setChecked(false);
                durationTextView.setChecked(true);
                departTextView.setChecked(false);
                break;
            }
            case R.id.tv_price: {
                if(PRICE_ASC_DESC){
                    Utils.sortByPriceAsc(flight.getFlightsData());
                    PRICE_ASC_DESC=false;
                    adapter.setFlightLisitingAdapter(flight.getAirlineMap(), flight.getAirportMap(), flight.getFlightsData());
                }else {
                    PRICE_ASC_DESC=true;
                    Utils.sortByPriceDesc(flight.getFlightsData());
                    adapter.setFlightLisitingAdapter(flight.getAirlineMap(), flight.getAirportMap(), flight.getFlightsData());
                }
                priceTextView.setChecked(true);
                durationTextView.setChecked(false);
                departTextView.setChecked(false);
            }

        }
    }
}
