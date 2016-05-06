package app.wiki.com.wikiapplication.utility;

import android.util.Log;

import app.wiki.com.wikiapplication.BuildConfig;
/**
 * <h1>LogUtil</h1>
 *
 * Class For Logs printing
 *
 * @author Reshma Salim
 * @version 1.0
 */
public class LogUtil
{
    private static final String TAG = "WikiApplication";

    public static void printLog(String tag, String msg)
    {
        if(BuildConfig.DEBUG)
        {
            Log.e(TAG, tag + " " + msg);
        }
    }
}
