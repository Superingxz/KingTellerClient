package com.kingteller.client.utils;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.kingteller.client.bean.map.AddressBean;

@SuppressLint("UseValueOf")
public class GeocoderUtils {
	//福建省漳州市漳浦县绥安镇大亭路中段闽洋大厦
	
	private static  PoiSearch.Query query;
	private static PoiSearch poiSearch;
	private static PoiResult poiResult; // poi返回的结果
	private static AddressBean addressBean = null;
	
	public static AddressBean getPoiSearch(String address,Context context){
		
		query = new PoiSearch.Query(address, "", null);
		query.setPageSize(1);// 设置每页最多返回多少条poiitem
		query.setPageNum(0);
		
		poiSearch = new PoiSearch(context, query);
		
		poiSearch.setOnPoiSearchListener(new OnPoiSearchListener() {

			/**
			 * POI信息查询回调方法
			 */
			@Override
			public void onPoiSearched(PoiResult result, int rCode) {
				// TODO Auto-generated method stub

				if (rCode == 0) {
					if (result != null && result.getQuery() != null) {// 搜索poi的结果
						if (result.getQuery().equals(query)) {// 是否是同一条
							poiResult = result;
							// 取得搜索到的poiitems有多少页
							List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
							List<SuggestionCity> suggestionCities = poiResult
									.getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

							if (poiItems != null && poiItems.size() > 0) {
								for (int i = 0; i < poiItems.size(); i++) {
									addressBean = new AddressBean();
									addressBean.setName(poiItems.get(i).getTitle());
									addressBean.setAddress(poiItems.get(i).getSnippet());
									addressBean.setLat(poiItems.get(i)
											.getLatLonPoint().getLatitude());
									addressBean.setLng(poiItems.get(i)
											.getLatLonPoint().getLongitude());
								}

							} 
						}
					} else {
					}
				}  else {
				}

			}

			/**
			 * POI详情查询回调方法
			 */
			@Override
			public void onPoiItemDetailSearched(PoiItemDetail arg0, int rCode) {
				// TODO Auto-generated method stub

			}
		});
		poiSearch.searchPOIAsyn();
		return addressBean;
	}
}