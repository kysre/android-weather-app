package edu.sharif.ce.android_weather_app.ui.home;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

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
        // TODO: add cached weather stuff
//        listItems.add(new RecyclerViewAdapter.ListItem(
//                "Sat", "18", "24", MainWeather.Clouds));
//        listItems.add(new RecyclerViewAdapter.ListItem(
//                "Sun", "20", "34", MainWeather.Clear));
//        listItems.add(new RecyclerViewAdapter.ListItem(
//                "Mon", "20", "34", MainWeather.Ash));
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
                if (coordinatesSwitch.isChecked()) {
                    if (!checkLongitudeLatitude()) return;
                } else {
                    if (cityNameEditText.getText().toString().equals("")) {
                        Toast toast = Toast.makeText(getActivity(),
                                "Please enter a valid city name!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                startAsyncTask();
            }
        });
    }

    public boolean checkLongitudeLatitude() {
        String toastStr = "";
        if (longitudeEditText.getText().toString().equals("") ||
                latitudeEditText.getText().toString().equals("")) {
            toastStr = "Please input valid latitude and longitude!";
        } else {
            double longitude = Double.parseDouble(longitudeEditText.getText().toString());
            double latitude = Double.parseDouble(latitudeEditText.getText().toString());
            if (latitude >= 90 || latitude <= -90) {
                toastStr = "Please input latitude between -90 and +90!";
            } else if (longitude >= 180 || longitude <= -180) {
                toastStr = "Please input longitude between -180 and +180!";
            }
        }
        if (!toastStr.equals("")) {
            Toast toast = Toast.makeText(getActivity(), toastStr, Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }

    public void startAsyncTask() {
        GetWeatherDataAsyncTask getWeatherDataAsyncTask = new GetWeatherDataAsyncTask(this);
        getWeatherDataAsyncTask.execute();
    }

    private class GetWeatherDataAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<HomeFragment> homeFragmentWeakReference;
        private String cityName;
        private double longitude, latitude;
        private boolean isCoordinates;

        GetWeatherDataAsyncTask(HomeFragment fragment) {
            homeFragmentWeakReference = new WeakReference<>(fragment);
        }

        @Override
        protected void onPreExecute() {
            HomeFragment fragment = homeFragmentWeakReference.get();
            if (fragment == null || fragment.isRemoving()) return;
            isCoordinates = fragment.cityNameEditText.getText().toString().equals("");
            if (isCoordinates) {
                longitude = Double.parseDouble(fragment.longitudeEditText.getText().toString());
                latitude = Double.parseDouble(fragment.latitudeEditText.getText().toString());
            } else {
                cityName = fragment.cityNameEditText.getText().toString();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (isCoordinates) {
                Weather.start(longitude, latitude);
            } else {
                Location location = Location.findCoordinate(cityName);
                if (location != null) {
                    Weather.start(location.getLongitude(), location.getLatitude());
                }
            }
            return null;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(Void unused) {
            cityNameEditText.setText("");
            latitudeEditText.setText("");
            longitudeEditText.setText("");
            if (Location.isThereCity) {
                ArrayList<Weather> weatherArrayList = Weather.getFullWeek();
                ArrayList<RecyclerViewAdapter.ListItem> newListItems = new ArrayList<>();
                for (Weather weather : weatherArrayList) {
                    newListItems.add(new RecyclerViewAdapter.ListItem(weather));
                }
                listItems.clear();
                listItems.addAll(newListItems);
                adapter.notifyDataSetChanged();
            } else {
                Toast toast = Toast.makeText(getActivity(), "this city not found", Toast.LENGTH_SHORT);
                toast.show();
                Location.isThereCity = true;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClicked(RecyclerViewAdapter.ListItem listItem) {
        NavHostFragment.findNavController(HomeFragment.this).navigate(
                HomeFragmentDirections.actionNavigationHomeToWeatherViewFragment(
                        listItem.getFeelsLike(), listItem.getWindSpeed(), listItem.getMoonPhase(),
                        listItem.getMaxTemp() + " / " + listItem.getMinTemp(),
                        listItem.getWeatherCondition(), listItem.getHumidity()
                ));
    }
}