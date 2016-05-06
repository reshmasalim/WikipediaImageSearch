package app.wiki.com.wikiapplication.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import app.wiki.com.wikiapplication.R;
/**
 * <h1>NetworkUtil</h1>
 *
 * Util class for network related functions
 *
 * @author Reshma Salim
 * @version 1.0
 */
public class NetworkUtil
{
    public static boolean isNetworkAvailable(Context context)
    {
        try
        {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ||
                    connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTING ||
                    connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTING;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public static AlertDialog showSettingDialog(final Context context, String msg, String title)
    {
        return new AlertDialog.Builder(context).setMessage(msg)
                                               .setTitle(title)
                                               .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener()
                                               {
                                                   public void onClick(DialogInterface dialog, int whichButton)
                                                   {

                                                   }
                                               })
                                               .setPositiveButton(R.string.alert_dialog_setting, new DialogInterface.OnClickListener()
                                               {
                                                   public void onClick(DialogInterface dialog, int whichButton)
                                                   {
                                                       context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                                                   }
                                               })
                                               .create();
    }
}
