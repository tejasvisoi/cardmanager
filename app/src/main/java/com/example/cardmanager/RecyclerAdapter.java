package com.example.cardmanager;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<ExampleItem> mExampleList;
    private OnItemClickListener mListener;
    Button delete;
    Button editfield,viewimage;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView itemImage;
        public EditText itemTitle;
        public EditText itemDetail;

        public ViewHolder(View itemView,final OnItemClickListener listener) {
            super(itemView);
            delete=itemView.findViewById(R.id.delete);
            viewimage=itemView.findViewById(R.id.view);
            itemImage = itemView.findViewById(R.id.item_image);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemDetail = itemView.findViewById(R.id.item_detail);
            itemTitle.setEnabled(false);
            itemDetail.setEnabled(false);
            viewimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
    public RecyclerAdapter(ArrayList<ExampleItem> exampleList) {
        mExampleList = exampleList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mylayoutregister, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v,mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ExampleItem currentItem = mExampleList.get(position);
        viewHolder.itemTitle.setText(currentItem.getText1());
        viewHolder.itemDetail.setText(currentItem.getText2());
        viewHolder.itemImage.setImageURI(Uri.parse(currentItem.getImageResource()));
    }
    public void filterList(ArrayList<ExampleItem> filteredList) {
        mExampleList = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}



