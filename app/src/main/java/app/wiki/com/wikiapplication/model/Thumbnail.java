package app.wiki.com.wikiapplication.model;

/**
 * <h1>Thumbnail</h1>
 *
 *
 * Model class for response
 *
 * @author Reshma Salim
 * @version 1.0
 */
public class Thumbnail
{

    private int height;

    private String source;

    private int width;

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [height = " + height + ", source = " + source + ", width = " + width + "]";
    }
}
