package net.paulacr.githubrepo.repositories;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.paulacr.githubrepo.R;
import net.paulacr.githubrepo.data.Item;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by paularosa on 3/31/16.
 */
public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoriesAdapter.ViewHolder> {

    private List<Item> itemList;
    private Context context;

    public RepositoriesAdapter(Context context, List<Item> items) {
        this.context = context;
        this.itemList = items;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.repoName.setText(item.getName());
        holder.repoDescritption.setText(item.getDescription());
        holder.repoForksCount.setText(String.valueOf(item.getForksCount()));
        holder.repoStarsCount.setText(String.valueOf(item.getStarsCount()));
        holder.repoUsername.setText(item.getOwner().getLogin());

        //Picasso implementation
        Picasso.with(context).load(item.getOwner().getAvatarUrl())
                .placeholder(R.drawable.user_placeholder).into(holder.repoProfilePicture);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
