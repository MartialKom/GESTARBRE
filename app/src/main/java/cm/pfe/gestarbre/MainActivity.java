package cm.pfe.gestarbre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cm.pfe.gestarbre.model.DbConnection;
import cm.pfe.gestarbre.model.Utilisateur;

public class MainActivity extends AppCompatActivity {

    TextView username;
    TextView pass;
    Button logbtn;
    int r;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Connexion à la base de données*/

        if(DbConnection.connexion==null) {
            String error = DbConnection.connecter();

            /* Message en cas d'erreur*/
            if (!error.equalsIgnoreCase("OK")) {
                Toast.makeText(this, "erreur: " + error, Toast.LENGTH_LONG).show();

            }
        }
       /* Je récupère les valeurs des élements de la page de connexion*/
       username = (TextView) findViewById(R.id.username);
       pass = (TextView) findViewById(R.id.password);
       logbtn = (Button) findViewById(R.id.loginbtn);

       /* Action lorsqu'on clique sur le bouton connexion*/
     logbtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String log = username.getText().toString();
             String mdp = pass.getText().toString();
            // Utilisateur u = new Utilisateur(log,mdp);
             //r = u.connection(DbConnection.connexion);

             if(log.equals(mdp)){
             Intent intent = new Intent(MainActivity.this, Main2Activity.class);
             startActivity(intent);
             } else {
                 android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
                 builder.setTitle("Attention");
                 builder.setMessage("Mauvaise Authentification...");
                 builder.create().show();
             }
         }
     });

        checkLocationPermission();

    }


    private boolean checkLocationPermission() {
        int location = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int location2 = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION);

        List<String> listPermission = new ArrayList<>();

        if(location != PackageManager.PERMISSION_GRANTED){
            listPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if(location2 != PackageManager.PERMISSION_GRANTED){
            listPermission.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if(!listPermission.isEmpty()){
            ActivityCompat.requestPermissions(this,listPermission.toArray(new String[listPermission.size()]),
                    1);

        }

        return true;
    }
}
