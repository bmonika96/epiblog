package si.uni_lj.fe.modulg.epiblog;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.content.Intent;

import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;



import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Node;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {


//pepe
    Shramba shramba;
    TextView ime;
    TextView naslov;
    TextView datum;
    TextView st_napadov;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = this.findViewById(R.id.app_bar_menu);
        Menu menu = navigation.getMenu();
        menu.findItem(R.id.navigation_profil).setChecked(true);
        shramba = new Shramba(this);
        ime = findViewById(R.id.main_ime);
        naslov = findViewById(R.id.main_naslov);
        datum=findViewById(R.id.profil_datum);
        st_napadov=findViewById(R.id.profil_st_napadov);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Node uporabnik = shramba.pridobiUporabnika();
        if (uporabnik == null ) {
            Intent registracija = new Intent(this, Registracija.class);
            startActivity(registracija);
        }
        else {
            ime.setText(shramba.pridobiUporabnikaIme() + " " + shramba.pridobiUporabnikaPriimek());
            naslov.setText(shramba.pridobiUporabnikaNaslov());
        }
        st_napadov.setText("Število zabeleženih napadov: "+String.valueOf(shramba.pridobiVelikostZgodovine()));
        datum.setText(danesDatum());
        requestPermisions();

    }

    public void odpri_zgodovino(View v) {
        Intent intent = new Intent(this, Zgodovina.class);
        startActivity(intent);
    }
    public void odpri_nov_vnos(View v) {
        Intent intent = new Intent(this, NovVnos.class);
        startActivity(intent);
    }
    public void odpri_nastavitve(View v) {
        Intent intent = new Intent(this, Nastavitve.class);
        startActivityForResult(intent, 1);

    }
    private String danesDatum() {
        Calendar cal = Calendar.getInstance();
        int leto = cal.get(Calendar.YEAR);
        int mesec = cal.get(Calendar.MONTH);
        mesec = mesec + 1; // gre samo od 0 do 11
        int dan = cal.get(Calendar.DAY_OF_MONTH);
        return dan+"."+mesec+"."+leto;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    public void requestPermisions() {
        int permissions_code = 42;
        String[] permissions = {Manifest.permission.SEND_SMS};

        if(!hasPermissions(this, permissions)){
            ActivityCompat.requestPermissions(this, permissions, permissions_code);
        }
    }
    public void clickedZgodovina(MenuItem item){
        odpri_zgodovino(null);
    }
    public void clickedProfil(MenuItem item){

    }
    public void clickedNastavitve(MenuItem item){
        odpri_nastavitve(null);
    }
}