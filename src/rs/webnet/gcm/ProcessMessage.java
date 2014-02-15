package rs.webnet.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.test.DemoActivity;
import com.test.R;

/**
 * Created by kursulla on 2/15/14.
 */
public class ProcessMessage {

    public static void process(Context context, Bundle dataBundle){


        String title = dataBundle.getString("title");
        String message = dataBundle.getString("message");

        PushMsg msg = new PushMsg();
        msg.title = title;
        msg.message = message;
        msg.icon = R.drawable.ic_launcher;

        generateNotification(context,msg);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context, PushMsg pushMsg) {

        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(pushMsg.icon, pushMsg.message, when);

        Intent notificationIntent = new Intent(context, DemoActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);


        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, pushMsg.title, pushMsg.message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        if(Parameters.UPDATE_PUSH_MESSAGE){
            notificationManager.notify(0, notification);
        }else{
            notificationManager.notify((int) System.currentTimeMillis(), notification);
        }

    }


    private static class PushMsg{
        public String title;
        public String message;
        public int icon;

        private PushMsg() {
        }
    }
}
