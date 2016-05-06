package app.wiki.com.wikiapplication.model;

/**
 * <h1>SearchItemModel</h1>
 *
 *
 * Model class for response
 * @author Reshma Salim
 * @version 1.0
 */
public class SearchItemModel
{
    private String title;
    private Thumbnail thumbnail;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Thumbnail getThumbnail()
    {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail)
    {
        this.thumbnail = thumbnail;
    }
}
