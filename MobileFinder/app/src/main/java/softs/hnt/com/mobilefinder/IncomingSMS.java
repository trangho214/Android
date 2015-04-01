package softs.hnt.com.mobilefinder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import utility.SharedPreferencesManager;

/**
 * Created by TrangHo on 11-03-2015.
 */
public class IncomingSMS  extends BroadcastReceiver{
    final SmsManager sms = SmsManager.getDefault();
    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras() ;
        String alarm_code = SharedPreferencesManager.getString(context, SharedPreferencesManager.ALARM_CODE);
        try{
            if(bundle!=null)
            {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i =0; i<pdusObj.length; i++)
                {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String message = currentMessage.getMessageBody().toString();
                    if(message.equals(alarm_code))
                    {
                        Intent alarmPlayerIntent = new Intent(context, AlarmPlayer.class);
                        alarmPlayerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(alarmPlayerIntent);
                    }
                }
            }
        }
        catch (Exception e)
        {
           Log.d("Incoming Exeption", e.getMessage());
        }
    }
}
