package com.example.myquizapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquizapp.Activity.ManagerUserActivity;
import com.example.myquizapp.Activity.UpdateUserActivity;
import com.example.myquizapp.Model.Rank;
import com.example.myquizapp.Model.User;
import com.example.myquizapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.HolderUser> implements Filterable {

    ManagerUserActivity context;
    List<User> userList;
    List<User> userListOld;

    public AdapterUser(ManagerUserActivity context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        this.userListOld = userList;
    }

    @NonNull
    @Override
    public HolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user,parent,false);
        return new HolderUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderUser holder, int position) {
        User user = userList.get(position);
        holder.idtxt.setText(user.getU_id());
        holder.emailtxt.setText(user.getEmail());
        holder.nametxt.setText(user.getName());
        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.delete(user.getU_id());
            }
        });
        holder.updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateUserActivity.class);
                intent.putExtra("data", (Serializable) user);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if(search.isEmpty()){
                    userList = userListOld;
                }else{
                    List<User> list = new ArrayList<>();
                    for(User user : userListOld){
                        if(user.getName().toLowerCase().contains(search.toLowerCase())){
                            list.add(user);
                        }
                    }
                    userList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = userList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                userList = (List<User>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class HolderUser extends RecyclerView.ViewHolder{
        TextView idtxt,nametxt,emailtxt;
        ImageButton updatebtn,deletebtn;
        public HolderUser(@NonNull View itemView) {
            super(itemView);
            idtxt = itemView.findViewById(R.id.id);
            nametxt = itemView.findViewById(R.id.name);
            emailtxt = itemView.findViewById(R.id.email);
            updatebtn = itemView.findViewById(R.id.update);
            deletebtn = itemView.findViewById(R.id.delete);
        }
    }
}
