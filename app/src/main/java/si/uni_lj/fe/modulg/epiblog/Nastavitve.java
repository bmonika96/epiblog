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

import com.google.android.material.bottomnavigation.BottomNavigationView;


import org.w3c.dom.Node;


import java.util.Timer;
import java.util.TimerTask;

public class Nastavitve extends AppCompatActivity {

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
    private int sheight;
    Boolean menuvisible=true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nastavitve);

        BottomNavigationView navigation = this.findViewById(R.id.app_bar_menu);
        Menu menu = navigation.getMenu();
        menu.findItem(R.id.navigation_nastavitve).setChecked(true);

        Rect rectgle= new Rect();
        Window window= getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
        sheight= rectgle.bottom;

        shramba = new Shramba(this);
        ime = (EditText) findViewById(R.id.ime);
        priimek = (EditText) findViewById(R.id.priimek);
        naslov = (EditText) findViewById(R.id.naslov);
        osebnaStevilka = (EditText) findViewById(R.id.osebnaStevilka);
        stevilkaZdravnika = (EditText) findViewById(R.id.stevilkaZdravnika);
        zdravila = (EditText) findViewById(R.id.zdravila);

        Node uporabnik = shramba.pridobiUporabnika();
        if (uporabnik != null) {
            ime.setText(shramba.pridobiUporabnikaIme());
            priimek.setText(shramba.pridobiUporabnikaPriimek());
            naslov.setText(shramba.pridobiUporabnikaNaslov());
            osebnaStevilka.setText(shramba.pridobiUporabnikaOsebnaStevilka());
            stevilkaZdravnika.setText(shramba.pridobiUporabnikaZdravnikovaStevilka());
            zdravila.setText(shramba.pridobiUporabnikaZdravila());
            Button shrani = (Button) findViewById(R.id.registracija_gumb);
            shrani.setText(R.string.shrani);
        }
        Timer timer = new Timer();
        TimerTask t = new TimerTask() {
            @Override
            public void run() {

                keyopen();
            }
        };
        timer.scheduleAtFixedRate(t,0,100);

    }
    public void pojdi_nazaj(View view) {
        this.finish();
    }
    public void keyopen()
    {
        Rect rectgle= new Rect();
        Window window= getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
        int curheight= rectgle.bottom;

        if (curheight<sheight && menuvisible )
        {
            setvisible(false);
        }
        else if(curheight>sheight && !menuvisible){
            setvisible(true);
        }
        sheight=curheight;
    }
    private void setvisible(Boolean t) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(t) {
                    Nastavitve.this.findViewById(R.id.app_bar_menu).setVisibility(View.VISIBLE);
                    Nastavitve.this.menuvisible=true;
                }
                else{
                    Nastavitve.this.findViewById(R.id.app_bar_menu).setVisibility(View.GONE);
                    Nastavitve.this.menuvisible=false;
                }
            }
        });
    }
    public void uporabnik_shrani(View view){
        shramba.ustvariUporabnika(ime.getText().toString(),priimek.getText().toString(),naslov.getText().toString(), osebnaStevilka.getText().toString(), stevilkaZdravnika.getText().toString(), zdravila.getText().toString());
        this.finish();
    }

    public void clickedZgodovina(MenuItem item){
        Intent i = new Intent(this,Zgodovina.class);
        startActivity(i);
        this.finish();
        Log.d("abc","zgo");
    }
    public void clickedProfil(MenuItem item){
        this.finish();
        Log.d("abc","pro");
    }
    public void clickedNastavitve(MenuItem item){
        Log.d("abc","nov");
    }
}