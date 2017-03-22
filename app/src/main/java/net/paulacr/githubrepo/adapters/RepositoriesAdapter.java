package net.paulacr.githubrepo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.paulacr.githubrepo.R;
import net.paulacr.githubrepo.data.Item;
import net.paulacr.githubrepo.utils.OnListItemClick;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoriesAdapter.ViewHolder> {

    private List<Item> itemList;
    private Context context;
    private OnListItemClick clickListener;

    public RepositoriesAdapter(List<Item> items, Context context, OnListItemClick listener) {
        this.context = context;
        this.itemList = items;
        setClickListener(listener);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.repoName)
        TextView repoName;
        @Bind(R.id.repoDescription)
        TextView repoDescritption;
        @Bind(R.id.repoForksCount)
        TextView repoForksCount;
        @Bind(R.id.repoStarsCount)
        TextView repoStarsCount;
        @Bind(R.id.repoUsername)
        TextView repoUsername;
        @Bind(R.id.repoProfilePicture)
        CircleImageView repoProfilePicture;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_repository, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Item item = itemList.get(position);

        holder.repoName.setText(item.getName());
        holder.repoDescritption.setText(item.getDescription());
        holder.repoForksCount.setText(String.valueOf(item.getForksCount()));
        holder.repoStarsCount.setText(String.valueOf(item.getStarsCount()));
        holder.repoUsername.setText(item.getOwner().getLogin());

        //Picasso implementation
        Picasso.with(context).load(item.getOwner().getAvatarUrl())
                .placeholder(R.drawable.user_placeholder).into(holder.repoProfilePicture);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onListItemClicked(itemList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    //**************************************************************************
    // Set Listeners
    //**************************************************************************

    public void setClickListener(OnListItemClick listener) {
        this.clickListener = listener;
    }

}
