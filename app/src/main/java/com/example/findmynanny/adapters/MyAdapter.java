package com.example.findmynanny.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.findmynanny.MySingleton;
import com.example.findmynanny.listeners.NannyClickListener;
import com.example.findmynanny.models.Nanny;
import com.example.findmynanny.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Nanny> nannies;
    private final Context mContext;
    private NannyClickListener mListener;

    // Constructor (depends on the kind of dataset)
    public MyAdapter(List<Nanny> myDataset, Context context, NannyClickListener nannyClickListener){
        nannies = myDataset;
        mContext = context;
        mListener = nannyClickListener;
    }

    public void setNannies(List<Nanny> nannies) {
        this.nannies = nannies;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nanny_info, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v, mListener);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        holder.firstName.setText(nannies.get(position).getFirstName());
        holder.lastName.setText(nannies.get(position).getLastName());
        holder.age.setText("Age: " + Integer.toString(nannies.get(position).getAge()));
        holder.address.setText(nannies.get(position).getAddress());
        holder.phoneNumber.setText(nannies.get(position).getPhoneNumber());
        try {
            holder.photo.setImageUrl(nannies.get(position).getPhotoUrl(), MySingleton.getInstance(mContext).getImageLoader());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    // Return the size of the dataset
    @Override
    public int getItemCount() {
        return nannies.size();
    }


    public void addNanny (Nanny nanny) {
        List<Nanny> nannies = this.nannies;
        nannies.add(nanny);
        this.nannies = nannies;


    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        NannyClickListener mListener;
        TextView firstName;
        TextView lastName;
        TextView age;
        TextView address;
        TextView phoneNumber;
        NetworkImageView photo;

        MyViewHolder(View itemView, NannyClickListener nannyClickListener) {
            super(itemView);
            firstName = itemView.findViewById(R.id.nanny_first_name);
            lastName = itemView.findViewById(R.id.nanny_last_name);
            age = itemView.findViewById(R.id.nanny_age);
            address = itemView.findViewById(R.id.nanny_address);
            phoneNumber = itemView.findViewById(R.id.nanny_phone_number);
            photo = itemView.findViewById(R.id.nanny_photo);
            mListener = nannyClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }
}

