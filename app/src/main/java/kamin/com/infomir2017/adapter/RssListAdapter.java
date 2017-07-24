package kamin.com.infomir2017.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import kamin.com.infomir2017.Activity.MainActivity;
import kamin.com.infomir2017.Model.DataHolder;
import kamin.com.infomir2017.R;


public class RssListAdapter extends RecyclerView.Adapter<RssListAdapter.MyViewHolder> {
    private Context mContext;

    public static final int ITEM_TYPE_NORMAL = 0;
    public static final int ITEM_TYPE_SHORT = 1;
    int id;

    MainActivity mainActivity;

    public RssListAdapter(Context mContext) {
        this.mContext = mContext;
        mainActivity = (MainActivity) mContext;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_NORMAL) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rss_item, parent, false);
            return new MyViewHolderNORMAL(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rss_item_short, parent, false);
            return new MyViewHolderShort(itemView);
        }

    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        final int pos = position;
        if (viewHolder.getItemViewType() == ITEM_TYPE_NORMAL) {
            MyViewHolderNORMAL holder = (MyViewHolderNORMAL) viewHolder;
            holder.title.setText(DataHolder.rssList.get(position).title);
            holder.description.setText(DataHolder.rssList.get(position).description);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    setID(DataHolder.rssList.get(pos).id);
                    return false;
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   mainActivity.goToRssScrean(DataHolder.rssList.get(pos).RSSLink);
                }
            });
        }
        else if (viewHolder.getItemViewType() == ITEM_TYPE_SHORT) {
            MyViewHolderShort holder = (MyViewHolderShort) viewHolder;
            holder.title.setText(DataHolder.rssList.get(position).title);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    setID(DataHolder.rssList.get(pos).id);
                    return false;
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.goToRssScrean(DataHolder.rssList.get(pos).RSSLink);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (DataHolder.rssList.get(position).description.equals("")) {
            return ITEM_TYPE_SHORT;
        } else {
            return ITEM_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return DataHolder.rssList.size();
    }

    @Override
    public void onViewRecycled(MyViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select The Action");
            contextMenu.add(0, 1, 0, "Delete item");
        }

    }

    public class MyViewHolderShort extends MyViewHolder {
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolderShort(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
            //thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }

    public class MyViewHolderNORMAL extends MyViewHolder {
        public TextView title,description;
        public ImageView thumbnail;

        public MyViewHolderNORMAL(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.description = (TextView) itemView.findViewById(R.id.description);
            //thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        }

    }

    public int getID() {
        return id;
    }

    public void setID(int position) {
        this.id = position;
    }

}
