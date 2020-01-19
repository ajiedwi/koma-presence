package com.wbin.komapresence;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    Spinner spEvent;
    RecyclerView rvEventHome;
    EventAdapter eventAdapter;
    int active = 0;
    ConstraintLayout clBgSettings;
    LinearLayout llSettings,llLogout, llSession;
    ImageView ivSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spEvent = view.findViewById(R.id.spinner_event_home);
        rvEventHome = view.findViewById(R.id.rv_event_home);
        ivSettings = view.findViewById(R.id.iv_setting_home);
        llSettings = view.findViewById(R.id.ll_settings);
        llSession = view.findViewById(R.id.ll_session);
        llLogout = view.findViewById(R.id.ll_logout);
        clBgSettings = view.findViewById(R.id.cl_bg_setting);
        eventAdapter = new EventAdapter();

        rvEventHome.setLayoutManager(new LinearLayoutManager(requireActivity(),RecyclerView.HORIZONTAL,false));
        rvEventHome.setAdapter(eventAdapter);

        ArrayAdapter<CharSequence> adapterEvent = ArrayAdapter.createFromResource(requireContext(),
                R.array.event_name, R.layout.item_spinner_home);
        adapterEvent.setDropDownViewResource(R.layout.item_spinner_dropdown_home);
        spEvent.setAdapter(adapterEvent);

        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clBgSettings.setVisibility(View.VISIBLE);
                llSettings.setVisibility(View.VISIBLE);
            }
        });

        llSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_home,new ExportFragment()).commit();
            }
        });


        clBgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clBgSettings.setVisibility(View.GONE);
                llSettings.setVisibility(View.GONE);
            }
        });

        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), LoginActivity.class));
                requireActivity().finish();
            }
        });

    }

    class EventViewHolder extends RecyclerView.ViewHolder{

        public TextView tvName;
        public View divider;
        public LinearLayout llMain;

        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_event_home);
            divider = itemView.findViewById(R.id.view_item_event_home);
            llMain = itemView.findViewById(R.id.ll_item_event_home);
        }
    }

    class EventAdapter extends RecyclerView.Adapter<EventViewHolder>{

        @NonNull
        @Override
        public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_event_home, parent, false);

            return new EventViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final EventViewHolder holder, final int position) {
            if (position == active){
                holder.divider.setVisibility(View.VISIBLE);
                holder.tvName.setTextColor(ContextCompat.getColor(requireContext(),R.color.textBlackPlan));
            } else {
                holder.divider.setVisibility(View.INVISIBLE);
                holder.tvName.setTextColor(ContextCompat.getColor(requireContext(),R.color.textDisabled));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    active = position;
                    notifyDataSetChanged();
                }
            });

            if (position == 0){
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(Constants.dpToPx(20),0,Constants.dpToPx(10),0);
                holder.llMain.setLayoutParams(params);
            } else if (position == 5){
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(Constants.dpToPx(10),0,Constants.dpToPx(20),0);
                holder.llMain.setLayoutParams(params);
            }
        }

        @Override
        public int getItemCount() {
            return 6;
        }
    }
}
