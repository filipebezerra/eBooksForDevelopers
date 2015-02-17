package io.github.filipebezerra.ebooksfordevelopers.adapter;

import android.graphics.*;
import android.graphics.drawable.*;
import android.support.v7.graphics.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.squareup.picasso.*;
import io.github.filipebezerra.ebooksfordevelopers.*;
import io.github.filipebezerra.ebooksfordevelopers.graphics.*;
import io.github.filipebezerra.ebooksfordevelopers.model.*;
import io.github.filipebezerra.ebooksfordevelopers.widget.*;
import java.util.*;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements View.OnClickListener {

	private List<RecyclerModel> mDataSet;

	private int mResLayout;
	
	private PaletteManager mPaletteManager = new PaletteManager();
	
	private OnRecyclerViewItemListener<RecyclerModel> mItemClickListener;

	public RecyclerAdapter(List<RecyclerModel> dataSet, int resLayout) {
		mDataSet = dataSet;
		mResLayout = resLayout;
	}
	
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(mResLayout, parent, false);
		view.setOnClickListener(this);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		final RecyclerModel item = mDataSet.get(position);
        holder.itemView.setTag(item);
        holder.mText.setText(item.getText());
        Picasso.with(holder.mImage.getContext()).load(item.getImage()).into(holder.mImage, new Callback() {
				@Override public void onSuccess() {
					holder.updatePalette(mPaletteManager);
				}

				@Override public void onError() {}
			});
	}

	@Override
	public int getItemCount() {
		return mDataSet.size();
	}
	
	@Override
	public void onClick(View view) {
		if (mItemClickListener != null) {
			RecyclerModel model = (RecyclerModel) view.getTag();
			mItemClickListener.onItemClick(view, model);
		}
	}
	
	public void setOnItemClickListener(OnRecyclerViewItemListener<RecyclerModel> listener) {
		this.mItemClickListener = listener;
	}
	
	public void add(RecyclerModel item, int position) {
        mDataSet.add(position, item);
        notifyItemInserted(position);
    }
	
	public void add(RecyclerModel item) {
        mDataSet.add(item);
		int position = mDataSet.indexOf(item);
        notifyItemInserted(position);
	}
	
	public void addAll(List<RecyclerModel> items) {
		mDataSet.addAll(items);
		int positionStart = mDataSet.indexOf(items.get(0));
		int count = items.size();
        notifyItemRangeInserted(positionStart, count);
	}

    public void remove(RecyclerModel item) {
        int position = mDataSet.indexOf(item);
        mDataSet.remove(position);
        notifyItemRemoved(position);
    }
	
	public void removeAll() {
		int count = mDataSet.size();
		mDataSet.clear();
		notifyItemRangeRemoved(0, count);
	}
	
	private static int setColorAlpha(int color, int alpha) {
        return (alpha << 24) | (color & 0x00ffffff);
    }
	
	public static class ViewHolder extends RecyclerView.ViewHolder {
		public ImageView mImage;
		public TextView mText;
		
		public ViewHolder(View itemView) {
			super(itemView);
			mImage = (ImageView) itemView.findViewById(R.id.image);
			mText = (TextView) itemView.findViewById(R.id.text);
		}
		
		public void updatePalette(PaletteManager paletteManager) {
            String key = ((RecyclerModel)itemView.getTag()).getImage();
            Bitmap bitmap = ((BitmapDrawable)mImage.getDrawable()).getBitmap();
            paletteManager.getPalette(key, bitmap, new PaletteManager.Callback() {
					@Override
					public void onPaletteReady(Palette palette) {
						int bgColor = palette.getDarkVibrantColor(-1);
						mText.setBackgroundColor(setColorAlpha(bgColor, 192));
						mText.setTextColor(palette.getLightMutedColor(-1));
					}
				});
        }
	}
	
}
