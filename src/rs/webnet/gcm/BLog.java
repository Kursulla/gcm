package rs.webnet.gcm;

import android.content.Context;
import android.util.Log;

/**
 * Created by kursulla on 2/15/14.
 */
public class BLog {
    public static void d(String tag, String message,Context context){
        if(CommonUtilities.isDebug(context)){
            Log.d(tag,message);
        }
    }
    public static void i(String tag, String message,Context context){
        if(CommonUtilities.isDebug(context)){
            Log.i(tag,message);
        }
    }
    public static void e(String tag, String message,Context context){
        if(CommonUtilities.isDebug(context)){
            Log.e(tag,message);
        }
    }
}
