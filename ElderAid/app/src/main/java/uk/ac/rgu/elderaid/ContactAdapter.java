package uk.ac.rgu.elderaid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private List<Contact> contactList;
    private OnContactListener cOnContactListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvUsername;
        public TextView tvPhone;
        OnContactListener onContactListener;

        public MyViewHolder(View view, OnContactListener onContactListener) {
            super(view);
            tvUsername = (TextView) view.findViewById(R.id.contact_item_username);
            tvPhone = (TextView) view.findViewById(R.id.contact_item_phonenumber);
            this.onContactListener = onContactListener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onContactListener.onContactClick(getAdapterPosition());
        }
    }


    public ContactAdapter(List<Contact> contactList, OnContactListener onContactListener) {
        this.contactList = contactList;
        this.cOnContactListener = onContactListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_item, parent, false);

        return new MyViewHolder(itemView, cOnContactListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.tvUsername.setText(contact.getName());
        holder.tvPhone.setText(contact.getPhoneNum());
    }

    @Override
    public int getItemCount() {
        return this.contactList.size();
    }

    public interface OnContactListener {
        void onContactClick(int position);
    }
}