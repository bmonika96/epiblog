package si.uni_lj.fe.modulg.epiblog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.Serializable;
import java.util.ArrayList;


public class NovVnos extends AppCompatActivity {

    //Naslov
    //datum un čas napada
    //Trajanje
    //Intenzivnost
    //možni sprozilci napada
    //obvesti zdravnika
    //prklkičič,dodaj
    private Shramba shramba;
    private EditText cas_napada;
    private EditText trajanje_napada;
    private EditText intenzivnost_napada;
    private EditText moznisprozilci_napada;

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

    public void nov_vnos_shrani(View view){
    shramba.dodajZgodovino(cas_napada.getText().toString(),trajanje_napada.getText().toString(),intenzivnost_napada.getText().toString(),moznisprozilci_napada.getText().toString());
        Intent i = new Intent(this,OpisDogodka.class);
        NodeList zgodovina=shramba.pridobiZgodovino();
        Node selectednode=zgodovina.item(3);
        ArrayList<String> list = new ArrayList<String>();
        list.add(Shramba.getValue("Cas",(Element) selectednode));
        list.add(Shramba.getValue("Trajanje",(Element) selectednode));
        i.putExtra(OpisDogodka.NODEOBJECT, list);
        startActivity(i);
    }
}