package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.activity.pxkhsj.DoPxkhSjActivity;
import com.kingteller.client.bean.pxkh.SjPaperBean;
import com.kingteller.client.view.KTAlertDialog;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;

public class AllSjpaperListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<SjPaperBean> sjPaperlist = new ArrayList<SjPaperBean>();
	private Context context;
	private SjPaperBean sjPaperbean;

	public AllSjpaperListAdapter(Context context, List<SjPaperBean> sjPaperlist) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.sjPaperlist = sjPaperlist;
	}

	public void setLists(List<SjPaperBean> sjPaperlist) {
		this.sjPaperlist = sjPaperlist;
		notifyDataSetChanged();
	}

	public void addLists(List<SjPaperBean> sjPaperlist) {
		this.sjPaperlist.addAll(sjPaperlist);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (sjPaperlist == null) {
			return 0;
		}
		return sjPaperlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		return sjPaperlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int postion, View v, ViewGroup parent) {
		
		sjPaperbean = sjPaperlist.get(postion);
		
		ViewHoler viewHoler = null;

		if (v == null) {
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_mypxkhsjlist, null);
			viewHoler.sjpaper_code_value = (TextView) v.findViewById(R.id.sjpaper_code_value);
			viewHoler.sjpaper_content_value = (TextView) v.findViewById(R.id.sjpaper_content_value);
			viewHoler.sjpaper_timeLong_value = (TextView) v.findViewById(R.id.sjpaper_timeLong_value);
			viewHoler.sjpaper_answerDate_value = (TextView) v.findViewById(R.id.sjpaper_answerDate_value);
			v.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) v.getTag();
		}

		viewHoler.sjpaper_code_value.setText(sjPaperbean.getPaperCode());
		viewHoler.sjpaper_content_value.setText(sjPaperbean.getContent());
		viewHoler.sjpaper_timeLong_value.setText(sjPaperbean.getTimeLong() + "分钟");
		viewHoler.sjpaper_answerDate_value.setText(sjPaperbean.getAnswerDateLike());
		
		v.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final NormalDialog dialog = new NormalDialog(context);
				KingTellerPromptDialogUtils.showTwoPromptDialog(dialog, 
						"1.考核期间不能做有关考核之外的任何操作(包括返回操作, home键操作), 直到试卷提交为止;" + "\n"
						+ "2.考核将会倒计时, 必须在规定时间内做完，否则系统会自动提交;" + "\n"
						+ "3.试卷在规定时间内, 可自由提交;" + "\n\n"
						+ "以上特别注意, 否则会影响你的考核成绩!" + "\n\n"
						+ "您是否进入考试？",
						new OnBtnClickL() {
							@Override
							public void onBtnClick() {
								dialog.dismiss(); 
							}
	                    },new OnBtnClickL() {
		                    @Override
		                    public void onBtnClick() {
		                    	dialog.dismiss();
		                    	
		                    	Intent intent = new Intent();
								intent.putExtra("paperId", sjPaperbean.getPaperId());
								intent.putExtra("timeLong", sjPaperbean.getTimeLong());
								intent.setClass(context, DoPxkhSjActivity.class);
								context.startActivity(intent);
		                    }
	                });
			}
		});
		return v;
	}

	private static class ViewHoler {
		public TextView sjpaper_code_value;
		public TextView sjpaper_content_value;
		public TextView sjpaper_timeLong_value;
		public TextView sjpaper_answerDate_value;
	}
}
