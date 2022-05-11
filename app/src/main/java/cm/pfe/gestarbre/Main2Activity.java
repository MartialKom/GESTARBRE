package cm.pfe.gestarbre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    public EditText longitude;
    public EditText latitude;
    public Button location;
    public Button suivant;

    public LocationManager locationManager; //Gestionnaire d'emplacement. Pour permettre l'acces au service de localisation

    public LocationListener locationListener = new MyLocationListener(); //pour récuperer les elements envoyes par le gestionnaire d'emplacement

    String longi="";
    String lat="";
    String [] coordonees = new String[2];

    private boolean gps_enable = false;
    private boolean network_enable = false;

    final LoadingDialog loadingDialog = new LoadingDialog(Main2Activity.this);



    //*********************************************Debut du onCreate********************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        longitude = (EditText) findViewById(R.id.longitude);
        latitude = (EditText) findViewById(R.id.latitude);
        location = (Button) findViewById(R.id.location);
        suivant  = (Button) findViewById(R.id.suivant);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE); //On initialise le Gestionnaire de localisation

        //****************************Lorsqu'on clique sur SUIVANT****************************

        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(longi.equals("") || lat.equals("")){
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Main2Activity.this);
                    builder.setTitle("Attention");
                    builder.setMessage("Veuillez remplir les champs de coordonées...");
                    builder.create().show();

                } else {

                    Intent intent = new Intent(Main2Activity.this, form_arbre.class);
                    intent.putExtra("coordo", coordonees);
                    startActivity(intent);
                }

            }
        });


        //****************************Lorsqu'on clique sur le bouton "ma loalisation" ****************************

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!checkPermission()){
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Main2Activity.this);
                    builder.setTitle("Attention");
                    builder.setMessage("Veuillez autoriser GESTARBRE a utiliser la localisation dans les paramètres...");
                    builder.create().show();

                } else {loadingDialog.startLoadingDialog(); getMyLocation();}
            }
        });


    }

    //****************************Fin du OnCreate****************************



    class MyLocationListener implements LocationListener {


        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                locationManager.removeUpdates(locationListener);

                lat = "" + location.getLatitude();
                longi = "" + location.getLongitude();

                coordonees[0] = lat;
                coordonees[1] = longi;

                latitude.setText(lat);
                longitude.setText(longi);
                loadingDialog.dismissDialog();

            }
        }


    }


    public void getMyLocation() {


        try {
            gps_enable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        } catch (Exception e) {

        }

        try {
            network_enable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        } catch (Exception e) {

        }

        if (!gps_enable || !network_enable) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Main2Activity.this);
            builder.setTitle("Attention");
            builder.setMessage("Désolé, le service de localisation est désactivé...");

            builder.create().show();
        }

        if (gps_enable) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }

        if(network_enable){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }

    }



    private boolean checkPermission(){
        int location = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        int location2 = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION);

        List<String> listPermission = new ArrayList<>();

        if(location != PackageManager.PERMISSION_GRANTED){
            listPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if(location2 != PackageManager.PERMISSION_GRANTED){
            listPermission.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if(!listPermission.isEmpty()){
            return false;
        }

        return true;
    }
}
