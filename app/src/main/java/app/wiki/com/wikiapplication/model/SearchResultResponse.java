package app.wiki.com.wikiapplication.model;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>SearchResultResponse</h1>
 *
 *
 * Model class for response
 *
 * @author Reshma Salim
 * @version 1.0
 */
public class SearchResultResponse extends ApiResponse
{
    List<SearchItemModel> searchItemModelList = new ArrayList<>();

    public List<SearchItemModel> getSearchItemModelList()
    {
        return searchItemModelList;
    }

    public void setSearchItemModelList(List<SearchItemModel> searchItemModelList)
    {
        this.searchItemModelList = searchItemModelList;
    }
}
