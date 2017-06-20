package com.kingteller.client.activity.pxkhsj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.base.BaseFragmentActivity;
import com.kingteller.client.activity.more.FeedBackActivity;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.pxkh.AnswerParamBean;
import com.kingteller.client.bean.pxkh.JdtParamBean;
import com.kingteller.client.bean.pxkh.KjBackDataBean;
import com.kingteller.client.bean.pxkh.QuestionParamBean;
import com.kingteller.client.bean.pxkh.XzParamBean;
import com.kingteller.client.bean.pxkh.XzTkParamBean;
import com.kingteller.client.bean.workorder.ReturnBackStatus;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.ConditionUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.ChoiceQuestionView;
import com.kingteller.client.view.CompleteQuestionView;
import com.kingteller.client.view.KTAlertDialog;
import com.kingteller.client.view.LineEditText;
import com.kingteller.client.view.ShortAnswerQuestionView;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.groupview.listview.StGroupListView;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

@SuppressLint("HandlerLeak")
public class DoPxkhSjActivity extends FragmentActivity implements OnClickListener {

	private int minute = -1;
	private int second = -1;
	private Timer timer;
	private TimerTask timerTask;
	private TextView countDown;
	private TextView fristQuestion;
	private TextView secondQuestion;
	private TextView thirdQuestion;
	private StGroupListView choiceQuestions;
	private StGroupListView completionQuestions;
	private StGroupListView shortAnswerQuestions;
	private Button btn_submit;
	private String paperId;
	private int timeLong;
	private List<XzTkParamBean> xztkList = new ArrayList<XzTkParamBean>();
	private List<AnswerParamBean> answerList = new ArrayList<AnswerParamBean>();
	private String xztCore="";
	private String tktCore="";
	private String jdtCore="";

