package uk.ac.rgu.elderaid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.MyViewHolder> {

    private ArrayList<Prescription> prescriptionlist;
    private OnPrescriptionListener cOnPrescriptionListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView pName;
        public TextView pExDate;
        public TextView pLevel;
        OnPrescriptionListener onPrescriptionListener;


    public MyViewHolder(View view, OnPrescriptionListener onPrescriptionListener) {
        super(view);
        pName = (TextView) view.findViewById(R.id.prescription_tvMyMedication1Name);
        pExDate = (TextView) view.findViewById(R.id.prescription_tvMyMedication1ExpDate);
        pLevel = (TextView) view.findViewById(R.id.prescription_tvMyMedication1Quantity);
        this.onPrescriptionListener = onPrescriptionListener;

        view.setOnClickListener(this);
    }


        @Override
        public void onClick(View v) {
            onPrescriptionListener.onPrescriptionClick(getAdapterPosition());
        }
    }

    public PrescriptionAdapter(ArrayList<Prescription> prescriptionlist, OnPrescriptionListener onPrescriptionListener){
        this.prescriptionlist = prescriptionlist;
        this.cOnPrescriptionListener = onPrescriptionListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prescription_list_item, parent, false);

        return new MyViewHolder(itemView,cOnPrescriptionListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Prescription prescription = prescriptionlist.get(position);
        holder.pName.setText(prescription.getName());
        holder.pExDate.setText(prescription.getExDate());
        holder.pLevel.setText(prescription.getLevel());
    }

    @Override
    public int getItemCount() {
        return this.prescriptionlist.size();
    }

    public interface OnPrescriptionListener {
        void onPrescriptionClick(int position);
    }

}
