package com.chemao.base.choose.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chemao.base.R;
import com.chemao.base.choose.bean.CarChexingInfo;

import java.util.ArrayList;

/**
 * @author tanhuohui
 * @date 2015-05-06
 * @description 车辆品牌车型列表数据适配器
 **/
public class ChooseCarChexingAdapter extends BaseAdapter {
	private ViewHolder viewHolder;
	private ArrayList<CarChexingInfo.ChexingsEntity> mlist;
	private LayoutInflater mInflater;
	private Context mcontext;
	private int selectPosition=-1;
	public ChooseCarChexingAdapter(Context context, ArrayList<CarChexingInfo.ChexingsEntity> items) {
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

		CarChexingInfo.ChexingsEntity carChexing = mlist.get(position);
		if (carChexing != null) {
			viewHolder.brandName.setText(""+ carChexing.getName());
			if(selectPosition==position){
				viewHolder.brandName.setTextColor(mcontext.getResources().getColor(R.color.color_com_r_1));
				viewHolder.choselineView.setVisibility(View.VISIBLE);
			}else{
				viewHolder.brandName.setTextColor(mcontext.getResources().getColor(R.color.color_com_b_1));
				viewHolder.choselineView.setVisibility(View.INVISIBLE);
			}
		}
		
		return convertView;
	}

	private static class ViewHolder {
		public ImageView mImageView;// logo图
		public TextView brandName,alpha;
		public View choselineView;
	}

	public int getSelectPosition() {
		return selectPosition;
	}

	public void setSelectPosition(int selectPosition) {
		this.selectPosition = selectPosition;
	}
	
}