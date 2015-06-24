package slapdevstudio.naikapa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends Activity {

    private GoogleMap googleMap;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            checkLocation(MainActivity.this);

        try{
            initilizeMap(MainActivity.this);
        }catch (Exception e){
            e.printStackTrace();
        }

        TextView youre = (TextView) findViewById(R.id.youre);
        TextView location = (TextView) findViewById(R.id.location);
        TextView submit = (TextView) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "okay , submit your new location", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton

    }

    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap(final Context context) {
        // Do a null check to confirm that we have not already instantiated the map.
        if (googleMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            // Check if we were successful in obtaining the map.
            if (googleMap != null ) {

                googleMap.setMyLocationEnabled(true);

                googleMap.getUiSettings().setCompassEnabled(false);

                googleMap.getUiSettings().setMyLocationButtonEnabled(false);

                googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                    @Override
                    public void onMyLocationChange(Location arg0) {
                        // TODO Auto-generated method stub
                        if(counter < 1) {
                            Toast.makeText(MainActivity.this, "Location changed", Toast.LENGTH_SHORT).show();
                            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                            MarkerOptions marker = null;
                            try {
                                List<android.location.Address> addresses = geocoder.getFromLocation(arg0.getLatitude(), arg0.getLongitude(), 1);
                                String address = addresses.get(0).getAddressLine(0) + " " + addresses.get(0).getAddressLine(1) + " " + addresses.get(0).getAddressLine(2);
                                marker = new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title(address);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            googleMap.addMarker(marker);
                            CameraPosition cameraPosition = new CameraPosition.Builder().target(marker.getPosition()).zoom(20).tilt(90).build();

                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            counter++;
                        }
                    }
                });

            }
        }
    }

    public void checkLocation(final Context context){
        LocationManager lm = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        if(!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage("To use this apps , you should first turn on your location option");
            dialog.setPositiveButton("Open Location Setting", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(myIntent);
                    //get gps
                }
            });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    android.os.Process.killProcess(android.os.Process.myPid());
                    // This above line close correctly
                }
            });
            dialog.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}