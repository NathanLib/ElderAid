package uk.ac.rgu.elderaid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private List<Contact> contactList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUsername;
        private TextView tvPhone;

        public MyViewHolder(View view) {
            super(view);
            tvUsername = (TextView) view.findViewById(R.id.contact_item_username);
            tvPhone = (TextView) view.findViewById(R.id.contact_item_phonenumber);
        }
    }


    public ContactAdapter(List<Contact> contacList) {
        this.contactList = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.tvUsername.setText(contact.getName());
        holder.tvPhone.setText(contact.getPhoneNum());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }
}