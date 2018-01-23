package com.mitcoe.ishanjoshi.depressionanalyser;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder>{
    private List<FileDataClass> FileDataList;

    public AdapterClass(List<FileDataClass> inputFilePathList) {
        FileDataList = inputFilePathList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FileDataClass D = FileDataList.get(position);
        holder.FileData.setText(D.FilePathData);
    }

    @Override
    public int getItemCount() {
        return FileDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView FileData;
        public ViewHolder(View itemView) {
            super(itemView);
            FileData = (TextView)itemView.findViewById(R.id.itemLayout);
        }
    }
}
