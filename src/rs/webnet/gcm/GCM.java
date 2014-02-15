package rs.webnet.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;

import com.google.android.gcm.GCMRegistrar;


import static rs.webnet.gcm.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static rs.webnet.gcm.CommonUtilities.EXTRA_MESSAGE;


/**
 * Created by kursulla on 2/15/14.
 */
public class GCM {
    private static final String TAG = "GCM";
    private Context context;
    private AsyncTask<Void, Void, Void> registerTask;
    private BroadcastReceiver handleMessageReceiver;

    public GCM(Context con){
        this.context = con;

        GCMRegistrar.checkDevice(context);
        GCMRegistrar.checkManifest(context);

        context.registerReceiver(handleMessageReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));

        CommonUtilities.checkNotNull(Parameters.SERVER_URL, "SERVER_URL");
        CommonUtilities.checkNotNull(Parameters.SENDER_ID, "SENDER_ID");

        final String regId = GCMRegistrar.getRegistrationId(context);
        BLog.d(TAG, "regId = " + regId,con);

        if (regId.equals("")) {
            // Automatically registers application on startup.
            GCMRegistrar.register(context, Parameters.SENDER_ID);
        } else {
            // Device is already registered on GCM, check server.
            if (GCMRegistrar.isRegisteredOnServer(context)) {
                // Skips registration.
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(), hence the use of AsyncTask instead of a raw thread.

                registerTask = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        //Trying to send registerID to server
                        boolean registered = ServerUtilities.register(context, regId);
                        if (!registered) {
                            GCMRegistrar.unregister(context);
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        registerTask = null;
                    }

                };
                registerTask.execute(null, null, null);
            }
        }

        handleMessageReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
                        BLog.d(TAG,"handleMessageReceiver::onReceive->"+newMessage,context);
                    }
                };
    }

    public AsyncTask<Void, Void, Void> getRegisterTask() {
        return registerTask;
    }

    public BroadcastReceiver getHandleMessageReceiver() {
        return handleMessageReceiver;
    }
}
