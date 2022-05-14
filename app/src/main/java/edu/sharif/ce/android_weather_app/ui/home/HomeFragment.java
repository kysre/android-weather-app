package edu.sharif.ce.android_weather_app.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import edu.sharif.ce.android_weather_app.R;
import edu.sharif.ce.android_weather_app.databinding.FragmentHomeBinding;
import edu.sharif.ce.android_weather_app.ui.home.location.CityFragment;
import edu.sharif.ce.android_weather_app.ui.home.location.CoordinatesFragment;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

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
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.locationFragmentPlaceHolder, new CityFragment());
        fragmentTransaction.commit();

        SwitchCompat coordinatesSwitch = view.findViewById(R.id.coordinatesSwitch);
        coordinatesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (isChecked) {
                    fragmentTransaction.replace(R.id.locationFragmentPlaceHolder,
                            new CoordinatesFragment());
                } else {
                    fragmentTransaction.replace(R.id.locationFragmentPlaceHolder,
                            new CityFragment());
                }
                fragmentTransaction.commit();
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}