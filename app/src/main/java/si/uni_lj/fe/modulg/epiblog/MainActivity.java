package si.uni_lj.fe.modulg.epiblog;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.content.Intent;
import android.os.Bundle;

import org.w3c.dom.Node;


public class MainActivity extends AppCompatActivity {

    //Navigation bar
    //Slika profila, ime, naslov
    //Današnji datum
    //Nov vnos
    //Preglej zgodovino

    Shramba shramba;
    TextView ime;
    TextView naslov;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shramba = new Shramba(this);
        ime = findViewById(R.id.main_ime);
        naslov = findViewById(R.id.main_naslov);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Node uporabnik = shramba.pridobiUporabnika();
        if (uporabnik == null ) {
            Intent registracija = new Intent(this, Registracija.class);
            startActivityForResult(registracija,1);
        }
        else {
            ime.setText(shramba.pridobiUporabnikaIme() + " " + shramba.pridobiUporabnikaPriimek());
            naslov.setText(shramba.pridobiUporabnikaNaslov());
        }


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
        Intent intent = new Intent(this, Registracija.class);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ime.setText(shramba.pridobiUporabnikaIme() + " " + shramba.pridobiUporabnikaPriimek());
        naslov.setText(shramba.pridobiUporabnikaNaslov());
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
        String[] permissions = {Manifest.permission.SEND_SMS, Manifest.permission.BLUETOOTH};

        if(!hasPermissions(this, permissions)){
            ActivityCompat.requestPermissions(this, permissions, permissions_code);
        }
    }

}