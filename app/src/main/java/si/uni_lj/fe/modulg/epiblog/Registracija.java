package si.uni_lj.fe.modulg.epiblog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private EditText imePriimek;
    private EditText naslov;
    private EditText datumRojstva;
    private EditText osebniZdravnik;
    private RadioButton daZdravila;
    private EditText zdravila;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);
        shramba = new Shramba(this);
        imePriimek=(EditText) findViewById(R.id.imePriimek);
        naslov=(EditText) findViewById(R.id.naslov);
        datumRojstva=(EditText) findViewById(R.id.datumRojstva);
        osebniZdravnik=(EditText) findViewById(R.id.osebniZdravnik);
        daZdravila= (RadioButton) findViewById(R.id.daZdravila);
        zdravila=(EditText) findViewById(R.id.zdravila);
    }
    public void nov_uporabnik_shrani(View view){

        shramba.ustvariUporabnika(imePriimek.getText().toString(),naslov.getText().toString(),datumRojstva.getText().toString(),osebniZdravnik.getText().toString(),daZdravila.getText().toString(), zdravila.getText().toString());

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}