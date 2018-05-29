package cgg.com.threeapp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.OrderBean;
import cgg.com.threeapp.utils.API;
import cgg.com.threeapp.utils.CommonUtil;
import cgg.com.threeapp.utils.OkHttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2018-03-11.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private Context context;
    private OrderBean orderBean;
    private Map<String,String> map;
    public OrderAdapter(Context context, OrderBean orderBean) {
        this.context = context;
        this.orderBean = orderBean;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        map = new HashMap<>();
        View view = LayoutInflater.from(context).inflate(R.layout.order_list_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        TextView createTime = holder.getmOrderCreateTime();
        TextView orderPrice = holder.getmOrderPrice();
        TextView orderState = holder.getmOrderState();
        TextView orderTitle = holder.getmOrderTitle();
        TextView textView = holder.getmOrderShow();
        OrderBean.DataBean dataBean = orderBean.getData().get(position);
        String createtime = dataBean.getCreatetime();
        double price = dataBean.getPrice();
        int status = dataBean.getStatus();
        final int orderid = dataBean.getOrderid();
        String title = dataBean.getTitle();
        String statusStr;
        /**
         * 0 待支付
         * 1 已支付
         * 2 已取消
         */
        if (status == 0) {
            statusStr = "待支付";
        } else if (status == 1) {
            statusStr = "已支付";
        } else if (status == 2) {
            statusStr = "已取消";
        } else {
            statusStr = "不明状态";
        }

        if(status == 0){
            textView.setText("取消订单");
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    map.put("uid", CommonUtil.getIntValue("uid")+"");
                    map.put("status","2");
                    map.put("orderId",""+orderid);
                    OkHttpUtil.doPost(API.UPDATE_ORDER_URL, map, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if(response.isSuccessful()){
                                CommonUtil.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        orderBean.getData().remove(position);
                                        notifyDataSetChanged();
                                        Toast.makeText(context, "取消成功", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    });
                }
            });
        }else{
            textView.setText("查看订单");
        }

        createTime.setText("创建时间: "+createtime);
        orderPrice.setText("价格 " + price);
        orderState.setText(statusStr);
        orderTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        return orderBean.getData().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mOrderCreateTime, mOrderPrice, mOrderState, mOrderTitle,mOrderShow;

        public TextView getmOrderCreateTime() {
            return mOrderCreateTime;
        }

        public TextView getmOrderPrice() {
            return mOrderPrice;
        }

        public TextView getmOrderState() {
            return mOrderState;
        }

        public TextView getmOrderTitle() {
            return mOrderTitle;
        }

        public TextView getmOrderShow() {
            return mOrderShow;
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            mOrderCreateTime = itemView.findViewById(R.id.mOrderCreateTime);
            mOrderPrice = itemView.findViewById(R.id.mOrderPrice);
            mOrderState = itemView.findViewById(R.id.mOrderState);
            mOrderTitle = itemView.findViewById(R.id.mOrderTitle);
            mOrderShow = itemView.findViewById(R.id.mOrderShow);
        }
    }
}
