package com.fengyongge.popupwindow;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopwindowActivity extends AppCompatActivity {

    @BindView(R.id.rlLeft)
    RelativeLayout rlLeft;
    @BindView(R.id.tvTopText)
    TextView tvTopText;
    @BindView(R.id.tvRight)
    TextView tvRight;
    private PopupWindow popupWindow;
    private Boolean enabled = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        ButterKnife.bind(this);

        tvRight.setText("asfasfafaf");
    }


    @OnClick({R.id.rlLeft, R.id.tvRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlLeft:
                finish();
                break;
            case R.id.tvRight:
                enabled = !enabled;

                if (popupWindow != null
                        && popupWindow.isShowing()) {
                    popupWindow.dismiss();

                } else {

                    View popupWindow_view = getLayoutInflater().inflate(
                            R.layout.view_popwindow, null);

                    popupWindow = new PopupWindow(
                            popupWindow_view,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);

                    WindowManager.LayoutParams params = getWindow().getAttributes();
                    params.alpha = 0.7f;
                    getWindow().setAttributes(params);

                    popupWindow.setFocusable(true);
                    popupWindow
                            .setBackgroundDrawable(new BitmapDrawable());

                    popupWindow.showAsDropDown(tvRight, 0, 0);

                    // 点击其他地方消失
                    popupWindow
                            .setOnDismissListener(new PopupWindow.OnDismissListener() {

                                @Override
                                public void onDismiss() {
                                    // TODO Auto-generated method
                                    // stub
                                    enabled = true;

                                    WindowManager.LayoutParams params = getWindow().getAttributes();
                                    params.alpha = 1f;
                                    getWindow().setAttributes(params);
                                }
                            });

                    View tvOrder = popupWindow_view
                            .findViewById(R.id.tvOrder);

                }

                break;
        }
    }
}
