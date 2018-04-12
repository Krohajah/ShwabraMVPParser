package com.krohajah.shwabramvpparser.ui.adapter.rcadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.krohajah.shwabramvpparser.R;
import com.krohajah.shwabramvpparser.model.FeedItem;
import com.krohajah.shwabramvpparser.ui.adapter.base.BaseRecyclerAdapter;


/**
 * @author Maxim Berezin.
 */
public class FeedAdapter extends BaseRecyclerAdapter<FeedItem, FeedAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(Html.fromHtml(items.get(position).getTitle()));
        holder.link.setText(Html.fromHtml(items.get(position).getLink()));
        holder.description.setText(Html.fromHtml(items.get(position).getDescription()));
        holder.pubDate.setText(Html.fromHtml(items.get(position).getPubDate()));
        holder.creator.setText(Html.fromHtml(items.get(position).getCreator()));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView link;
        TextView description;
        TextView pubDate;
        TextView creator;

        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            link = itemView.findViewById(R.id.link);
            description = itemView.findViewById(R.id.description);
            pubDate = itemView.findViewById(R.id.pubDate);
            creator = itemView.findViewById(R.id.creator);
        }
    }
}
