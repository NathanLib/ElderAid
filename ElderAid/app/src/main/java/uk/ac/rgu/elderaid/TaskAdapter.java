package uk.ac.rgu.elderaid;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private ArrayList<Task> taskList;
    private OnTaskListener cOnTaskListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tName;
        public TextView tDate;
        public TextView tTime;
        public TextView tOccurrence;
        public TextView tDescription;
        OnTaskListener onTaskListener;

        public MyViewHolder(View view, OnTaskListener onTaskListener){
            super(view);
            tName = (TextView) view.findViewById(R.id.Task_tvName);
            tDate = (TextView) view.findViewById(R.id.Task_tvDate);
            tTime = (TextView) view.findViewById(R.id.Task_tvTime);
            tDescription = (TextView) view.findViewById(R.id.Task_tvDescription);
            this.onTaskListener = onTaskListener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            onTaskListener.onTaskClick(getAdapterPosition());
        }
    }

    public TaskAdapter(ArrayList<Task> taskList, OnTaskListener onTaskListener){
        this.taskList = taskList;
        this.cOnTaskListener = onTaskListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_item, parent,false);

        return new MyViewHolder(itemView,cOnTaskListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        Task task = taskList.get(position);
        holder.tName.setText(task.getName());
        holder.tDate.setText(task.getDate());
        holder.tTime.setText(task.getTime());
        holder.tDescription.setText(task.getDescription());
    }

    @Override
    public int getItemCount(){return this.taskList.size();}

    public interface OnTaskListener{
        void onTaskClick(int position);
    }
}
