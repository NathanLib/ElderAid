package uk.ac.rgu.elderaid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MedicalConditionAdapter extends RecyclerView.Adapter<MedicalConditionAdapter.MyViewHolder> {

    private List<MedicalCondition> medicalConditionsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCondition;

        public MyViewHolder(View view) {
            super(view);
            tvCondition = (TextView) view.findViewById(R.id.tvMedicalCondition);
        }
    }


    public MedicalConditionAdapter(List<MedicalCondition> medicalConditionsList) {
        this.medicalConditionsList = medicalConditionsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicalcondition_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MedicalCondition medicalCondition = medicalConditionsList.get(position);
        holder.tvCondition.setText(medicalCondition.getName());
    }

    @Override
    public int getItemCount() {
        return medicalConditionsList.size();
    }
}