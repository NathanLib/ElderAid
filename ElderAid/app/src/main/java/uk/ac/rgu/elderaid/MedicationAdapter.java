package uk.ac.rgu.elderaid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MyViewHolder> {

    private List<Medication> medicationsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMedication;

        public MyViewHolder(View view) {
            super(view);
            tvMedication = (TextView) view.findViewById(R.id.tvMedication);
        }
    }


    public MedicationAdapter(List<Medication> medicationsList) {
        this.medicationsList = medicationsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medication_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Medication medication = medicationsList.get(position);
        holder.tvMedication.setText(medication.getName());
    }

    @Override
    public int getItemCount() {
        return medicationsList.size();
    }
}