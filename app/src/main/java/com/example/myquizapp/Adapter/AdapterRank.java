package com.example.myquizapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquizapp.Model.Rank;
import com.example.myquizapp.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterRank extends RecyclerView.Adapter<AdapterRank.HolderRank> implements Filterable {
    Context context;
    List<Rank> rankList;
    List<Rank> rankListOld;

    public AdapterRank(Context context, List<Rank> rankList) {
        this.context = context;
        this.rankList = rankList;
        this.rankListOld = rankList;
    }

    @NonNull
    @Override
    public HolderRank onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rank,parent,false);
        return new HolderRank(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderRank holder, int position) {
        Rank rank = rankList.get(position);
        holder.stt.setText((position+1)+"");
        holder.name.setText(rank.getName());
        holder.email.setText(rank.getEmail());
        holder.score.setText(rank.getScore()+"");
    }

    @Override
    public int getItemCount() {
        return rankList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if(search.isEmpty()){
                    rankList = rankListOld;
                }else{
                    List<Rank> list = new ArrayList<>();
                    for(Rank rank : rankListOld){
                        if(rank.getName().toLowerCase().contains(search.toLowerCase())){
                            list.add(rank);
                        }
                    }
                    rankList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = rankList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                rankList = (List<Rank>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class HolderRank extends RecyclerView.ViewHolder{
        TextView stt,name,email,score;
        public HolderRank(@NonNull View itemView) {
            super(itemView);
            stt = itemView.findViewById(R.id.stt);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            score = itemView.findViewById(R.id.score);
        }
    }
}
