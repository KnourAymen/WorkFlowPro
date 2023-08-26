package com.example.workflowpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RequestsFragment extends Fragment {

    FloatingActionButton addRequestFab;

    public RequestsFragment() {
    }

    public static RequestsFragment newInstance(String param1, String param2) {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requests, container, false);
        addRequestFab = view.findViewById(R.id.add_work_request_fab);
        addRequestFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToWorkRequestsActivity = new Intent(getContext(), WorkRequestsActivity.class);
                startActivity(intentToWorkRequestsActivity);
            }
        });
        return view;
    }
}