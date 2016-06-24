package com.chemao.base.choose.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chemao.base.R;
import com.chemao.base.choose.ProvinceSectionIndexer;
import com.chemao.base.choose.bean.CarBrandInfo;
import com.chemao.base.choose.view.PinnedHeaderListView;

import java.util.ArrayList;

/**
 * @author tanhuohui
 * @date 2015-05-07
 * @description 品牌选择列表数据适配器
 **/
public class ChooseCarBrandAdapter extends BaseAdapter implements PinnedHeaderListView.PinnedHeaderAdapter {
    private ViewHolder viewHolder;
    private ArrayList<CarBrandInfo.BrandsEntity> mlist;
    private LayoutInflater mInflater;
    private Context mcontext;
    private int mLocationPosition = -1;
    private int selectPosition = -1;
    private ProvinceSectionIndexer mIndexer;

    public ChooseCarBrandAdapter(Context context, ArrayList<CarBrandInfo.BrandsEntity> items, ProvinceSectionIndexer indexer) {
        this.mcontext = context;
        this.mlist = items;
        this.mIndexer = indexer;
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
            viewHolder.name = (TextView) convertView
                    .findViewById(R.id.brandNameTextFromItem);
            viewHolder.alpha = (TextView) convertView
                    .findViewById(R.id.alphaText);
            viewHolder.choselineView = convertView.findViewById(R.id.choseCarAgeLine);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CarBrandInfo.BrandsEntity carBrand = mlist.get(position);
        if (carBrand != null) {
            viewHolder.name.setText("" + carBrand.getName());
            if (selectPosition == position) {
                viewHolder.name.setTextColor(mcontext.getResources().getColor(R.color.color_com_r_1));
                viewHolder.choselineView.setVisibility(View.VISIBLE);
            } else {
                viewHolder.name.setTextColor(mcontext.getResources().getColor(R.color.color_com_b_1));
                viewHolder.choselineView.setVisibility(View.INVISIBLE);
            }
            String currentStr = mlist.get(position).getBrand_py().substring(0, 1);
            String previewStr = (position - 1) >= 0 ? mlist.get(position - 1).getBrand_py().substring(0, 1) : " ";
            if (!previewStr.equals(currentStr)) {
                viewHolder.alpha.setVisibility(View.VISIBLE);
                viewHolder.alpha.setText(currentStr);
            } else {
                viewHolder.alpha.setVisibility(View.GONE);
            }
            String stemp = carBrand.getBrand_id();
            if (stemp != null && !stemp.equals("")) {
                int brandId = Integer.parseInt(stemp);
                String imgFileName = null;
                if (brandId < 10) {
                    imgFileName = "brandicon/brand_00" + stemp;
                } else if (brandId > 10 && brandId < 100) {
                    imgFileName = "brandicon/brand_0" + stemp;

                } else if (brandId > 100) {
                    imgFileName = "brandicon/brand_" + stemp;
                }
                if (imgFileName != null) {
                    imgFileName = imgFileName + ".png";
                    Glide.with(mcontext)
                            .load("file:///android_asset/" + imgFileName)
                            .placeholder(R.drawable.friends_sends_pictures_no)
                            .crossFade()
                            .into(viewHolder.mImageView);
                }
            }
        }

        return convertView;
    }

    private static class ViewHolder {
        public ImageView mImageView;// logo图
        public TextView name, alpha;
        public View choselineView;
    }

    @Override
    public int getPinnedHeaderState(int position) {
        int realPosition = position;
        if (realPosition < 0
                || (mLocationPosition != -1 && mLocationPosition == realPosition)) {
            return PINNED_HEADER_GONE;
        }
        mLocationPosition = -1;
        int section = mIndexer.getSectionForPosition(realPosition);
        int nextSectionPosition = mIndexer.getPositionForSection(section + 1);
        if (nextSectionPosition != -1
                && realPosition == nextSectionPosition - 1) {
            return PINNED_HEADER_PUSHED_UP;
        }
        return PINNED_HEADER_VISIBLE;
    }

    @Override
    public void configurePinnedHeader(View header, int position, int alpha) {
        // TODO 自动生成的方法存根
        int realPosition = position;
        int section = mIndexer.getSectionForPosition(realPosition);
        String title = (String) mIndexer.getSections()[section];
        ((TextView) header.findViewById(R.id.pinnealphaText)).setText(title);
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

}