package me.quhu.haohushi.nurse;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import me.quhu.haohushi.nurse.application.BaseApplication;
import me.quhu.haohushi.nurse.service.receiveutils.ReceiverModelAction;
import me.quhu.haohushi.nurse.service.receiveutils.ReceiverModelUtils;
import me.quhu.haohushi.nurse.utils.ActivityManager;
import me.quhu.haohushi.nurse.utils.LogUtils;
import me.quhu.haohushi.nurse.utils.TimerUtils;

public class FacePayResultActivity extends CustomBaseActivity {

    private ImageView iv_back;
    TextView tv_orderAmount;//订单金额
    String orderAmount;//金额
    String orderNO;
    private TextView tv_orderNO;//
    private TextView tv_orderConform;//收款时间:

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e(">>>>>>>>>>>>>getClassName"+getMyClassName());
        ((BaseApplication)getApplication()).addDestoryActivity(this,"FacePayResultActivity");
        setContentView(R.layout.v1_1_activity_payresult,true,true,R.string.string_title_pacepayresult,R.string.string_title_pacepayresult_right);
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
        orderAmount = getIntent().getStringExtra("orderAmount");
        orderNO = getIntent().getStringExtra("orderNO");
        ((BaseApplication)getApplication()).destoryActivity("ScanpayActivity");
    }

    /**
     * @author: ly
     * create time: 2016/5/4 11:13
     * description：查找视图。init后调用；
     */
    @Override
    protected void findViews() {
        tv_orderAmount = (TextView) view.findViewById(R.id.tv_orderAmount);
        tv_orderNO = (TextView) view.findViewById(R.id.tv_orderNO);
        tv_orderConform = (TextView) view.findViewById(R.id.tv_orderConform);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
    }

    /**
     * @author: ly
     * create time: 2016/5/4 11:13
     * description：设置侦听器，findViews后调用
     */
    @Override
    protected void setListeners() {
        iv_back.setOnClickListener(this);
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
        tv_orderAmount.setText(orderAmount);
        tv_orderNO.setText("订单号:"+orderNO);
        tv_orderConform.setText("收款时间:"+ TimerUtils.getStringDate());
        ReceiverModelUtils receiverModel = new ReceiverModelUtils(FacePayResultActivity.this);
        receiverModel.sendReceiver(ReceiverModelAction.RECEIVER_ACTION_PAYRESULT, "dataStatus", "1");
    }

}
