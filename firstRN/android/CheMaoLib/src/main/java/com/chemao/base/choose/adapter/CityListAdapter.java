package com.chemao.base.choose.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chemao.base.R;
import com.chemao.base.choose.bean.CityInfo;

import java.util.ArrayList;

/**
 * @author tanhuohui
 * @date 2015-04-11
 * @description 城市选择列表数据适配器
 **/
public class CityListAdapter extends BaseAdapter {
	private ViewHolder viewHolder;
	private ArrayList<CityInfo.CitysEntity> mlist;
	private LayoutInflater mInflater;
	private Context mcontext;
	private int selectionPosition=-1;
	public CityListAdapter(Context context,ArrayList<CityInfo.CitysEntity> items ) {
		this.mcontext = context;
		this.mlist = items;
		mInflater = LayoutInflater.from(mcontext);
	}

	@Override
	public int getCount() {
		return mlist.size();
	}

	@Override
	public Object getItem(int position) {
		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.find_brand_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.mImageView = (ImageView) convertView
					.findViewById(R.id.brandNameIconFromItem);
			viewHolder.mImageView.setVisibility(View.GONE);
			viewHolder.brandName = (TextView) convertView
					.findViewById(R.id.brandNameTextFromItem);
			viewHolder.alpha = (TextView) convertView
					.findViewById(R.id.alphaText);
			viewHolder.alpha.setVisibility(View.GONE);
			viewHolder.choselineView=convertView.findViewById(R.id.choseCarAgeLine);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.alpha.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		CityInfo.CitysEntity cityBean = mlist.get(position);
		if (cityBean != null) {
			viewHolder.brandName.setText(""+cityBean.getName());
		}
		if(selectionPosition==position){
			viewHolder.brandName.setTextColor(mcontext.getResources().getColor(R.color.color_com_r_1));
			viewHolder.choselineView.setVisibility(View.VISIBLE);
		}else{
			viewHolder.brandName.setTextColor(mcontext.getResources().getColor(R.color.color_com_b_2));
			viewHolder.choselineView.setVisibility(View.GONE);
			if(selectionPosition==-1&&position==0){
				viewHolder.choselineView.setVisibility(View.VISIBLE);
				viewHolder.brandName.setTextColor(mcontext.getResources().getColor(R.color.color_com_r_1));
			}else{
				viewHolder.choselineView.setVisibility(View.GONE);
				viewHolder.brandName.setTextColor(mcontext.getResources().getColor(R.color.color_com_b_2));
			}
		}
		return convertView;
	}

	public static class ViewHolder {
		public ImageView mImageView;// logo图
		public TextView brandName,alpha;
		public View choselineView;
	}
	public int getSelectionPosition() {
		return selectionPosition;
	}

	public void setSelectionPosition(int selectionPosition) {
		this.selectionPosition = selectionPosition;
	}
}