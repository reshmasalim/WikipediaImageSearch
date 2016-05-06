package app.wiki.com.wikiapplication.network;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import app.wiki.com.wikiapplication.model.ApiResponse;
import app.wiki.com.wikiapplication.parser.Parser;
import app.wiki.com.wikiapplication.utility.LogUtil;
import app.wiki.com.wikiapplication.utility.ServerDataListener;
/**
 * <h1>GetServerResponseTask</h1>
 *
 * AsyncTask class for making the request to the server and parsing the response
 *
 * @author Reshma Salim
 * @version 1.0
 */
public class GetServerResponseTask extends AsyncTask<String, Void, Integer>
{

    private static final String TAG = "GetServerResponseTask";
    private String mUrl;
    private ServerDataListener mServerDataListener;
    private ProgressBar progressBarSearch;
    private Context mContext;
    private ApiResponse apiResponse;
    private Parser mParser;

    public GetServerResponseTask(Context context, String url, Parser parser, ServerDataListener serverDataListener, ProgressBar progressBar)
    {
        mContext = context;
        mUrl = url;
        mServerDataListener = serverDataListener;
        mParser = parser;
        progressBarSearch = progressBar;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        progressBarSearch.setVisibility(View.VISIBLE);
    }

    @Override
    protected Integer doInBackground(String... params)
    {
        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;

        if(isCancelled())
        {
            return null;
        }

        try
        {
            URL url = new URL(mUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while((inputString = bufferedReader.readLine()) != null)
            {
                builder.append(inputString);
            }

            urlConnection.disconnect();
            String response = builder.toString();
            LogUtil.printLog(TAG, response);
            if(mParser != null)
            {
                return parseResponse(response);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if(bufferedReader != null)
            {
                try
                {
                    bufferedReader.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return -1;
    }

    @Override
    protected void onPostExecute(Integer status)
    {
        progressBarSearch.setVisibility(View.GONE);
        if(mServerDataListener != null)
        {
            if(status == 1 && apiResponse != null)
            {
                // success
                mServerDataListener.onComplete(apiResponse);
            }
            else
            {
                // failure
                mServerDataListener.onFailure("");
            }
        }
    }

    /**
     * parsing the response
     */
    private int parseResponse(String response)
    {
        if(response != null)
        {
            try
            {
                apiResponse = mParser.parse(response);
                return 1;
            }
            catch(JSONException e1)
            {
                e1.printStackTrace();
                return -1;
            }
        }

        return -1;
    }
}


