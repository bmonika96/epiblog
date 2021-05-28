package si.uni_lj.fe.modulg.epiblog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class NovVnos extends AppCompatActivity {

    private Shramba shramba;
    private EditText trajanje_napada;
    private EditText moznisprozilci_napada;
    private Button nov_vnos_datum;
    private Button nov_vnos_ura;
    private DatePickerDialog izberiDatum;
    private TimePickerDialog izberiUro;
    private int sheight;
    Boolean menuvisible=true;
    private Slider slider;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nov_vnos);
        //Pridobimo velikost okna aplikacije, za kasnejše prepoznavanje ali je prikazanaa tipkovnica
        Rect rectgle= new Rect();
        Window window= getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
        sheight= rectgle.bottom;


        //inicializacija datum pickerjov
        initDatePicker();
        initTimePicker();

        shramba = new Shramba(this);
        trajanje_napada=(EditText) findViewById(R.id.nov_vnos_vnesi_trajanje);
        moznisprozilci_napada=(EditText) findViewById(R.id.nov_vnos_vnesi_sprozilci);

        slider = (Slider) findViewById(R.id.nov_vnos_vnesi_intenzivnost);

        // nastavi datum na danes
        nov_vnos_datum = findViewById(R.id.nov_vnos_datum);
        nov_vnos_datum.setText(danesDatum());

        // nastavi uro na trenutno
        nov_vnos_ura = findViewById(R.id.nov_vnos_ura);
        nov_vnos_ura.setText(danesUra());

        //Časovnik z 50 ms periodo preverja ali je tipkovnica prikazana
        Timer timer = new Timer();
        TimerTask t = new TimerTask() {
            @Override
            public void run() {
            if(NovVnos.this!=null) {
                keyopen();
            }
            }
        };
        timer.scheduleAtFixedRate(t,0,50);


    }

    //Preverjanje ali je tipkovnica prikazana (če se spremeni velikost okna aplikacije)
    public void keyopen()
    {
        try{
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
        }

        catch (Exception e){}
    }

    //Skrijemo ali prikažemo navigation bar
    private void setvisible(Boolean t) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(t) {
                    NovVnos.this.findViewById(R.id.app_bar_menu).setVisibility(View.VISIBLE);
                    NovVnos.this.menuvisible=true;
                }
                else{
                    NovVnos.this.findViewById(R.id.app_bar_menu).setVisibility(View.GONE);
                    NovVnos.this.menuvisible=false;
                }
            }
        });
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = datumString(day, month, year);
                nov_vnos_datum.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        izberiDatum = new DatePickerDialog(this, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private void initTimePicker()
    {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int ura, int minuta)
            {
                // namesto 11:05 napiše 11:5, zato so potrebni popravki
                if(minuta == 0 ) {
                    nov_vnos_ura.setText(ura + ":00");
                }
                else if(minuta < 10){
                    nov_vnos_ura.setText(ura + ":0" + minuta);
                }
                else {
                    nov_vnos_ura.setText(ura + ":" + minuta);
                }
            }
        };

        Calendar cal = Calendar.getInstance();
        int ura = cal.get(Calendar.HOUR_OF_DAY);
        int minuta = cal.get(Calendar.MINUTE);


        izberiUro = new TimePickerDialog(this, timeSetListener, ura, minuta,true);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }



    private String danesDatum() {
        Calendar cal = Calendar.getInstance();
        int leto = cal.get(Calendar.YEAR);
        int mesec = cal.get(Calendar.MONTH);
        mesec = mesec + 1; // gre samo od 0 do 11
        int dan = cal.get(Calendar.DAY_OF_MONTH);
        return datumString(dan, mesec, leto);
    }
    private String danesUra() {
        Calendar cal = Calendar.getInstance();
        int ura = cal.get(Calendar.HOUR_OF_DAY);
        int minuta = cal.get(Calendar.MINUTE);
        //if(minuta < 10) {
          //   minuta = '0' + minuta;
        //}
        return (ura + ":" + minuta);

    }


    private String datumString(int dan, int mesec, int leto) {
        return dan  + " " + mesec  + " " + leto;
    }

    //Gumbi na aktivnosti
    public void pojdi_nazaj(View view) {
        this.finish();
    }
    public void izberi_datum(View view){
        izberiDatum.show();
    }
    public void izberi_uro(View view){
        izberiUro.show();
    }


    public void nov_vnos_shrani(View view){
        String thisid=shramba.dodajZgodovino(nov_vnos_datum.getText().toString() + " " + nov_vnos_ura.getText().toString(),trajanje_napada.getText().toString(), String.valueOf(slider.getValue()),moznisprozilci_napada.getText().toString());
        Intent i = new Intent(this,OpisDogodka.class);
        ArrayList<String> list = new ArrayList<String>();
        list.add(nov_vnos_datum.getText().toString() + " " + nov_vnos_ura.getText().toString());
        list.add(trajanje_napada.getText().toString());
        list.add(String.valueOf(slider.getValue()));
        list.add(moznisprozilci_napada.getText().toString());
        list.add(thisid);
        i.putExtra(OpisDogodka.PODATKIODOGODKU, list);
        startActivity(i);
        this.finish();
    }

    //Navigation bar
    public void clickedZgodovina(MenuItem item){
        Intent i = new Intent(this,Zgodovina.class);
        startActivity(i);
        this.finish();
    }
    public void clickedProfil(MenuItem item){
        this.finish();
    }
    public void clickedNastavitve(MenuItem item){
        Intent i = new Intent(this,Nastavitve.class);
        startActivity(i);
        this.finish();
    }
}