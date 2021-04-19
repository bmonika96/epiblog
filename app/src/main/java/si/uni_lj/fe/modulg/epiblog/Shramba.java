package si.uni_lj.fe.modulg.epiblog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Shramba  {
    private Activity self;
    private String filename;

    public Shramba(Activity self){
        self=self;
    }

    public void shrani(View view) {
        EditText vnosno= self.findViewById(R.id.vnos);
        String vsebina= vnosno.getText().toString();
        vpisiVDatoteko(vsebina+"\n");
        vnosno.setText("");

    }
    private void vpisiVDatoteko(String vsebina){

        try {
            //ustvarimo izhodni tok
            FileOutputStream os = self.openFileOutput(filename, Context.MODE_PRIVATE| Context.MODE_APPEND);
            //zapisemo posredovano vsebino v datoteko
            os.write(vsebina.getBytes());
            //sprostimo izhodni tok
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void nalozi(View view) {
        String vsebinaDatoteke=beriIzDatoteke();
        Toast.makeText(self,vsebinaDatoteke,Toast.LENGTH_LONG).show();

    }


    private String beriIzDatoteke(){

        // ustvarimo vhodni podatkovni tok
        FileInputStream inputStream;

        //ugotovimo, koliko je velika datoteka
        File file = new File(self.getFilesDir(), filename);
        int length = (int) file.length();

        //pripravimo spremenljivko, v katero se bodo prebrali podatki
        byte[] bytes = new byte[length];

        //preberemo podatke
        try {
            inputStream = self.openFileInput(filename);
            inputStream.read(bytes);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //podatke pretvorimo iz polja bajtov v znakovni niz
        String vsebina = new String(bytes);

        return vsebina;
    }

    private void vpisiVZunanjoDatoteko(String vsebina){

        //metodo isExternalStorageWritable najedete na http://developer.android.com/training/basics/data-storage/files.html#WriteExternalStorage
        if(isExternalStorageWritable()) {
            File file = new File(self.getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename);
            Log.d("Vpis",file.getAbsolutePath());

            try {
                OutputStream os = new FileOutputStream(file);
                os.write(vsebina.getBytes());
                os.close();
            } catch (IOException e) {
                Log.w("ExternalStorage", "Error writing " + file, e);
            }
        }
    }
}
