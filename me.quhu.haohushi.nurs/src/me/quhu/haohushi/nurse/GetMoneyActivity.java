package me.quhu.haohushi.nurse;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.JsonHttpResponseHandler;

import me.quhu.haohushi.nurse.bean.MySpaceBean;
import me.quhu.haohushi.nurse.bean.OrderMessageBean;
import me.quhu.haohushi.nurse.http.ApiHttpClient;
import me.quhu.haohushi.nurse.http.HttpConstants;
import me.quhu.haohushi.nurse.utils.UIUtils;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GetMoneyActivity extends CustomBaseActivity {
	private TextView tv_title;
    private ImageView iv_back;
    private RelativeLayout rl_suggest;
    
    private EditText et_money;
    private RelativeLayout rl_bank;
   
    private Button bt_confirm;
	private String cardid=null;
	private StringEntity entity;
	private String bankname;
	private TextView tv_bankname;
	private MySpaceBean bean;

	private View view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_money,true,true,R.string.string_title_getmoney,R.string.string_title_help_right);
		init();
		findViews();
		setListeners();
		loadData();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.bt_confirm:
			if(TextUtils.isEmpty(cardid)||TextUtils.isEmpty(et_money.getText().toString()))
			UIUtils.showToastSafe("信息填写不完整");
			else
			{
				sendHttp();
			}
			
			break;
		case R.id.rl_bank:
			startActivityForResult(new Intent(this,MybankActivity.class), 100);
			break;
		

		default:
			break;
		}
	}

	/**
	 * @param contentLayoutId 布局文件
	 * @param ifShow          是否显示父类布局文件类型
	 * @param ifshowBack
	 * @param title
	 * @param rightStr
	 * @author: ly
	 * create time: 2016/5/9 13:01
	 * description：加载布局
	 */
	@Override
	protected void setContentView(int contentLayoutId, boolean ifShow, boolean ifshowBack, int title, int rightStr) {
		view = setBaseContentView(contentLayoutId,ifShow,ifshowBack,title,rightStr);
		setContentView(view);
	}

	/**
	 * @author: ly
	 * create time: 2016/5/4 11:14
	 * description：初始化，setContentView后调用
	 */
	@Override
	protected void init() {
		bean=(MySpaceBean) getIntent().getSerializableExtra("money");
	}

	/**
	 * @author: ly
	 * create time: 2016/5/4 11:13
	 * description：查找视图。init后调用；
	 */
	@Override
	protected void findViews() {
		iv_back = (ImageView) view.findViewById(R.id.iv_back);
		et_money = (EditText) view.findViewById(R.id.et_money);
		rl_bank = (RelativeLayout) view.findViewById(R.id.rl_bank);
		tv_bankname = (TextView) view.findViewById(R.id.tv_bankname);
		bt_confirm = (Button) view.findViewById(R.id.bt_confirm);
	}

	/**
	 * @author: ly
	 * create time: 2016/5/4 11:13
	 * description：设置侦听器，findViews后调用
	 */
	@Override
	protected void setListeners() {
		iv_back.setOnClickListener(this);
		bt_confirm.setOnClickListener(this);
		rl_bank.setOnClickListener(this);
	}

	/**
	 * @author: ly
	 * create time: 2016/5/4 11:13
	 * description：释放资源
	 */
	@Override
	protected void release() {

	}

	/**
	 * @author: ly
	 * create time: 2016/5/9 13:32
	 * description：findviewById后加载数据
	 */
	@Override
	protected void loadData() {
		et_money.setText(bean.getCount());
		et_money.setEnabled(false);
	}

	private void sendHttp() {
		// TODO Auto-generated method stub
		JSONObject json=new JSONObject();
		try {
			json.put("cardId",cardid);	
			json.put("amount_out", bean.account);
			entity = new StringEntity(json.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ApiHttpClient.postJson(
				HttpConstants.GETMONEY,
				entity, new JsonHttpResponseHandler() {
					

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						// TODO Auto-generated method stub
						super.onSuccess(statusCode, headers, response);
						try {
							if(response.get("status").equals("SUCCESS"))
							{UIUtils.showToastSafe(response.getString("message"));
							showFinish();}else
							{
								UIUtils.showToastSafe(response.getString("message"));
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					public void onFailure(int statusCode, Header[] headers,
							Throwable throwable, JSONObject errorResponse) {
						UIUtils.showToastSafe("网络连接失败");
					}
				});
			
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(data!=null)
		if(data.hasExtra("cardid"))
		{
			cardid = data.getStringExtra("cardid");
		bankname = data.getStringExtra("bankname");
		tv_bankname.setText(bankname);
		}
	}
public void showFinish()
{
	finish();
}
}
