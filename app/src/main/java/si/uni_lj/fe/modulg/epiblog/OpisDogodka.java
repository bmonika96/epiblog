package si.uni_lj.fe.modulg.epiblog;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class OpisDogodka extends AppCompatActivity {
    public static String PODATKIODOGODKU="PodatkiODogodku";
    private Shramba shramba;
    TextView cas_napada;
    TextView trajanje_napada;
    TextView intenzivnost_napada;
    TextView moznisprozilci_napada;
    TextView stevilka;
    String id="";
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
        stevilka=(TextView) findViewById(R.id.opis_dogodka_stevilka);

        Intent i = getIntent();
        list = (ArrayList<String>) i.getSerializableExtra(PODATKIODOGODKU);
        trajanje_napada.setText(getString(R.string.opis_dogodka_trajanje) + ":" + " \n" +list.get(0));
        cas_napada.setText(getString(R.string.opis_dogodka_datum_ura) + ":" + " \n" +list.get(1));
        intenzivnost_napada.setText(getString(R.string.opis_dogodka_intenzivnost) + ":" + " \n" + list.get(2));
        moznisprozilci_napada.setText((getString(R.string.opis_dogodka_sprozilci) + ":" + " \n" +list.get(3)));
        id=list.get(4);

        String phone=shramba.pridobiUporabnikaZdravnikovaStevilka();
        stevilka.setText(phone);
    }

    public void poslji_sms(View view){
        String phone=shramba.pridobiUporabnikaZdravnikovaStevilka();
        String message=
                shramba.pridobiUporabnikaIme()+"\n" +
                        shramba.pridobiUporabnikaPriimek()+"\n" +
                        list.get(0)+"\n"+
                        list.get(1)+"\n"+
                        list.get(2)+"\n" +
                        list.get(3)+"\n"+
                        shramba.pridobiUporabnikaOsebnaStevilka();
        sendSms(message,phone);
        this.finish();
    }

    public void pojdi_nazaj(View view) {
        this.finish();
    }
    private void sendSms( String message, String phonenumber)  {
        try{
            SmsManager smgr = SmsManager.getDefault();
            smgr.sendTextMessage(phonenumber.trim(),null,message.trim(),null,null);
            Toast.makeText(this, getString(R.string.opisdogodka_uspesnoposlansms), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(this, getString(R.string.opisdogodka_smsfailedstring), Toast.LENGTH_SHORT).show();
        }
    }

    public void izbrisi_zgo(View v){
        shramba.izbrisiZgodovino(id);
        this.finish();
    }
    public void clickedZgodovina(MenuItem item){
        Intent i = new Intent(this,Zgodovina.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        this.finish();

    }
    public void clickedProfil(MenuItem item){
        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        this.finish();

    }
    public void clickedNastavitve(MenuItem item){
        Intent i = new Intent(this,Nastavitve.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        this.finish();
    }
}