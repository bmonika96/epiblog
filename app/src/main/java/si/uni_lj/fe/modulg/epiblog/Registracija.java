package si.uni_lj.fe.modulg.epiblog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class Registracija extends AppCompatActivity {

    //Naslov
    //ime
    //priimek
    //naslov bivanja
    //stevilka osbnega zdravnika
    //ali jemljete zdravila? ->vpišite zdravil
    private Shramba shramba;
    private EditText ime;
    private EditText priimek;
    private EditText naslov;
    private EditText osebnaStevilka;
    private EditText stevilkaZdravnika;
    private EditText zdravila;


    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_registracija);
        shramba = new Shramba(this);
        ime=(EditText) findViewById(R.id.ime);
        priimek=(EditText) findViewById(R.id.priimek);
        naslov=(EditText) findViewById(R.id.naslov);
        osebnaStevilka=(EditText) findViewById(R.id.osebnaStevilka);
        stevilkaZdravnika=(EditText) findViewById(R.id.stevilkaZdravnika);
        zdravila=(EditText) findViewById(R.id.zdravila);

        Node uporabnik = shramba.pridobiUporabnika();
        if (uporabnik != null ) {
            ime.setText(shramba.pridobiUporabnikaIme());
            priimek.setText(shramba.pridobiUporabnikaPriimek());
            naslov.setText(shramba.pridobiUporabnikaNaslov());
            osebnaStevilka.setText(shramba.pridobiUporabnikaOsebnaStevilka());
            stevilkaZdravnika.setText(shramba.pridobiUporabnikaZdravnikovaStevilka());
            zdravila.setText(shramba.pridobiUporabnikaZdravila());
            Button shrani = (Button) findViewById(R.id.registracija_gumb);
            shrani.setText(R.string.shrani);
        }
    }
    public void nov_uporabnik_shrani(View view){
        shramba.ustvariUporabnika(ime.getText().toString(),priimek.getText().toString(),naslov.getText().toString(), osebnaStevilka.getText().toString(), stevilkaZdravnika.getText().toString(), zdravila.getText().toString());
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        this.finish();
    }
}