package si.uni_lj.fe.modulg.epiblog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;



public class NovVnos extends AppCompatActivity {

    //Naslov
    //datum un čas napada
    //Trajanje
    //Intenzivnost
    //možni sprozilci napada
    //obvesti zdravnika
    //prklkičič,dodaj
    Shramba shramba;
    EditText cas_napada;
    EditText trajanje_napada;
    EditText intenzivnost_napada;
    EditText moznisprozilci_napada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nov_vnos);
        shramba = new Shramba(this);
        cas_napada=(EditText) findViewById(R.id.nov_vnos_vnesi_cas);
        trajanje_napada=(EditText) findViewById(R.id.nov_vnos_vnesi_trajanje);
        intenzivnost_napada=(EditText) findViewById(R.id.nov_vnos_vnesi_intenzivnost);
        moznisprozilci_napada=(EditText) findViewById(R.id.nov_vnos_vnesi_sprozilci);
    }
}