package com.example.robotics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<person> person;
    public MyAdapter(Context c,ArrayList<person> usedata){
        context=c;
        person=usedata;
    }
    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.users,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.name.setText(person.get(position).getFullname());
        holder.email.setText(person.get(position).getEmail());
        holder.phone.setText(person.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return person.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,email,phone;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            name=itemView.findViewById(R.id.show_fullname);
            email=itemView.findViewById(R.id.show_email);
            phone=itemView.findViewById(R.id.show_phone);

        }
    }

}
