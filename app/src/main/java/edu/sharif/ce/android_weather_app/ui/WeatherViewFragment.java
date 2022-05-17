package edu.sharif.ce.android_weather_app.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.sharif.ce.android_weather_app.R;
import edu.sharif.ce.android_weather_app.databinding.FragmentWeatherViewBinding;

public class WeatherViewFragment extends Fragment {
    private ImageView iconImageView;
    private TextView tempTextView;
    private TextView weatherConditionTextView;
    private TextView feelsLikeTextView;
    private TextView windSpeedTextView;
    private TextView humidityTextView;
    private TextView moonPhaseTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentWeatherViewBinding binding = FragmentWeatherViewBinding
                .inflate(inflater, container, false);
        return binding.getRoot();
    }

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

        // TODO: set TextViews and ImageView
    }
}
