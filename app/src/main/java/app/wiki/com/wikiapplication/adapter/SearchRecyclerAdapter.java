package app.wiki.com.wikiapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import app.wiki.com.wikiapplication.R;
import app.wiki.com.wikiapplication.model.SearchItemModel;

/**
 * <h1>SearchRecyclerAdapter</h1>
 *
 * Recycler view adapter
 *
 * @author Reshma Salim
 * @version 1.0
 */
public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.SearchViewHolder>
{

    private Context mContext;
    private int screenWidth;
    private List<SearchItemModel> searchItemModelList;
    int targetWidth;

    public SearchRecyclerAdapter(Context mContext, List<SearchItemModel> searchItemModelList)
    {
        this.mContext = mContext;
        this.searchItemModelList = searchItemModelList;
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        targetWidth = screenWidth / 2;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SearchViewHolder holder, int position)
    {

        if(searchItemModelList.get(position).getThumbnail() != null)
        {

            animate(holder);
            Transformation transformation = new Transformation()
            {

                @Override
                public Bitmap transform(Bitmap source)
                {
                    Bitmap result = resizeImage(source);
                    if(result != source)
                    {
                        // Same bitmap is returned if sizes are the same
                        source.recycle();
                    }
                    return result;
                }

                @Override
                public String key()
                {
                    return "transformation" + " desiredWidth";
                }
            };

            Picasso.with(mContext)
                   .load(searchItemModelList.get(position).getThumbnail().getSource())
                   .error(R.drawable.no_image_available)
                   .placeholder(R.drawable.progress_animation)
                   .transform(transformation)
                   .into(holder.ivSearchItem);
        }
        else
        {
            Bitmap bitmap = resizeImage(
                    ((BitmapDrawable) ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.no_image_available,
                                                                  null)).getBitmap());
            holder.getIvSearchItem().setImageDrawable(new BitmapDrawable(mContext.getResources(), bitmap));
        }
    }

    /**
     * resizing the image
     */
    private Bitmap resizeImage(Bitmap source)
    {
        double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
        int targetHeight = (int) (targetWidth * aspectRatio);
        return Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
    }

    /**
     * fade animation for items
     */
    public void animate(SearchViewHolder viewHolder)
    {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }

    public void notifyDataChanged(List<SearchItemModel> searchItemModelList)
    {
        this.searchItemModelList = searchItemModelList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        return searchItemModelList.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView ivSearchItem;

        public SearchViewHolder(View itemView)
        {
            super(itemView);
            ivSearchItem = (ImageView) itemView.findViewById(R.id.iv_search_item);
        }

        public ImageView getIvSearchItem()
        {
            return ivSearchItem;
        }
    }
}
