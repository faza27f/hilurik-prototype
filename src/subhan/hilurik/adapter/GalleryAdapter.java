package subhan.hilurik.adapter;

import java.util.ArrayList;
import java.util.List;

import subhan.hilurik.R;
import subhan.hilurik.model.Gallery;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GalleryAdapter extends BaseAdapter {
	
	Context mContext;
	LayoutInflater layoutInflater;
/*	@SuppressWarnings("unused")
	private final String KEY_IMAGE = "image_value";*/
	private List<Gallery> listGallery = null;
	private ArrayList<Gallery> aListGallery;
	
	public GalleryAdapter(Context context,
			List<Gallery> listGallery) {
		mContext = context;
		this.listGallery = listGallery;
		layoutInflater = LayoutInflater.from(mContext);
		this.aListGallery = new ArrayList<Gallery>();
		this.aListGallery.addAll(listGallery);
	}

	private class ViewHolder {
		ImageView ivBanner;
		TextView tvHeadLine;
	}

	@Override
	public int getCount() {
		return listGallery.size();
	}

	@Override
	public Object getItem(int position) {
		return listGallery.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder viewHolder;
		
		if (view == null) {
			viewHolder = new ViewHolder();
			view = layoutInflater.inflate(R.layout.list_menu_view, null);
			
			viewHolder.ivBanner = (ImageView) view.findViewById(R.id.iv_banner_collection);
			viewHolder.tvHeadLine = (TextView) view.findViewById(R.id.tv_headline_collection);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)view.getTag();
		}
		
		viewHolder.ivBanner.setImageResource(listGallery.get(position).getBanner());
		viewHolder.tvHeadLine.setText(listGallery.get(position).getHeadline());
				
		return view;
	}
}
