package cgg.com.threeapp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.CartBean;

/**
 * Created by Administrator on 2018-03-11.
 */

public class ConfirmOrderAdapter extends RecyclerView.Adapter<ConfirmOrderAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<CartBean.DataBean.ListBean> orderList;

    public ConfirmOrderAdapter(Context context, ArrayList<CartBean.DataBean.ListBean> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.order_adapter_view, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageView icon = holder.getIcon();
        TextView num = holder.getNum();
        TextView price = holder.getPrice();
        TextView title = holder.getTitle();

        CartBean.DataBean.ListBean listBean = orderList.get(position);
        int numStr = listBean.getNum();
        double bargainPrice = listBean.getBargainPrice();
        String titleStr = listBean.getTitle();
        String iconUrl = listBean.getImages().split("\\|")[0];

        Glide.with(context).load(iconUrl).into(icon);
        num.setText("× "+numStr);
        title.setText(titleStr);
        price.setText("￥"+bargainPrice);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView num, price, title;

        public ImageView getIcon() {
            return icon;
        }

        public TextView getNum() {
            return num;
        }

        public TextView getPrice() {
            return price;
        }

        public TextView getTitle() {
            return title;
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.order_icon);
            num = itemView.findViewById(R.id.order_num);
            price = itemView.findViewById(R.id.order_price);
            title = itemView.findViewById(R.id.order_title);
        }
    }
}
