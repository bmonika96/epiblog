package si.uni_lj.fe.modulg.epiblog;

import android.app.Activity;
import android.app.PendingIntent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SmsSender extends Activity{

    PendingIntent sentPI;
    String phonenumber="";
    String txtMessage="123";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtMessage="aaa";
        phonenumber="bbb";
        send();
        finish();
    }

    public void send() {
        try{
            SmsManager smgr = SmsManager.getDefault();
            smgr.sendTextMessage(phonenumber,null,txtMessage,sentPI,null);
            Toast.makeText(this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
        }
    }


}