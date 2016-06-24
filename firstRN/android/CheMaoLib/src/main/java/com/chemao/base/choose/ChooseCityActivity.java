package com.chemao.base.choose;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chemao.base.R;
import com.chemao.base.choose.adapter.CityAdapter;
import com.chemao.base.choose.adapter.ProvinceAdapter;
import com.chemao.base.choose.bean.AddressInfo;
import com.chemao.base.choose.bean.CityInfo;
import com.chemao.base.choose.bean.ProvinceInfo;
import com.chemao.base.choose.view.LetterListView;
import com.chemao.base.choose.view.PinnedHeaderListView;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

@SuppressLint("DefaultLocale")
public class ChooseCityActivity extends FragmentActivity implements View.OnClickListener {
    public static String INTENT_KEY_IS_CITY_UNLIMITED = "IS_CITY_UNLIMITED";
    public static String INTENT_KEY_HAS_COUNTRY = "HAS_COUNTRY";
    public static String INTENT_KEY_INTO_CHOSEDCITYINFO = "CHOSEDCITYINFO";
    private static final String TAG = "chooseAddr";// 获取地址信息
    private ChooseCityActivity activityThis;
    private PinnedHeaderListView provinceListView;
    private LetterListView provinceLetterListView;
    private ListView cityListView;
    private LinearLayout backBtn;// 返回
    private TextView selectIndexTxt, titleName;
    private ProvinceInfo mProvinceInfo;
    private OverlayThread overlayThread;
    private CityAdapter cityAdapter;
    private ProvinceAdapter provinceAdapter;
    private ProvinceSectionIndexer mIndexer;
    private HashMap<String, Integer> alphaIndexer;
    private String[] sections = {"·", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private String ALL_CHARACTER = "·ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String[] sectionsHasDot = {"·", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private String ALL_CHARACTERHasDot = "·ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String[] sectionsNoDot = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private String ALL_CHARACTERNoDot = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private Handler myHandler;
    private String selectProvice;
    private Animation inAnimation, outAnimation;
    private AddressInfo addressInfo;
    private boolean isCityUnlimited = true;
    private boolean hasCountry = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityThis = ChooseCityActivity.this;
        setContentView(R.layout.choose_city_view);
        if (getIntent() != null) {
            isCityUnlimited = getIntent().getBooleanExtra(INTENT_KEY_IS_CITY_UNLIMITED, true);
            hasCountry = getIntent().getBooleanExtra(INTENT_KEY_HAS_COUNTRY, true);
            if (hasCountry) {
                ALL_CHARACTER = ALL_CHARACTERHasDot;
                sections = sectionsHasDot;
            } else {
                ALL_CHARACTER = ALL_CHARACTERNoDot;
                sections = sectionsNoDot;
            }
        }
        initView();
        setListener();
        initDate();
    }

    public void initView() {
        titleName = (TextView) findViewById(R.id.mainTitleMidText);
        backBtn = (LinearLayout) findViewById(R.id.titleLeftLayout);
        backBtn.setVisibility(View.VISIBLE);
        titleName.setText(getResources().getString(R.string.choose_address));
        provinceListView = (PinnedHeaderListView) findViewById(R.id.provinceListView);
        provinceListView.setDividerHeight(0);
        provinceLetterListView = (LetterListView) findViewById(R.id.provinceLetterListView);
        provinceLetterListView.setbHasDot(hasCountry);
        cityListView = (ListView) findViewById(R.id.cityListView);
        cityListView.setVisibility(View.GONE);
        selectIndexTxt = (TextView) findViewById(R.id.selectIndexTxt);
        selectIndexTxt.setVisibility(View.INVISIBLE);
        inAnimation = AnimationUtils.loadAnimation(activityThis, R.anim.in_from_right);
        outAnimation = AnimationUtils.loadAnimation(activityThis, R.anim.out_to_right);
    }

    public void setListener() {
        backBtn.setOnClickListener(this);
        provinceListView.setOnItemClickListener(new ProvinceOnItemClickListener());
        cityListView.setOnItemClickListener(new CityOnItemClickListener());
        provinceLetterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());

        provinceListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.d(TAG, "----onScrollStateChanged----");
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    provinceAdapter.setSelectPosition(-1);
                    provinceAdapter.notifyDataSetChanged();
                    setVisiable("cityListView", View.GONE);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (view instanceof PinnedHeaderListView) {
                    ((PinnedHeaderListView) view).configureHeaderView(firstVisibleItem);
                }
            }
        });
    }

    private void initDate() {
        myHandler = new MyHandler();
        overlayThread = new OverlayThread();
        mProvinceInfo = new ProvinceInfo();
        addressInfo = new AddressInfo();
        ArrayList<ProvinceInfo.ProvincesEntity> mProvincesEntityList = new ArrayList<>();
        for (int i = 0; i < AddressData.PROVINCES.length; i++) {
            ProvinceInfo.ProvincesEntity mProvincesEntity = new ProvinceInfo.ProvincesEntity();
            String name = AddressData.PROVINCES[i];
            mProvincesEntity.setName(name);
            mProvincesEntity.setProvincePy(CnToSpell.getFullSpell(name).toUpperCase().substring(0, 1));
            mProvincesEntity.setId(String.valueOf(AddressData.P_ID[i]));
            mProvincesEntity.setAddressFlag(0);
            mProvincesEntityList.add(mProvincesEntity);
        }
        Comparator<Object> comp = new Mycomparator();
        Collections.sort(mProvincesEntityList, comp);
        if (hasCountry) {
            ProvinceInfo.ProvincesEntity countyProvincesEntity = new ProvinceInfo.ProvincesEntity();
            countyProvincesEntity.setName("全国");
            countyProvincesEntity.setId("0");
            countyProvincesEntity.setProvincePy("·");
            countyProvincesEntity.setAddressFlag(1);
            mProvincesEntityList.add(0, countyProvincesEntity);
        }
        mProvinceInfo.setProvinces(mProvincesEntityList);

        int[] counts = new int[sections.length];
        alphaIndexer = new HashMap<>();
        for (int i = 0, len = mProvinceInfo.getProvinces().size(); i < len; i++) { // 计算全部城市
            String firstCharacter = mProvinceInfo.getProvinces().get(i).getProvincePy();
            String currentStr = mProvinceInfo.getProvinces().get(i).getProvincePy().substring(0, 1);
            // 上一个汉语拼音首字母，如果不存在为“ ”
            String previewStr = (i - 1) >= 0 ? mProvinceInfo.getProvinces().get(i - 1).getProvincePy().substring(0, 1) : " ";
            if (!previewStr.equals(currentStr)) {
                String name = currentStr;
                alphaIndexer.put(name, i);
            }
            int index = ALL_CHARACTER.indexOf(firstCharacter);
            counts[index]++;
        }

        mIndexer = new ProvinceSectionIndexer(sections, counts);
        provinceAdapter = new ProvinceAdapter(activityThis, mProvinceInfo.getProvinces(), mIndexer);
        provinceListView.setAdapter(provinceAdapter);
        provinceListView.setOnItemClickListener(new ProvinceOnItemClickListener());
        // 设置固定头部
        provinceListView.setPinnedHeaderView(LayoutInflater.from(activityThis).inflate(R.layout.pinne_head_item, provinceListView, false));
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int i = v.getId();
        if (i == R.id.titleLeftLayout) {// 返回
            finish();

        } else {
        }
    }

    class ProvinceOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ProvinceInfo.ProvincesEntity provincesEntity = (ProvinceInfo.ProvincesEntity) parent.getItemAtPosition(position);
            provinceAdapter.setSelectPosition(position);
            provinceAdapter.notifyDataSetChanged();
            setVisiable("cityListView", View.GONE);
            if (provincesEntity.getAddressFlag() == 0) {
                // 选择市区
                int proId = Integer.parseInt(provincesEntity.getId());
                int count = proId - 1;
                ArrayList<CityInfo.CitysEntity> citysEntityList = new ArrayList<>();
                int addrlength = AddressData.CITIES[count].length;
                if (addrlength > 0 && addrlength == 1) {
                    addressInfo.setProvinceId(provincesEntity.getId());
                    addressInfo.setProvinceName(provincesEntity.getName());
                    choosed(AddressData.CITIES[count][0], String.valueOf(AddressData.C_ID[count][0]));
                } else {
                    for (int i = 0; i < addrlength; i++) {
                        CityInfo.CitysEntity citysEntity = new CityInfo.CitysEntity();
                        citysEntity.setName(AddressData.CITIES[count][i]);
                        citysEntity.setId(String.valueOf(AddressData.C_ID[count][i]));
                        citysEntityList.add(citysEntity);
                    }
                    //添加不限代码
                    if (isCityUnlimited) {
                        CityInfo.CitysEntity citysEntityAll = new CityInfo.CitysEntity();
                        citysEntityAll.setName("不限");
                        selectProvice = provincesEntity.getName();
                        citysEntityAll.setId(provincesEntity.getId());
                        citysEntityList.add(0, citysEntityAll);
                    }
                    //封装返回的对象
                    addressInfo.setProvinceId(provincesEntity.getId());
                    addressInfo.setProvinceName(provincesEntity.getName());
                    cityAdapter = new CityAdapter(activityThis, citysEntityList);
                    setVisiable("cityListView", View.VISIBLE);
                    cityListView.setAdapter(cityAdapter);
                }
            } else if (provincesEntity.getAddressFlag() == 1) {
                // 选择全国
                choosed(provincesEntity.getName(), provincesEntity.getId());
            }
        }
    }

    class MyHandler extends Handler {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
        }
    }


    class CityOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CityInfo.CitysEntity citysEntity = (CityInfo.CitysEntity) parent.getItemAtPosition(position);
            cityAdapter.setSelectionPosition(position);
            cityAdapter.notifyDataSetChanged();
            if (citysEntity != null) {
                String name = citysEntity.getName();
                if (name != null && name.equals("不限")) {
                    choosed(selectProvice, citysEntity.getId());
                    return;
                }
                choosed(citysEntity.getName(), citysEntity.getId());
            }
        }

    }

    private class LetterListViewListener implements LetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            if (alphaIndexer.get(s) != null) {
                int position = alphaIndexer.get(s);
                if (s.equals("热")) {
                    if (provinceListView != null) {
                        provinceListView.setSelection(0);
                    }
                } else {
                    if (provinceListView != null) {
                        provinceListView.setSelection(position);
                    }
                }
                selectIndexTxt.setText(s);
                selectIndexTxt.setVisibility(View.VISIBLE);
                myHandler.removeCallbacks(overlayThread);
                myHandler.postDelayed(overlayThread, 1500);
            } else {
                if (s.equals("热")) {
                    if (provinceListView != null) {
                        provinceListView.setSelection(0);
                    }
                }
            }
        }

    }

    // 中文拼音排序
    public class Mycomparator implements Comparator<Object> {
        public int compare(Object o1, Object o2) {
            ProvinceInfo.ProvincesEntity bean1 = (ProvinceInfo.ProvincesEntity) o1;
            ProvinceInfo.ProvincesEntity bean2 = (ProvinceInfo.ProvincesEntity) o2;
            Comparator<Object> cmp = Collator.getInstance(java.util.Locale.ENGLISH);
            return cmp.compare(bean1.getProvincePy(), bean2.getProvincePy());
        }
    }

    /**
     * 返回逻辑：所有字段默认为 “”，如果选择为直辖市或者全国等没有城市信息的选项，所选信息填充在城市的两个字段，只有当省份和城市两个选项都有的时候才填充在所有字段。
     *
     * @param name
     * @param id
     */
    private void choosed(String name, String id) {
        Intent mIntent = new Intent();
        addressInfo.setCityId(id);
        addressInfo.setCityName(name);
        mIntent.putExtra(INTENT_KEY_INTO_CHOSEDCITYINFO, addressInfo);
        setResult(RESULT_OK, mIntent);
        finish();
    }

    // 设置overlay不可见
    private class OverlayThread implements Runnable {
        public void run() {
            selectIndexTxt.setVisibility(View.GONE);
        }
    }

    private void setVisiable(String layout, int i) {
        switch (layout) {
            case "cityListView":
                if (i == View.VISIBLE) {
                    if (cityListView.getVisibility() != View.VISIBLE) {
                        cityListView.startAnimation(inAnimation);
                        cityListView.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (cityListView.getVisibility() == View.VISIBLE) {
                        cityListView.startAnimation(outAnimation);
                        cityListView.setVisibility(View.GONE);
                    }
                }
                break;
            default:
                Log.d(TAG, "-------------入参设置不正确-----------");
                break;
        }
    }
}