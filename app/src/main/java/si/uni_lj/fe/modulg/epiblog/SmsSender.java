package si.uni_lj.fe.modulg.epiblog;

import android.app.Activity;
import android.app.PendingIntent;

import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;


import java.util.ArrayList;

public class SmsSender  {

    static public void sendSms(Activity app,String message,String phonenumber)  {
        try{
            SmsManager smgr = SmsManager.getDefault();
            PendingIntent sentPI;
            smgr.sendTextMessage(phonenumber.trim(),null,message.trim(),null,null);
            Toast.makeText(app, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(app, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
            Log.d("abababa",e.getMessage());
        }
    }



}