	private Context mContext;
	private TextView title_text;
	
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (minute == 0) {
				if (second == 0) {
					countDown.setText("时间到 !");
					submitData(getJsonData());

					if (timer != null) {
						timer.cancel();
						timer = null;
					}
					if (timerTask != null) {
						timerTask = null;
					}
				} else {
					second--;
					if (second >= 10) {
						countDown.setText("0" + minute + "分" + second+"秒");
					} else {
						countDown.setText("0" + minute + "分0" + second+"秒");
					}
				}
			} else {
				if (second == 0) {
					second = 59;
					minute--;
					if (minute >= 10) {
						countDown.setText(minute + "分" + second+"秒");
					} else {
						countDown.setText("0" + minute + "分" + second+"秒");
					}
				} else {
					second--;
					if (second >= 10) {
						if (minute >= 10) {
							countDown.setText(minute + "分" + second+"秒");
						} else {
							countDown.setText("0" + minute + "分" + second+"秒");
						}
					} else {
						if (minute >= 10) {
							countDown.setText(minute + "分0" + second+"秒");
						} else {
							countDown.setText("0" + minute + "分0" + second+"秒");
						}
					}
				}
			}
		};
	};

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_dopxkhsj);
		KingTellerApplication.addActivity(this);
		
		mContext = DoPxkhSjActivity.this;
		
		initUI();
		initData();
		changeUI();
		
	}

	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		
		title_text.setText("专项考核卷(满分100分)");

		fristQuestion = (TextView) findViewById(R.id.fristQuestion);
		secondQuestion = (TextView) findViewById(R.id.secondQuestion);
		thirdQuestion = (TextView) findViewById(R.id.thirdQuestion);
		countDown = (TextView) findViewById(R.id.countDown);

		choiceQuestions = (StGroupListView) findViewById(R.id.choice_questions);
		completionQuestions = (StGroupListView) findViewById(R.id.completion_questions);
		shortAnswerQuestions = (StGroupListView) findViewById(R.id.short_answer_questions);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(this);

		paperId = getIntent().getStringExtra("paperId");
		timeLong = Integer.parseInt(getIntent().getStringExtra("timeLong"));

	}

	private void initData() {
		getXztQuestions();
		getTktQuestions();
		getJdtQuestions();
	}

  
	
	private void changeUI() {	
		
		minute = timeLong;
		second = 0;
		countDown.setText(minute + ":" + second);

		timerTask = new TimerTask() {

			@Override
			public void run() {
				Message msg = new Message();
				msg.what = 0;
				handler.sendMessage(msg);
			}
		};

		timer = new Timer();
		timer.schedule(timerTask, 0, 1000);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_submit:
			dialog = new NormalDialog(mContext);
			KingTellerPromptDialogUtils.showTwoPromptDialog(dialog, "您是否提交试卷？",
					new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
						}
                    },new OnBtnClickL() {
	                    @Override
	                    public void onBtnClick() {
	                    	dialog.dismiss();
	                    	submitData(getJsonData());
	                    }
                });
			
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return false;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
		
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		if (timerTask != null) {
			timerTask = null;
		}
		minute = -1;
		second = -1;
	}

	private void submitData(String assign) {
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络，请检查网络是否可用!");
			return;
		}

		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("assign", assign);

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.TjsjUrl), params,
				new AjaxHttpCallBack<ReturnBackStatus>(this,
						new TypeToken<ReturnBackStatus>() {
						}.getType(), true) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "正在提交中...");
					}
			
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						KingTellerProgressUtils.closeProgress();
						
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(ReturnBackStatus data) {
						KingTellerProgressUtils.closeProgress();
						
						T.showShort(mContext, data.getMessage());
						if (data.getResult().equals("success")) {
							finish();
						}
					};
				});
	}

	private void getXztQuestions() {
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络，请检查网络是否可用!");
			return;
		}

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("paperId", paperId);
		params.put("userId", User.getInfo(this).getUserId());

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.XztUrl), params,
				new AjaxHttpCallBack<BasePageBean<XzParamBean>>(this,
						new TypeToken<BasePageBean<XzParamBean>>() {
						}.getType(), true) {

					@Override
					public void onFinish() {
					}

					@Override
					public void onDo(BasePageBean<XzParamBean> data) {

						if(data.getList().get(0).getQuestions().size()>0){
							fristQuestion.setText("选择题" + "(每小题" + data.getList().get(0).getCore() + "分)");
							
							xztCore = data.getList().get(0).getCore();
							for (int i = 0; i < data.getList().size(); i++) {
								xztkList = data.getList().get(i).getQuestions();
								setDataXztInfo(xztkList);
							}
						}
					};
				});
	}

	private void getTktQuestions() {
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络，请检查网络是否可用!");
			return;
		}

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("paperId", paperId);
		params.put("userId", User.getInfo(this).getUserId());

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.TktUrl), params,
				new AjaxHttpCallBack<BasePageBean<XzParamBean>>(this,
						new TypeToken<BasePageBean<XzParamBean>>() {
						}.getType(), true) {

					@Override
					public void onFinish() {
					}

					@Override
					public void onDo(BasePageBean<XzParamBean> data) {

						if(data.getList().get(0).getQuestions().size()>0){
							secondQuestion.setText("填空题" + "(每空" + data.getList().get(0).getCore() + "分)");

							tktCore = data.getList().get(0).getCore();
							for (int i = 0; i < data.getList().size(); i++) {
								xztkList = data.getList().get(i).getQuestions();
								setDataTktInfo(xztkList);
							}
						}
						
					};
				});
	}

	private void getJdtQuestions() {
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络，请检查网络是否可用!");
			return;
		}

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("paperId", paperId);
		params.put("userId", User.getInfo(this).getUserId());

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.JdtUrl), params,
				new AjaxHttpCallBack<BasePageBean<JdtParamBean>>(this,
						new TypeToken<BasePageBean<JdtParamBean>>() {
						}.getType(), true) {

					@Override
					public void onFinish() {
					}

					@Override
					public void onDo(BasePageBean<JdtParamBean> data) {
						if(data.getList().get(0).getQuestions().size()>0){
							thirdQuestion.setText("简答题" + "(每题" + data.getList().get(0).getCore() + "分)");
							
							jdtCore = data.getList().get(0).getCore();
							setDataJdtInfo(data.getList());
						}
						
					};
				});
	}

	private void setDataXztInfo(List<XzTkParamBean> data) {

		ChoiceQuestionView fview = null;
		for (int i = 0; i < data.size(); i++) {
			fview = new ChoiceQuestionView(this);
			fview.setData(data.get(i), i + 1);
			choiceQuestions.addView(fview);
		}
	}

	private void setDataTktInfo(List<XzTkParamBean> data) {
		CompleteQuestionView cview = null;
		for (int i = 0; i < data.size(); i++) {
			cview = new CompleteQuestionView(this);
			cview.setData(data.get(i).getQuest(), i + 1);
			completionQuestions.addView(cview);
		}
	}

	private void setDataJdtInfo(List<JdtParamBean> data) {
		ShortAnswerQuestionView saview = null;
		List<QuestionParamBean> questions = data.get(0).getQuestions();
		for (int i = 0; i < questions.size(); i++) {
			saview = new ShortAnswerQuestionView(this);
			saview.setData(questions.get(i), i + 1);
			shortAnswerQuestions.addView(saview);
		}
	}

	public String getJsonData() {
		List<KjBackDataBean> xztInfoList = getDataXztInfo();
		List<KjBackDataBean> tktInfoList = getDataTktInfo();
		List<KjBackDataBean> jdtInfoList = getDataJdtInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();
		
		params.put("xztCore", KingTellerJudgeUtils.isEmpty(xztCore)?"":xztCore);
		params.put("tktCore", KingTellerJudgeUtils.isEmpty(tktCore)?"":tktCore);
		params.put("jdtCore", KingTellerJudgeUtils.isEmpty(jdtCore)?"":jdtCore);
		params.put("paperId", paperId);
		params.put("userId", User.getInfo(this).getUserId());
		params.put("xztList", xztInfoList);
		params.put("tktList", tktInfoList);
		params.put("jdtList", jdtInfoList);
		return ConditionUtils.getJsonFromHashMap(params);
	}

	private List<KjBackDataBean> getDataXztInfo() {
		List<KjBackDataBean> kbdbList = new ArrayList<KjBackDataBean>();
		KjBackDataBean kbdb = null;
		int count = choiceQuestions.getChildCount();
		for (int i = 1; i < count; i++) {
			String id = ((TextView) choiceQuestions.getChildAt(i).findViewById(R.id.contentId)).getText().toString();
			kbdb = new KjBackDataBean();
			kbdb.setId(id);
			ListView listview = (ListView) choiceQuestions.getChildAt(i).findViewById(R.id.listView2);
			int count1 = listview.getChildCount();
			List<String> strList = new ArrayList<String>();
			String str = null;
			for (int j = 0; j < count1; j++) {
				CheckBox radioButton = (CheckBox) listview.getChildAt(j).findViewById(R.id.radioButton);
				if (radioButton.isChecked()) {
					str = ((TextView) listview.getChildAt(j).findViewById(R.id.choiceId)).getText().toString();
					strList.add(str);
				}
			}
			if (strList.size() == 1) {
				kbdb.setValue(strList.get(0));
			} else if (strList.size() > 1) {
				StringBuffer strBuffer = new StringBuffer();
				for (int k = 0; k < strList.size(); k++) {
					strBuffer.append(strList.get(k));
					if (k != strList.size() - 1) {
						strBuffer.append(",");
					}
				}
				kbdb.setValue(strBuffer.toString());
			} else {
				kbdb.setValue("");
			}
			kbdbList.add(kbdb);
		}
		
		return kbdbList;
	}

	private List<KjBackDataBean> getDataTktInfo() {
		List<KjBackDataBean> kbdbList = new ArrayList<KjBackDataBean>();
		KjBackDataBean kbdb = null;
		int count = completionQuestions.getChildCount();
		for (int i = 1; i < count; i++) {
			ListView listview = (ListView) completionQuestions.getChildAt(i)
					.findViewById(R.id.listView);
			int count1 = listview.getChildCount();
			for (int j = 0; j < count1; j++) {
				kbdb = new KjBackDataBean();
				String id = ((TextView) listview.getChildAt(j).findViewById(
						R.id.completeId)).getText().toString();
				String value = ((LineEditText) listview.getChildAt(j)
						.findViewById(R.id.myEdit)).getText().toString();
				kbdb.setId(id);
				kbdb.setValue(value);
				kbdbList.add(kbdb);
			}
		}
		return kbdbList;
	}

	private List<KjBackDataBean> getDataJdtInfo() {
		List<KjBackDataBean> kbdbList = new ArrayList<KjBackDataBean>();
		KjBackDataBean kbdb = null;
		int count = shortAnswerQuestions.getChildCount();
		for (int i = 1; i < count; i++) {
			kbdb = new KjBackDataBean();
			String id = ((TextView) shortAnswerQuestions.getChildAt(i)
					.findViewById(R.id.shortAnswerId)).getText().toString();
			String value = ((EditText) shortAnswerQuestions.getChildAt(i)
					.findViewById(R.id.saEditText)).getText().toString();
			kbdb.setId(id);
			kbdb.setValue(value);
			kbdbList.add(kbdb);
		}
		return kbdbList;
	}

}
