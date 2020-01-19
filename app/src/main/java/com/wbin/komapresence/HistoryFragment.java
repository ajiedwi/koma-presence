package com.wbin.komapresence;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {


    public HistoryFragment() {
        // Required empty public constructor
    }

    private ArrayList<String> selected = new ArrayList();
    private boolean selecting = false;

    RecyclerView rvHistory;
    RelativeLayout rlManage;
    CardView cvCloseSearch,cvSearch, cvOption, cvSearchBtn, cvSort, cvSave;
    Button btnSelect,btnDelete;
    LinearLayout llBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rlManage = view.findViewById(R.id.rl_manage_history);
        cvSearch = view.findViewById(R.id.cv_search_history);
        cvCloseSearch = view.findViewById(R.id.cv_searchblue);
        cvOption = view.findViewById(R.id.cv_option_history);
        cvSearchBtn = view.findViewById(R.id.cv_btn_search_history);
        cvSort = view.findViewById(R.id.cv_btn_sort_history);
        cvSave = view.findViewById(R.id.cv_btn_save_history);
        btnSelect = view.findViewById(R.id.btn_select_history);
        btnDelete = view.findViewById(R.id.btn_delete_history);
        llBack = view.findViewById(R.id.ll_back_history);

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelect(false);
            }
        });

        cvSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cvOption.setVisibility(View.GONE);
                cvSearch.setVisibility(View.VISIBLE);
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected.clear();
                for (int i = 0; i <10 ; i++) {
                    selected.add(String.valueOf(i));
                    btnDelete.setText("Hapus("+String.valueOf(selected.size())+")");
                    rvHistory.getAdapter().notifyDataSetChanged();
                }
            }
        });

        cvCloseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cvOption.setVisibility(View.VISIBLE);
                cvSearch.setVisibility(View.GONE);
            }
        });

        rvHistory = view.findViewById(R.id.rv_history);
        rvHistory.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvHistory.setAdapter(new HistoryAdapter());

    }

    private void setSelect(boolean i){
        selecting = i;
        if (i){
            rlManage.setVisibility(View.VISIBLE);
            cvOption.setVisibility(View.GONE);
            cvSearch.setVisibility(View.GONE);
        } else {
            selected.clear();
            rlManage.setVisibility(View.GONE);
            cvOption.setVisibility(View.VISIBLE);
            cvSearch.setVisibility(View.GONE);
            btnDelete.setText("Hapus");
        }
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder{

        LinearLayout llBackground;
        TextView tvName,tvDivisi,tvNim,tvTime;
        boolean selectedId = false;
        HistoryViewHolder(@NonNull View itemView)
        {
            super(itemView);
            llBackground = itemView.findViewById(R.id.ll_bg_item_history);
            tvName = itemView.findViewById(R.id.tv_name_item_history);
            tvDivisi = itemView.findViewById(R.id.tv_division_item_history);
            tvNim = itemView.findViewById(R.id.tv_nim_item_history);
            tvTime = itemView.findViewById(R.id.tv_time_item_history);
        }
    }

    class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder>{

        @NonNull
        @Override
        public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_history, parent, false);


            return new HistoryViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final HistoryViewHolder holder, final int position) {

            holder.llBackground.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white));
            holder.tvTime.setTextColor(ContextCompat.getColor(requireContext(),R.color.textBlackPlan));
            holder.tvName.setTextColor(ContextCompat.getColor(requireContext(),R.color.textBlackPlan));
            holder.tvNim.setTextColor(ContextCompat.getColor(requireContext(),R.color.textBlackPlan));
            holder.tvDivisi.setTextColor(ContextCompat.getColor(requireContext(),R.color.textBlackPlan));
            holder.selectedId = false;

            for (int i = 0; i < selected.size(); i++) {
                if (position == Integer.valueOf(selected.get(i))){
                    holder.llBackground.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.lightBlue));
                    holder.tvTime.setTextColor(ContextCompat.getColor(requireContext(),R.color.white));
                    holder.tvName.setTextColor(ContextCompat.getColor(requireContext(),R.color.white));
                    holder.tvNim.setTextColor(ContextCompat.getColor(requireContext(),R.color.white));
                    holder.tvDivisi.setTextColor(ContextCompat.getColor(requireContext(),R.color.white));
                    holder.selectedId = true;
                }
            }

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (!selecting){
                        selected.add(String.valueOf(position));
                        setSelect(true);
                        btnDelete.setText("Hapus("+String.valueOf(selected.size())+")");
                        notifyDataSetChanged();
                    }
                    return false;
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selecting){
                        if (holder.selectedId){
                            selected.remove(String.valueOf(position));
                            if (selected.size()==0)
                                setSelect(false);
                        } else {
                            selected.add(String.valueOf(position));
                        }
                        notifyDataSetChanged();
                        btnDelete.setText("Hapus("+String.valueOf(selected.size())+")");
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

}
