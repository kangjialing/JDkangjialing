package cgg.com.threeapp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import cgg.com.threeapp.R;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * author: Wanderer
 * date:   2018/3/30 22:55
 * email:  none
 */
public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.DiscoverViewHolder> {
    private Context context;
    private String videoUrl = "https://www.zhaoapi.cn/images/quarter/1517733899967video_fucking_awesome.mp4";
    private String iconUrl = "https://www.zhaoapi.cn/images/quarter/1517733899967video_cover_fucking.jpg";

    public DiscoverAdapter(Context context) {
        this.context = context;
    }

    @Override
    public DiscoverViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.discover_item_view, null);
        return new DiscoverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DiscoverViewHolder holder, int position) {
        JZVideoPlayerStandard videoPlayer = holder.getVideoPlayer();
        Glide.with(context).load(iconUrl).into(videoPlayer.thumbImageView);
        videoPlayer.setUp(videoUrl, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "1511牛班");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class DiscoverViewHolder extends RecyclerView.ViewHolder {
        private JZVideoPlayerStandard videoPlayer;

        public DiscoverViewHolder(View itemView) {
            super(itemView);
            videoPlayer = itemView.findViewById(R.id.videoplayer);
        }

        public JZVideoPlayerStandard getVideoPlayer() {
            return videoPlayer;
        }
    }
}
