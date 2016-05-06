package app.wiki.com.wikiapplication.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.wiki.com.wikiapplication.R;
import app.wiki.com.wikiapplication.adapter.SearchRecyclerAdapter;
import app.wiki.com.wikiapplication.model.ApiResponse;
import app.wiki.com.wikiapplication.model.SearchItemModel;
import app.wiki.com.wikiapplication.model.SearchResultResponse;
import app.wiki.com.wikiapplication.network.GetServerResponseTask;
import app.wiki.com.wikiapplication.parser.SearchResultParser;
import app.wiki.com.wikiapplication.utility.AppConstants;
import app.wiki.com.wikiapplication.utility.NetworkUtil;
import app.wiki.com.wikiapplication.utility.ServerDataListener;
/**
 * <h1>SearchActivity</h1>
 *
 * Main entry point of application
 *
 * @author Reshma Salim
 * @version 1.0
 */
public class SearchActivity extends AppCompatActivity implements ServerDataListener
{

    private EditText etSearch;
    private RecyclerView rvSearchResult;
    private SearchRecyclerAdapter searchRecyclerAdapter;
    private List<SearchItemModel> searchItemModelList;
    private ServerDataListener serverDataListener;
    private GetServerResponseTask getServerResponseTask;
    private RelativeLayout rlParent;
    private ProgressBar progressBarSearch;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchItemModelList = new ArrayList<>();
        serverDataListener = this;

        initializeViews();

        etSearch.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(getServerResponseTask != null)
                {
                    if(getServerResponseTask.getStatus() == AsyncTask.Status.PENDING ||
                            getServerResponseTask.getStatus() == AsyncTask.Status.RUNNING ||
                            getServerResponseTask.getStatus() == AsyncTask.Status.FINISHED)
                    {
                        getServerResponseTask.cancel(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(!TextUtils.isEmpty(s.toString()))
                {
                    fetchSearchResult(SearchActivity.this, s.toString(), 200, serverDataListener);
                }
                else
                {
                    searchItemModelList.clear();
                    progressBarSearch.setVisibility(View.GONE);
                    searchRecyclerAdapter.notifyDataChanged(searchItemModelList);
                }
            }
        });
    }

    /**
     * fetch data from server
     */
    public void fetchSearchResult(Context context, String searchQuery, int imageSize, ServerDataListener serverDataListener)
    {

        if(NetworkUtil.isNetworkAvailable(this))
        {
            String url = String.format(AppConstants.BASE_URL +
                                               "w/api.php?action=query&prop=pageimages&format=json&piprop=thumbnail&pithumbsize=%1$d&pilimit=50&generator=prefixsearch&gpssearch=%2$s",
                                       imageSize, searchQuery);

            getServerResponseTask =
                    new GetServerResponseTask(context, url, new SearchResultParser(), serverDataListener, progressBarSearch);
            getServerResponseTask.execute();
        }
        else
        {

            if(!alertDialog.isShowing())
            {
                alertDialog.show();
            }
        }
    }

    /**
     * initialize the views
     */

    private void initializeViews()
    {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etSearch = (EditText) findViewById(R.id.et_search);
        rvSearchResult = (RecyclerView) findViewById(R.id.rv_search_result);
        rlParent = (RelativeLayout) findViewById(R.id.rl_parent);
        progressBarSearch = (ProgressBar) findViewById(R.id.pb_search);

        searchRecyclerAdapter = new SearchRecyclerAdapter(this, searchItemModelList);
        rvSearchResult.setHasFixedSize(true);
        rvSearchResult.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvSearchResult.setAdapter(searchRecyclerAdapter);

        Drawable backgroundImage = ResourcesCompat.getDrawable(getResources(), R.drawable.background, null);
        backgroundImage.setAlpha(30);
        rlParent.setBackground(backgroundImage);

        progressBarSearch.getIndeterminateDrawable()
                         .setColorFilter(ContextCompat.getColor(this, R.color.colorPink), android.graphics.PorterDuff.Mode.SRC_IN);

        alertDialog = NetworkUtil.showSettingDialog(this, getResources().getString(R.string.no_internet_msg),
                                                    getResources().getString(R.string.no_internet_title));
    }

    /**
     * Callback function on receiving the successful response from server.
     */

    @Override
    public void onComplete(ApiResponse apiResponse)
    {
        if(apiResponse != null)
        {
            if(apiResponse instanceof SearchResultResponse)
            {
                if(((SearchResultResponse) apiResponse).getSearchItemModelList().size() == 0)
                {
                    Toast.makeText(this, getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                }
                searchItemModelList.clear();
                searchItemModelList.addAll(((SearchResultResponse) apiResponse).getSearchItemModelList());
                searchRecyclerAdapter.notifyDataChanged(searchItemModelList);
            }
        }
    }

    /**
     * Callback function on receiving the failure response from server.
     */

    @Override
    public void onFailure(String errorMessage)
    {
        progressBarSearch.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
    }
}
