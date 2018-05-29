package cgg.com.threeapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

import cgg.com.threeapp.R;
import cgg.com.threeapp.utils.ChenjinUtil;
import cgg.com.threeapp.utils.OkHttpUtil;
import cgg.com.threeapp.view.adapter.DiscoverAdapter;
import cn.jzvd.JZVideoPlayer;

/**
 * author: Wanderer
 * date:   2018/2/23 19:11
 * email:  none
 */

public class DiscoverFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discover_fragment,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.discover_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new DiscoverAdapter(getActivity()));
        HashMap<String, String> map = new HashMap<>();
       // OkHttpUtil.doPost();
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initChenJin();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (! hidden) {
            initChenJin();
        }
    }

    private void initChenJin() {
        ChenjinUtil.setStatusBarColor(getActivity(), getResources().getColor(R.color.colorGray));
    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
