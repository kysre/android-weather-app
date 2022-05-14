package edu.sharif.ce.android_weather_app.ui.home.location;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.sharif.ce.android_weather_app.R;

public class CityFragment extends Fragment {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText cityNameEditText = view.findViewById(R.id.cityNameEditText);
        Button cityNameButton = view.findViewById(R.id.enterCityNameButton);

        cityNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: get coord and weather
                // TODO: get coord and weather after 5s of typing city name
            }
        });
    }
}
