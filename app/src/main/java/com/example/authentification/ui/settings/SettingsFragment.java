package com.example.authentification.ui.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.authentification.R;
import com.example.authentification.databinding.FragmentSlideshowBinding;

public class SettingsFragment extends Fragment {
    private TextView noInternet;
    private TextView isInternet;
    private ImageView noWifi;
    private ImageView isWifi;
    Context context;
private  TextView battery;
private BroadcastReceiver batterylevelReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        battery.setText("Your battery level is "+String.valueOf(level) + "%");
    }
};
    private FragmentSlideshowBinding binding;
    @Override
    public void onViewCreated(@NonNull View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initEvent();



    }
    private void initView() {
        noInternet = getView().findViewById(R.id.no_connected);
        isInternet= getView().findViewById(R.id.is_connected);
        noWifi=getView().findViewById(R.id.no_wifi);
        isWifi=getView().findViewById(R.id.is_wifi);
        battery = getView().findViewById(R.id.batteryleveltext);

    }
    private void initEvent() {
        getContext().registerReceiver(this.batterylevelReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        if(!isConnected()){
            noInternet.setVisibility(View.VISIBLE);
            noWifi.setVisibility(View.VISIBLE);
        } else {
            isInternet.setVisibility(View.VISIBLE);
            isWifi.setVisibility(View.VISIBLE);
        }
    }
    public boolean isConnected(){
        ConnectivityManager connectivityManager =(ConnectivityManager)getContext().getApplicationContext().getSystemService(context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}