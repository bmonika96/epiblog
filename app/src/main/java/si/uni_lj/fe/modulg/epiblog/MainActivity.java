package si.uni_lj.fe.modulg.epiblog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
        //poglej v datoteko če je vpisan, če ni vpisan začni activity Registracija.
        //Sicer ne naredi ničesar
    }


}