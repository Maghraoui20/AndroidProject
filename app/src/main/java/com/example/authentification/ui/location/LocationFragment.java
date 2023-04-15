package com.example.authentification.ui.location;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.authentification.R;
import com.example.authentification.databinding.FragmentSlideshowBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices ;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class LocationFragment extends Fragment {

    private  SupportMapFragment supportMapFragment;
   private FusedLocationProviderClient fusedLocationProviderClient;
    private FragmentSlideshowBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();


    }

    private void initView() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googlemap);
        fusedLocationProviderClient = (FusedLocationProviderClient) LocationServices
                .getFusedLocationProviderClient(getContext());
        Dexter.withContext(getActivity().getApplicationContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                       getCurrentLocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();


    }

    public void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location == null){
                    Log.d("null", "onSuccess: ");
                }
                if(supportMapFragment != null ) {
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            if(location != null) {
                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Current Location");
                                googleMap.addMarker(markerOptions);
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                            }
                            else {
                                Toast.makeText(getView().getContext(), "Please On Your Location App Permission", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }



    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_location, container, false);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}