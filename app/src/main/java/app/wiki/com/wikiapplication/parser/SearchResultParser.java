package app.wiki.com.wikiapplication.parser;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import app.wiki.com.wikiapplication.model.SearchItemModel;
import app.wiki.com.wikiapplication.model.SearchResultResponse;
import app.wiki.com.wikiapplication.model.Thumbnail;
/**
 * <h1>SearchResultParser</h1>
 *
 * Class for parsing the response from server.
 *
 * @author Reshma Salim
 * @version 1.0
 */
public class SearchResultParser implements Parser<SearchResultResponse>
{
    private static final String KEY_TITLE = "title";
    private static final String KEY_THUMBNAIL = "thumbnail";
    private static final String KEY_IMAGE_SOURCE = "source";
    private static final String KEY_IMAGE_WIDTH = "width";
    private static final String KEY_IMAGE_HEIGHT = "height";
    private static final String KEY_QUERY = "query";
    private static final String KEY_PAGES = "pages";

    @Override
    public SearchResultResponse parse(String response) throws JSONException
    {
        if(!TextUtils.isEmpty(response))
        {
            SearchResultResponse searchResultResponse = new SearchResultResponse();
            List<SearchItemModel> searchItemModelList = new ArrayList<>();
            searchResultResponse.setSearchItemModelList(searchItemModelList);

            JSONObject jsonObject = new JSONObject(response).getJSONObject(KEY_QUERY).getJSONObject(KEY_PAGES);
            Iterator<String> keys = jsonObject.keys();

            while(keys.hasNext())
            {
                SearchItemModel searchItemModel = new SearchItemModel();
                String key = keys.next();
                JSONObject pageIdJsonObject = jsonObject.getJSONObject(key);
                searchItemModel.setTitle(pageIdJsonObject.getString(KEY_TITLE));
                Thumbnail thumbnail = new Thumbnail();
                if(pageIdJsonObject.has(KEY_THUMBNAIL))
                {
                    JSONObject thumbnailJsonObject = pageIdJsonObject.getJSONObject(KEY_THUMBNAIL);
                    thumbnail.setSource(thumbnailJsonObject.getString(KEY_IMAGE_SOURCE));
                    thumbnail.setHeight(thumbnailJsonObject.getInt(KEY_IMAGE_HEIGHT));
                    thumbnail.setWidth(thumbnailJsonObject.getInt(KEY_IMAGE_WIDTH));
                    searchItemModel.setThumbnail(thumbnail);
                }
                searchItemModelList.add(searchItemModel);
            }
            return searchResultResponse;
        }
        return null;
    }
}
