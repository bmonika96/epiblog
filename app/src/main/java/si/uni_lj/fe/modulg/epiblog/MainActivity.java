package si.uni_lj.fe.modulg.epiblog;
// jabolko
// jagoda
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import org.w3c.dom.Node;


public class MainActivity extends AppCompatActivity {

    //Navigation bar
    //Slika profila, ime, naslov
    //Današnji datum
    //Nov vnos
    //Preglej zgodovino



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Shramba shramba = new Shramba(this);
       Node uporabnik = shramba.pridobiUporabnika();
        if (uporabnik == null ) {
            Intent registracija = new Intent(this, Registracija.class);
            startActivity(registracija);
        }


        //poglej v datoteko če je vpisan, če ni vpisan začni activity Registracija.
        //Sicer ne naredi ničesar

    }

    public void odpri_zgodovino(View v) {
        Intent intent = new Intent(this, Zgodovina.class);
        startActivity(intent);
    }
    public void odpri_nov_vnos(View v) {
        Intent intent = new Intent(this, NovVnos.class);
        startActivity(intent);
    }


}