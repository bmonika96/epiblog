package si.uni_lj.fe.modulg.epiblog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class OpisDogodka extends AppCompatActivity {
    public static String PODATKIODOGODKU="PodatkiODogodku";
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
        list = (ArrayList<String>) i.getSerializableExtra(PODATKIODOGODKU);
        cas_napada.setText(list.get(0));
        trajanje_napada.setText(list.get(1));
        intenzivnost_napada.setText(list.get(2));
        moznisprozilci_napada.setText((list.get(3)));
    }

    public void poslji_sms(View view){
        String phone=shramba.pridobiUporabnikaZdravnikovaStevilka();
        String message="<alarm>Epilepsija</alarm>\n" +
                "<ime>"+shramba.pridobiUporabnikaIme()+"</ime>\n" +
                "<priimek>"+shramba.pridobiUporabnikaPriimek()+"</priimek>\n" +
                "<stevilka>12345</stevilka>\n" +
                "<lokacija>"+shramba.pridobiUporabnikaNaslov()+"</lokacija>\n" +
                "<tel>"+shramba.pridobiUporabnikaOsebnaStevilka()+"</tel>";
        sendSms(message,phone);
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
}