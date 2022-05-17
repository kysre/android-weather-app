package edu.sharif.ce.android_weather_app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.sharif.ce.android_weather_app.R;

public class WeatherViewFragment extends Fragment {
    private ImageView iconImageView;
    private TextView tempTextView;
    private TextView weatherConditionTextView;
    private TextView feelsLikeTextView;
    private TextView windSpeedTextView;
    private TextView humidityTextView;
    private TextView moonPhaseTextView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iconImageView = view.findViewById(R.id.iconWeatherViewImageView);
        tempTextView = view.findViewById(R.id.weatherViewTempTextView);
        weatherConditionTextView = view.findViewById(R.id.weatherConditionTextView);
        feelsLikeTextView = view.findViewById(R.id.feelsLikeTextView);
        windSpeedTextView = view.findViewById(R.id.windSpeedTextView);
        humidityTextView = view.findViewById(R.id.humidityTextView);
        moonPhaseTextView = view.findViewById(R.id.moonPhaseTextView);

    }
}
