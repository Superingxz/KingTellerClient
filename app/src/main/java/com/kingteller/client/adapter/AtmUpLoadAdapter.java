package com.kingteller.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.bean.map.AtmGroupBean;
import com.kingteller.client.view.image.allview.SelectPicGridView;
import com.kingteller.client.view.image.allview.SelectPicGridView.OnSelectImageItemLister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 16-2-26.
 */
public class AtmUpLoadAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context mContext;

    private HashMap<Integer, Boolean> isCheckedMap = new HashMap<Integer, Boolean>();
    private List<AtmGroupBean> list = new ArrayList<>();

    private OnAtmUpLoadPicLister onAtmUpLoadPicLister;

    public interface OnAtmUpLoadPicLister {
        public void OnItemClick(SelectPicGridView aview, String id, int type, int num);
    }

    public void setOnAtmUpLoadPicLister(OnAtmUpLoadPicLister onAtmUpLoadPicLister) {
        this.onAtmUpLoadPicLister = onAtmUpLoadPicLister;
    }

    public AtmUpLoadAdapter(Context context, List<AtmGroupBean> list){
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.list = list;
        configCheckMap(false);
    }

    public void setLists(List<AtmGroupBean> list) {
        this.list = list;
        configCheckMap(false);
        notifyDataSetChanged();
    }

    public void addLists(List<AtmGroupBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 首先,默认情况下,所有项目都是没有选中的.这里进行初始化
     */
    public void configCheckMap(boolean bool) {
        for (int i = 0; i < list.size(); i++) {
            isCheckedMap.put(i, bool);
        }
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public HashMap<Integer, Boolean> getCheckMap() {
        return this.isCheckedMap;
    }

    public void remove(int position) {
        this.list.remove(position);
    }

    @Override
    public View getView(final int position, View v, ViewGroup arg2) {

        final ViewHoler viewHoler;
        if (v == null) {
            viewHoler = new ViewHoler();
            v = inflater.inflate(R.layout.item_group_add_atm, null);

            viewHoler.select_pic = (SelectPicGridView) v.findViewById(R.id.layout_add_pic);
            viewHoler.CheckBox = (CheckBox) v.findViewById(R.id.cb_select);
            viewHoler.content = (TextView) v.findViewById(R.id.jqbhText);

            v.setTag(viewHoler);
        }else{
            viewHoler = (ViewHoler)v.getTag();
        }

        v.findViewById(R.id.btn_delete).setVisibility(View.GONE);
        v.findViewById(R.id.group_add_atm_jqxx).setVisibility(View.GONE);
        viewHoler.CheckBox.setVisibility(View.VISIBLE);
        viewHoler.content.setVisibility(View.VISIBLE);

        viewHoler.CheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheckedMap.put(position, isChecked); //将选择项加载到map里面寄存
            }
        });

        if (isCheckedMap.get(position) == null) {
            isCheckedMap.put(position, false);
        }

        viewHoler.CheckBox.setChecked(isCheckedMap.get(position));
        viewHoler.select_pic.setImagesView(list.get(position).getPiclist());
        viewHoler.content.setText(list.get(position).getStr());

        viewHoler.select_pic.setOnSelectImageItemLister(new OnSelectImageItemLister() {
            @Override
            public void OnItemClick(View view, int type, int num) {
                if (onAtmUpLoadPicLister != null)
                    onAtmUpLoadPicLister.OnItemClick(viewHoler.select_pic, list.get(position).getId() , type, num);
            }
        });
        return v;
    }

    private static class ViewHoler{
        public SelectPicGridView select_pic;
        public TextView content;
        public CheckBox CheckBox;
    }
}
