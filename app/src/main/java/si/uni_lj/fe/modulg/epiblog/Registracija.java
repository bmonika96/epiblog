package si.uni_lj.fe.modulg.epiblog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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