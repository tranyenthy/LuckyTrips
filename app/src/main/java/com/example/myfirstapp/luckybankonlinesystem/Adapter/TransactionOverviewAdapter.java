package com.example.myfirstapp.luckybankonlinesystem.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.luckybankonlinesystem.Class.TransactionOverviewViewHolder;
import com.example.myfirstapp.luckybankonlinesystem.Model.TransactionModel;
import com.example.myfirstapp.luckybankonlinesystem.R;

import java.util.ArrayList;

public class TransactionOverviewAdapter extends RecyclerView.Adapter<TransactionOverviewViewHolder> {

    private final ArrayList<TransactionModel> dataSource;

    public TransactionOverviewAdapter(ArrayList<TransactionModel> dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public TransactionOverviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new TransactionOverviewViewHolder(inflater.inflate(R.layout.transaction_overview_line_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionOverviewViewHolder holder, int position) {
        TransactionModel model = dataSource.get(position);
        holder.setShortName(model.getReceiverName());
        holder.setTransactionId(model.getTransactionID());
        holder.setTransactionValue((int) model.getAmount());
        holder.setTransactionPerformTime(model.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public ArrayList<TransactionModel> getDataSource() {
        return dataSource;
    }
}
