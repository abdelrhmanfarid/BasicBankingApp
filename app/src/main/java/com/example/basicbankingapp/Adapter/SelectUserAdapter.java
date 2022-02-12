package com.example.basicbankingapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicbankingapp.Data.User;
import com.example.basicbankingapp.R;

import java.util.ArrayList;

public class SelectUserAdapter extends RecyclerView.Adapter<SelectUserAdapter.ViewHolder>{
    private ArrayList<User> userArrayList;
    private OnUserListener onUserListener;

    public SelectUserAdapter(ArrayList<User> userArrayList, OnUserListener onUserListener) {
        this.userArrayList = userArrayList;
        this.onUserListener = onUserListener;
    }

    @NonNull
    @Override
    public SelectUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new SelectUserAdapter.ViewHolder(view, onUserListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectUserAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(userArrayList.get(position));
        holder.userName.setText(userArrayList.get(position).getName());
        holder.userAccountBalance.setText(String.format("%d", userArrayList.get(position).getBalance()));
    }

    @Override
    public int getItemCount() {
        return  userArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView userName, userAccountBalance;
        OnUserListener onUserListener;

        public ViewHolder(@NonNull View itemView, OnUserListener onUserListener) {
            super(itemView);
            userName = itemView.findViewById(R.id.username);
            userAccountBalance = itemView.findViewById(R.id.amount);
            this.onUserListener = onUserListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onUserListener.onUserClick(getAdapterPosition());
        }
    }

    public interface OnUserListener {
        void onUserClick(int position);
    }
}
