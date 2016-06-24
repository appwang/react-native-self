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
import com.chemao.base.choose.ProvinceSectionIndexer;
import com.chemao.base.choose.bean.ProvinceInfo;
import com.chemao.base.choose.view.PinnedHeaderListView;

import java.util.ArrayList;

/**
 * @author tanhuohui
 * @date 2015-04-11
 * @description 城市选择列表数据适配器
 **/
public class ProvinceAdapter extends BaseAdapter implements PinnedHeaderListView.PinnedHeaderAdapter {
    private ViewHolder viewHolder;
    private ArrayList<ProvinceInfo.ProvincesEntity> mlist;
    private LayoutInflater mInflater;
    private Context mcontext;
    private int mLocationPosition = -1;
    private int selectPosition = -1;
    private ProvinceSectionIndexer mIndexer;

    public ProvinceAdapter(Context context, ArrayList<ProvinceInfo.ProvincesEntity> items, ProvinceSectionIndexer indexer) {
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
            viewHolder.mImageView.setVisibility(View.GONE);
            viewHolder.name = (TextView) convertView
                    .findViewById(R.id.brandNameTextFromItem);
            viewHolder.alpha = (TextView) convertView
                    .findViewById(R.id.alphaText);
            viewHolder.choselineView = convertView.findViewById(R.id.choseCarAgeLine);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.alpha.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        ProvinceInfo.ProvincesEntity mProvincesEntity = mlist.get(position);
        if (mProvincesEntity != null) {
            viewHolder.name.setText("" + mProvincesEntity.getName());
            if(selectPosition==position){
                viewHolder.name.setTextColor(mcontext.getResources().getColor(R.color.color_com_r_1));
                viewHolder.choselineView.setVisibility(View.VISIBLE);
            }else{
                viewHolder.name.setTextColor(mcontext.getResources().getColor(R.color.color_com_b_1));
                viewHolder.choselineView.setVisibility(View.INVISIBLE);
            }
            String currentStr = mlist.get(position).getProvincePy().substring(0, 1);
            String previewStr = (position - 1) >= 0 ? mlist.get(position - 1).getProvincePy().substring(0, 1) : " ";
            if (!previewStr.equals(currentStr)) {
                viewHolder.alpha.setVisibility(View.VISIBLE);
                if (currentStr.equals("热")) {
                    currentStr = "热门城市";
                }
                viewHolder.alpha.setText(currentStr);
            } else {
                viewHolder.alpha.setVisibility(View.GONE);
            }

        }

        return convertView;
    }

    public static class ViewHolder {
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
        if (title.equals("热")) {
            title = "热门城市";
        }
        ((TextView) header.findViewById(R.id.pinnealphaText)).setText(title);
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

}