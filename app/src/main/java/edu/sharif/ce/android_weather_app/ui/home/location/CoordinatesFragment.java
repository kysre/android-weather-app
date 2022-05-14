package edu.sharif.ce.android_weather_app.ui.home.location;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.sharif.ce.android_weather_app.R;

public class CoordinatesFragment extends Fragment {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText longEditText = view.findViewById(R.id.longitudeEditText);
        EditText latEditText = view.findViewById(R.id.latitudeEditText);
        Button enterButton = view.findViewById(R.id.enterCoordNameButton);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: get weather on clicked
                // TODO: get weather after 5s of inputting
            }
        });
    }
}
