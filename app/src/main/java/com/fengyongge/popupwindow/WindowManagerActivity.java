package com.fengyongge.popupwindow;

import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WindowManagerActivity extends AppCompatActivity {


    @BindView(R.id.tvTopTitle)
    TextView tvTopTitle;
    @BindView(R.id.btAll)
    Button btAll;
    @BindView(R.id.btZone)
    Button btZone;
    private View mContentView;
    private WindowManager windowManager;
    private WindowManager.LayoutParams mWinLayoutParams;
    private Display display;
    ArrayList<AllBean> all_list = new ArrayList<>();
    private boolean all_isSelect;

    private View rlAllView;
    private View llTitle;
    private View llChose;
    AllAdapter allAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_manager);


        ButterKnife.bind(this);
        windowManager = getWindowManager();
        display = windowManager.getDefaultDisplay();

        intiView();
        initData();
    }

    public void initData() {

        //全部
        AllBean allBean1 = new AllBean();
        allBean1.setName("全部");
        allBean1.setId("0");
        allBean1.setIs_check(false);
        AllBean allBean2 = new AllBean();
        allBean2.setName("我创建的");
        allBean2.setId("1");
        allBean2.setIs_check(false);
        AllBean allBean3 = new AllBean();
        allBean3.setName("我是助理");
        allBean3.setId("2");
        allBean3.setIs_check(false);

        all_list.add(allBean1);
        all_list.add(allBean2);
        all_list.add(allBean3);
        allAdapter.notifyDataSetChanged();
    }


    private void intiView() {
        tvTopTitle.setText("会议管理");
        llTitle = findViewById(R.id.llTitle);
        llChose = findViewById(R.id.llChose);
        rlAllView = findViewById(R.id.rlAllView);
        allAdapter = new AllAdapter();

        getWindowParameter();
    }


    private void getWindowParameter() {
        llChose.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int[] locations = new int[2];
                        llChose.getLocationInWindow(locations);
                        mWinLayoutParams = new WindowManager.LayoutParams();
                        mWinLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
                        mWinLayoutParams.x = locations[0];
                        mWinLayoutParams.y = locations[1] + llChose.getHeight();

                        mWinLayoutParams.width = display.getWidth();

                        // 添加属性FLAG_WATCH_OUTSIDE_TOUCH，用于监听窗口外Touch事件
                        mWinLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

                        mWinLayoutParams.windowAnimations = 0;
                        mWinLayoutParams.format = PixelFormat.RGBA_8888;
                        if (Build.VERSION.SDK_INT < 16) {
                            llChose.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);

                        } else {
                            llChose.getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(this);
                        }
                    }
                });

    }



    @OnClick({R.id.btAll, R.id.btZone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btAll:
                all_isSelect = !all_isSelect;
                btAll.setSelected(all_isSelect);

                showAllPopu();
                break;
            case R.id.btZone:
                break;
        }
    }


    private void showAllPopu() {
        if (btAll.isSelected()) {
            if (mContentView != null) {
                try {
                    windowManager.removeView(mContentView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mWinLayoutParams.height = rlAllView.getMeasuredHeight() - llTitle.getMeasuredHeight() - llChose.getMeasuredHeight();
            mContentView = getLayoutInflater().inflate(R.layout.view_windowmanager, null);
            ListView all_listview = (ListView) mContentView.findViewById(R.id.all_listview);
            all_listview.setAdapter(allAdapter);
            allAdapter.notifyDataSetChanged();

            mContentView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    Rect rect = new Rect();
                    mContentView.getGlobalVisibleRect(rect);
                    if (rect.contains(x, y)) {
                        windowManager.removeView(mContentView);
                    }
                    Logger.i("onTouch : " + x + ", " + y + ", rect: " + rect);
                    return false;
                }
            });

            ViewGroup.LayoutParams params = all_listview.getLayoutParams();
            params.height = display.getWidth() * 3 / 5;
            all_listview.setLayoutParams(params);

            windowManager.addView(mContentView, mWinLayoutParams);
        } else {
            if (mContentView != null) {
                windowManager.removeView(mContentView);
                mContentView = null;
            }
        }
    }


    /**
     * 全部/我创建的
     */
    class AllAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return all_list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub

            return all_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }


        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {

            final ViewHolder holder;

            if (convertView == null) {

                convertView = getLayoutInflater().inflate(
                        R.layout.activity_window_manager_item, null);

                holder = new ViewHolder();
                holder.supplier_text = (TextView) convertView.findViewById(R.id.supplier_text);
                holder.check_CB = (CheckBox) convertView.findViewById(R.id.check_CB);
                holder.item = (RelativeLayout) convertView.findViewById(R.id.item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.supplier_text.setText(all_list.get(position)
                    .getName());

            if (all_list.get(position).getIs_check()) {
                holder.supplier_text.setSelected(true);
                holder.check_CB.setChecked(true);
            } else {
                holder.supplier_text.setSelected(false);
                holder.check_CB.setChecked(false);
            }


            holder.item.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    holder.supplier_text.setSelected(true);
                    holder.check_CB.setChecked(true);

                    for (int i = 0; i < all_list.size(); i++) {
                        if (all_list.get(position).getId().equals(all_list.get(i).getId())) {
                            all_list.get(i).setIs_check(true);
                        } else {
                            all_list.get(i).setIs_check(false);
                        }
                    }

                    all_isSelect = false;

                    try {
                        windowManager.removeView(mContentView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });
            return convertView;
        }



        class ViewHolder {
            TextView supplier_text;
            CheckBox check_CB;
            RelativeLayout item;

        }

    }
}