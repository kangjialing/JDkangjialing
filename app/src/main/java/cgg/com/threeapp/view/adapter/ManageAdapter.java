package cgg.com.threeapp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cgg.com.threeapp.R;
import cgg.com.threeapp.model.bean.AddressBean;
import cgg.com.threeapp.utils.CommonUtil;
import cgg.com.threeapp.view.activity.AddAddressActivity;
import cgg.com.threeapp.view.activity.ManageAddressActivity;

/**
 * Created by Administrator on 2018-03-15.
 */

public class ManageAdapter extends RecyclerView.Adapter<ManageAdapter.ManageViewHolder> {
    private List<AddressBean.DataBean> dataBeans;
    private Context context;
    private ManageAddressActivity manageAddressActivity;

    public ManageAdapter(List<AddressBean.DataBean> dataBeans, Context context, ManageAddressActivity manageAddressActivity) {
        this.dataBeans = dataBeans;
        this.context = context;
        this.manageAddressActivity = manageAddressActivity;
    }

    @Override
    public ManageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.address_list_view, parent, false);
        return new ManageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ManageViewHolder holder, int position) {
        TextView address = holder.getAddress();
        TextView consignee = holder.getConsignee();
        TextView del = holder.getDel();
        TextView edit = holder.getEdit();
        RadioButton isDefault = holder.getIsDefault();
        TextView tel = holder.getTel();


        final AddressBean.DataBean dataBean = dataBeans.get(position);
        String addr = dataBean.getAddr();
        final int addrid = dataBean.getAddrid();
        String mobile = dataBean.getMobile();
        String name = dataBean.getName();
        int status = dataBean.getStatus();

        tel.setText(mobile);
        consignee.setText(name);
        address.setText(addr);


        if (status == 1) {
            isDefault.setChecked(true);
            CommonUtil.setSharedString("userDefaultAddress", addr);
            CommonUtil.setSharedString("userConsignee", name);
        } else {
            isDefault.setChecked(false);
        }

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "待开发", Toast.LENGTH_SHORT).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddAddressActivity.class);
                intent.putExtra("dataBean",dataBean);
                context.startActivity(intent);
            }
        });

        isDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageAddressActivity.setPrograssBarIsShow(true);
                manageAddressActivity.setDefaultAddress(addrid);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    class ManageViewHolder extends RecyclerView.ViewHolder {
        private TextView consignee, edit, del, tel, address;
        private RadioButton isDefault;

        public ManageViewHolder(View itemView) {
            super(itemView);
            consignee = itemView.findViewById(R.id.adds_consignee);
            edit = itemView.findViewById(R.id.adds_edit);
            del = itemView.findViewById(R.id.adds_del);
            isDefault = itemView.findViewById(R.id.adds_isDefault);
            tel = itemView.findViewById(R.id.adds_tel);
            address = itemView.findViewById(R.id.adds_address);
        }

        public TextView getConsignee() {
            return consignee;
        }

        public TextView getEdit() {
            return edit;
        }

        public TextView getDel() {
            return del;
        }

        public TextView getTel() {
            return tel;
        }

        public TextView getAddress() {
            return address;
        }

        public RadioButton getIsDefault() {
            return isDefault;
        }
    }
}
