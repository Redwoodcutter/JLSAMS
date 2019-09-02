package com.redwoodcutter.haber.Model.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.redwoodcutter.haber.Model.Haberler;
import com.redwoodcutter.haber.R;

import java.util.ArrayList;
import java.util.List;

public class HaberAdapters extends RecyclerView.Adapter<HaberAdapters.MyViewHolder> implements Filterable {

    private Context context;
    private List<Haberler> haberlerList;
    private List<Haberler> haberlerListFiltered;
    private HaberlerAdaptersListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView isim,kategori,url,tarih;
        public MyViewHolder(View view){
            super(view);
            isim = view.findViewById(R.id.isim);
            kategori = view.findViewById(R.id.kategori);
            url = view.findViewById(R.id.url);
            tarih = view.findViewById(R.id.tarih);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onHaberlerSelected(haberlerListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    public HaberAdapters(Context context, List<Haberler> haberlerList, HaberlerAdaptersListener listener) {
        this.context = context;
        this.listener = listener;
        this.haberlerList = haberlerList;
        this.haberlerListFiltered = haberlerList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.haberler_search_list, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Haberler contact = haberlerListFiltered.get(position);
        holder.isim.setText(contact.getIsim());
        holder.kategori.setText(contact.getKategori());
        holder.url.setText(contact.getUrl());
        holder.tarih.setText(contact.getTarih());
    }

    @Override
    public int getItemCount(){
        return haberlerListFiltered.size();
    }

    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    haberlerListFiltered = haberlerList;
                } else {
                    List<Haberler> filteredList = new ArrayList<>();
                    for (Haberler row : haberlerList) {
                        if (row.getIsim().toLowerCase().contains(charString.toLowerCase()) || row.getKategori().contains(charString.toLowerCase()) || row.getTarih().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    haberlerListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = haberlerListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                haberlerListFiltered = (ArrayList<Haberler>) filterResults.values;
                notifyDataSetChanged();

            }
        };
            }
    public interface HaberlerAdaptersListener {
        void onHaberlerSelected(Haberler contact);
    }
    }



