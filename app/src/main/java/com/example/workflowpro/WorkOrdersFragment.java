package com.example.workflowpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WorkOrdersFragment extends Fragment {

    FloatingActionButton addWorkOrderFab;

    public WorkOrdersFragment() {
    }

    public static WorkOrdersFragment newInstance(String param1, String param2) {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work_orders, container, false);

        addWorkOrderFab = view.findViewById(R.id.add_work_order_fab);
        addWorkOrderFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToWorkOrdersActivity = new Intent(getContext(), WorkOrdersActivity.class);
                startActivity(intentToWorkOrdersActivity);
            }
        });
        return view;
    }
}