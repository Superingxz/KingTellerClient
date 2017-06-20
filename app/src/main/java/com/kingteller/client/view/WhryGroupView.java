package com.kingteller.client.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kingteller.R;
import com.kingteller.client.activity.workorder.AssignWorkerSearchActivity;
import com.kingteller.client.activity.workorder.RapairSendOrderActivity;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.utils.KingTellerJudgeUtils;

public class WhryGroupView extends GroupViewBase {

	private Context context;
	private Button btn_choose;
	private KingTellerEditText editText;

	public WhryGroupView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public WhryGroupView(Context context, boolean isdel) {
		super(context, isdel);
		this.context = context;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		LayoutInflater.from(getContext()).inflate(R.layout.item_add_whry, this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 10, 0, 0);
		setLayoutParams(lp);

		if (isdel)
			findViewById(R.id.btn_delete).setVisibility(View.VISIBLE);
		else
			findViewById(R.id.btn_delete).setVisibility(View.INVISIBLE);

		findViewById(R.id.btn_delete).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((LinearLayout) getParent()).removeView(WhryGroupView.this);
				RapairSendOrderActivity.resetNumId();
			}
		});

		btn_choose = (Button) findViewById(R.id.btn_choose);
		btn_choose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// context.startActivity();
				Intent intent = new Intent(context, AssignWorkerSearchActivity.class);
				KingTellerEditText machine = (KingTellerEditText) ((Activity) context).findViewById(R.id.machine);
				if(KingTellerJudgeUtils.isEmpty(machine.getFieldValue())){
					Toast.makeText(context, "机器必须选择", Toast.LENGTH_SHORT).show();
					return;
				}
				intent.putExtra("ssbscid", machine.getFieldValue());
				((Activity) context).startActivityForResult(intent, KingTellerStaticConfig.SELECT_XZRY);
				((LinearLayout) getParent()).setTag(btn_choose);
			}
		});
		editText = (KingTellerEditText) findViewById(R.id.editText);
		editText.setTextSize(12.0f);
		editText.setFouces(false);
		
	}

	@Override
	public boolean checkData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}

}
