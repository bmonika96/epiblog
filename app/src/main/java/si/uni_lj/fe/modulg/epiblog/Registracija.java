package si.uni_lj.fe.modulg.epiblog;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Registracija extends AppCompatActivity {

    private Shramba shramba;
    private EditText ime;
    private EditText priimek;
    private EditText naslov;
    private EditText osebnaStevilka;
    private EditText stevilkaZdravnika;
    private EditText zdravila;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);

        shramba = new Shramba(this);
        ime = (EditText) findViewById(R.id.ime);
        priimek = (EditText) findViewById(R.id.priimek);
        naslov = (EditText) findViewById(R.id.naslov);
        osebnaStevilka = (EditText) findViewById(R.id.osebnaStevilka);
        stevilkaZdravnika = (EditText) findViewById(R.id.stevilkaZdravnika);
        zdravila = (EditText) findViewById(R.id.zdravila);

    }
    public void nov_uporabnik_shrani(View view){
        shramba.ustvariUporabnika(ime.getText().toString(),priimek.getText().toString(),naslov.getText().toString(), osebnaStevilka.getText().toString(), stevilkaZdravnika.getText().toString(), zdravila.getText().toString());
        this.finish();
    }


}