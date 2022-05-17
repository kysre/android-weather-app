package edu.sharif.ce.android_weather_app.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import edu.sharif.ce.android_weather_app.Model.Location;
import edu.sharif.ce.android_weather_app.Model.MainWeather;
import edu.sharif.ce.android_weather_app.Model.Weather;
import edu.sharif.ce.android_weather_app.R;
import edu.sharif.ce.android_weather_app.RecyclerViewAdapter;
import edu.sharif.ce.android_weather_app.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements RecyclerViewAdapter.SelectListener {

    private FragmentHomeBinding binding;
    private SwitchCompat coordinatesSwitch;
    private TableRow cityTableRow;
    private TableRow coordinatesTableRow;
    private EditText cityNameEditText;
    private EditText longitudeEditText;
    private EditText latitudeEditText;
    private Button enterButton;
    private RecyclerView weatherRecyclerView;
    private RecyclerViewAdapter adapter;
    private ArrayList<RecyclerViewAdapter.ListItem> listItems;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cityTableRow = view.findViewById(R.id.cityNameTableRow);
        coordinatesTableRow = view.findViewById(R.id.coordinatesTableRow);
        cityNameEditText = view.findViewById(R.id.cityNameEditText);
        longitudeEditText = view.findViewById(R.id.longitudeEditText);
        latitudeEditText = view.findViewById(R.id.latitudeEditText);
        coordinatesSwitch = view.findViewById(R.id.coordinatesSwitch);
        enterButton = view.findViewById(R.id.enterLocation);
        weatherRecyclerView = view.findViewById(R.id.weatherRecyclerView);

        weatherRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listItems = new ArrayList<>();
        // TODO: add weather stuff
        listItems.add(new RecyclerViewAdapter.ListItem(
                "Sat", "18", "24", MainWeather.Clouds));
        listItems.add(new RecyclerViewAdapter.ListItem(
                "Sun", "20", "34", MainWeather.Clear));
        listItems.add(new RecyclerViewAdapter.ListItem(
                "Mon", "20", "34", MainWeather.Ash));
        adapter = new RecyclerViewAdapter(getActivity(), listItems, this);
        weatherRecyclerView.setAdapter(adapter);


        coordinatesSwitch.setChecked(false);
        cityTableRow.setVisibility(View.VISIBLE);
        coordinatesTableRow.setVisibility(View.GONE);
        coordinatesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cityTableRow.setVisibility(View.GONE);
                    coordinatesTableRow.setVisibility(View.VISIBLE);
                } else {
                    cityTableRow.setVisibility(View.VISIBLE);
                    coordinatesTableRow.setVisibility(View.GONE);
                }
            }
        });

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAsyncTask();
            }
        });
    }

    public void startAsyncTask(){
        new WeatherStuff(this).execute();
    }

    private class WeatherStuff extends AsyncTask<Void,Void,Void> {
        private WeakReference<HomeFragment> homeFragmentWeakReference;
        WeatherStuff(HomeFragment fragment){
            homeFragmentWeakReference = new WeakReference<>(fragment);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            HomeFragment activity = homeFragmentWeakReference.get();
            if(activity.cityNameEditText.getText().toString().equals("")){
                Weather.start(Double.parseDouble(longitudeEditText.getText().toString())
                        ,Double.parseDouble(latitudeEditText.getText().toString()));
            }else{
                Location location=Location.findCoordinate(activity.cityNameEditText.
                        getText().toString());
                Weather.start(location.getLongitude(),location.getLatitude());
            }
            return null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClicked(RecyclerViewAdapter.ListItem listItem) {
        // TODO: goto day weather forecast
        NavHostFragment.findNavController(HomeFragment.this).navigate(
                HomeFragmentDirections.actionNavigationHomeToWeatherViewFragment());
    }
}