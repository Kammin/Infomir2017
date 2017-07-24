package kamin.com.infomir2017.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kamin.com.infomir2017.Activity.RSSActivity;
import kamin.com.infomir2017.Model.DataHolder;
import kamin.com.infomir2017.R;


public class RssAdapter extends RecyclerView.Adapter<RssAdapter.MyViewHolder> {
    private Context mContext;
    RSSActivity rssActivity;

    public RssAdapter(Context mContext) {
        this.mContext = mContext;
        rssActivity = (RSSActivity) mContext;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rss_item_detailed, parent, false);
            return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        Log.d("TAG", "nBind item ");
        viewHolder.title.setText(DataHolder.rss.item.get(position).title);
        viewHolder.description.setText(DataHolder.rss.item.get(position).description);

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @Override
    public int getItemCount() {
        Log.d("TAG", "nBind getItemCount "+DataHolder.rss.item.size());
        return DataHolder.rss.item.size();
    }

    @Override
    public void onViewRecycled(MyViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,description;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.description = (TextView) itemView.findViewById(R.id.description);
        }

    }



}
