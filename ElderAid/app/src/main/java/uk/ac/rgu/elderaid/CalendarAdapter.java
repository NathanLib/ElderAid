package uk.ac.rgu.elderaid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.MyViewHolder> {

    private List<Event> eventList;
    private OnEventListener eventListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView eventTitle;
        public TextView eventLocation;
        OnEventListener eventListener;

        public MyViewHolder(@NonNull View view, OnEventListener eventListener) {
            super(view);
            eventTitle = (TextView) view.findViewById(R.id.calendar_item_eventName);
            eventLocation = (TextView) view.findViewById(R.id.calendar_item_location);
            this.eventListener = eventListener;

            view.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            eventListener.onEventClick(getAdapterPosition());
        }
    }

    public CalendarAdapter(List<Event> eventList, OnEventListener eventListener) {
        this.eventList = eventList;
        this.eventListener = eventListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_list_item, parent, false);

        return new MyViewHolder(itemView, eventListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.eventTitle.setText(event.getTitle());
        holder.eventLocation.setText(event.getLocation());
    }

    @Override
    public int getItemCount() {
        return this.eventList.size();
    }

    public interface OnEventListener {
        void onEventClick(int position);
    }


}
