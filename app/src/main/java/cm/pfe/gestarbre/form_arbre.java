package cm.pfe.gestarbre;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class form_arbre extends AppCompatActivity {

    public EditText nom_arbre;
    public EditText description;
    public EditText nom_scientifique;
    public Button valider;
    public Button retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_arbre);

        nom_arbre = (EditText) findViewById(R.id.nomA);
        description = (EditText) findViewById(R.id.description);
        nom_scientifique = (EditText) findViewById(R.id.nomS);
        valider = (Button) findViewById(R.id.valider);
        retour = (Button) findViewById(R.id.retour);

        String[] coordo =  new String[2];

        coordo = getIntent().getStringArrayExtra("coordo");


        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
