package si.uni_lj.fe.modulg.epiblog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class OpisDogodka extends AppCompatActivity {
    public static String NODEOBJECT="NodeOBject";
    //Naslov
    //Čas napada
    //Trajanje napada
    //intenzivnost
    //možni sprozilci
    //obvesti zdravnika => pošlji smsmsmsmsmsmsmsms
    private Shramba shramba;
    TextView cas_napada;
    TextView trajanje_napada;
    TextView intenzivnost_napada;
    TextView moznisprozilci_napada;
    ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opis_dogodka);
        shramba = new Shramba(this);
        cas_napada=(TextView) findViewById(R.id.opis_dogodka_cas);
        trajanje_napada=(TextView) findViewById(R.id.opis_dogodka_trajanje);
        intenzivnost_napada=(TextView) findViewById(R.id.opis_dogodka_intenzivnost);
        moznisprozilci_napada=(TextView) findViewById(R.id.opis_dogodka_moznisprozilci);
        Intent i = getIntent();
        list = (ArrayList<String>) i.getSerializableExtra(NODEOBJECT);
        cas_napada.setText(list.get(0));
        trajanje_napada.setText(list.get(1));
    }

    public void sendSms(View view){
        //Intent i = new Intent(this,SmsSender.class);
        //i.putExtra(OpisDogodka.NODEOBJECT, list);
        //startActivity(i);
    }
}