package com.kingteller.client.view.groupview.itemview;

import com.kingteller.R;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.db.AtmGroup;
import com.kingteller.client.bean.map.AtmGroupBean;
import com.kingteller.client.bean.map.CheckMacineBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.GroupPicGridView;
import com.kingteller.client.view.GroupPicGridView.OnItemClickLister;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.image.allview.SelectPicGridView;
import com.kingteller.client.view.image.allview.SelectPicGridView.OnSelectImageItemLister;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 添加ATP的视图
 * @author 王定波
 *
 */
public class ATMGroupView extends LinearLayout {

	private boolean isdel;
    private int type;
    private Context mContext;

    private RadioButton atmcodeID;
    private RadioButton storterID;
    private EditText atmcodeET;
    private TextView sn_str;
    private TextView jqbhText;
    private Button btn_delete, btn_seach;

    private SelectPicGridView layout_select_pic;
    
    /**
     * -1未输入,
     * -2需要重新验证 ,
     * 0没有图片 且 无经纬度信息,
     * 1没有图片 且 有经纬度信息,
     * 2有待审核图片 且 无经纬度信息,
     * 3有待审核图片 且 有经纬度信息,
     * 4有合格图片 且无经纬度信息,
     * 5有合格图片 且有经纬度信息,
     */
    private int status = -1;


    private OnATMGroupLister onATMGroupLister;
    
    public interface OnATMGroupLister {
        public void OnItemClick(ATMGroupView aview, int type, int num);
        public void OnDelClick(ATMGroupView aview);
    }

    public void setOnATMGroupLister(OnATMGroupLister onATMGroupLister) {
        this.onATMGroupLister = onATMGroupLister;
    }
    
    public ATMGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ATMGroupView(Context context) {
        super(context);
        initView();
    }

    public ATMGroupView(Context context, boolean isdel) {
        super(context);
        this.mContext = context;
        this.isdel = isdel;
        initView();
    }

    public ATMGroupView(Context context, boolean isdel, int type) {
        super(context);
        this.isdel = isdel;
        this.type = type;
        initView();
    }

	private void initView() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_group_add_atm, this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 0, 0, 0);
		setLayoutParams(lp);

        layout_select_pic = (SelectPicGridView) findViewById(R.id.layout_add_pic);

        atmcodeID = (RadioButton) findViewById(R.id.atmcodeID);
        atmcodeID.setChecked(true);

        storterID = (RadioButton) findViewById(R.id.storterID);
        btn_seach = (Button) findViewById(R.id.btn_seach);

        sn_str = (TextView) findViewById(R.id.sn_str);
        jqbhText = (TextView) findViewById(R.id.jqbhText);
        atmcodeET = (EditText) findViewById(R.id.atmcodeET);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        
        if (isdel){
            findViewById(R.id.btn_delete).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.btn_delete).setVisibility(View.INVISIBLE);
        }
        
		atmcodeET.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(jqbhText.getVisibility() == View.VISIBLE){
                    jqbhText.setText("");
                    jqbhText.setVisibility(View.GONE);
                }


                if (s.length() > 0) {
                    status = -2;
                } else {
                    status = -1;
                }
			}

		});

		atmcodeID.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean ischecked) {
				 if (ischecked) {
					 sn_str.setVisibility(View.VISIBLE);
	             } else{
	             	 sn_str.setVisibility(View.GONE);
	             }
			}
		});

		storterID.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean ischecked) {
                if (ischecked) {
                    sn_str.setVisibility(View.GONE);
                } else{
                    sn_str.setVisibility(View.VISIBLE);
                }
			}
		});
		
		btn_seach.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				getMachineInfo();
			}
		});

		btn_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (onATMGroupLister != null)
                    onATMGroupLister.OnDelClick(ATMGroupView.this);
			}
		});
		
	   layout_select_pic.setOnSelectImageItemLister(new OnSelectImageItemLister() {
            @Override
            public void OnItemClick(View view, int type, int num) {
                if (onATMGroupLister != null)
                    onATMGroupLister.OnItemClick(ATMGroupView.this, type, num);
            }
        });
	}

	public SelectPicGridView getLayoutAddPic() {
        return layout_select_pic;
    }

	private void getMachineInfo() {
		String maccode = atmcodeET.getText().toString().trim();

        if (KingTellerJudgeUtils.isEmpty(maccode)) {
            T.showShort(mContext, "请输入机器编号!");
            return;
        }
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();

		if (atmcodeID.isChecked()){
			maccode = "S/N" + maccode;
		}
		params.put("jqbh", maccode);
		params.put("userid", User.getInfo(getContext()).getUserId());

		fh.post(KingTellerConfigUtils.CreateUrl(getContext(), KingTellerUrlConfig.CheckMacineUrl),
				params, new AjaxHttpCallBack<CheckMacineBean>(getContext(),
						true) {

					@Override
					public void onFinish() {
						KingTellerProgressUtils.closeProgress();
					}

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(getContext(), "正在检测...");
					}

					@Override
					public void onDo(CheckMacineBean data) {
						if ("lose".equals(data.getPicFlag().trim())) {
							status = -2;
							T.showShort(getContext(), "请重新输入机器编号!");
						} else{
							if ("无图片".equals(data.getPicFlag().trim())) {
								if("无经纬度信息".equals(data.getJwdFlag().trim())){
									status = 0;
								}else{
									status = 1;
								}
							} else if ("有待审核图片".equals(data.getPicFlag().trim())) {
								if("无经纬度信息".equals(data.getJwdFlag().trim())){
									status = 2;
								}else{
									status = 3;
								}
							} else if ("有合格图片".equals(data.getPicFlag().trim())) {
								if("无经纬度信息".equals(data.getJwdFlag().trim())){
									status = 4;
								}else{
									status = 5;
								}
							}
							jqbhText.setVisibility(View.VISIBLE);
							jqbhText.setText(" 图片状态:" + data.getPicFlag() + "\n "
									+ "位置状态:" + data.getJwdFlag() + "\n "
									+ "网点地址:" + data.getWddz());
						} 
						
					};
				});
	}
	
	public AtmGroupBean getData() {
		 AtmGroupBean data = new AtmGroupBean();
	        if (atmcodeID.isChecked()){
	            data.setJqbh("S/N" + atmcodeET.getText().toString());
	        }else{
	            data.setJqbh(atmcodeET.getText().toString());
	        }
	        data.setPiclist(layout_select_pic.getImagesList());
	        data.setStatus(status);
	        return data;
	}
}
