package com.example.findmynanny.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.findmynanny.MySingleton;
import com.example.findmynanny.R;
import com.example.findmynanny.activities.MapsActivity;
import com.example.findmynanny.adapters.MyAdapter;
import com.example.findmynanny.listeners.NannyClickListener;
import com.example.findmynanny.models.Nanny;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private ArrayList<String> addresses = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button button;
    private Button button2;
    private List<Nanny> nannies;
    private NannyClickListener mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = myview.findViewById(R.id.recyclerView);

        // set the Layout Manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // Parse data from json only first time
        if (nannies == null)
            parseNanniesFromJSON();
        else
        {
            mAdapter = new MyAdapter(nannies, getContext(), mListener);
            recyclerView.setAdapter(mAdapter);
        }

        button = myview.findViewById(R.id.click);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ADDRESSESLIST", (Serializable)addresses);
                args.putSerializable("NAMESLIST", (Serializable)names);
                intent.putExtra("BUNDLE", args);
                startActivity(intent);
            }
        });

        return myview;
    }

    private void parseNanniesFromJSON(){
        String url = "https://my-json-server.typicode.com/gradinariuioana/Find-My-Nanny/nannies";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                nannies = new ArrayList<>();

                for (int index = 0; index < response.length(); index++) {
                    try {
                        Nanny nanny = new Nanny().fromJSON(response.getJSONObject(index));
                        nannies.add(nanny);
                        addresses.add(nanny.getAddress());
                        names.add(nanny.getFirstName() + " " + nanny.getLastName());
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
                mListener = new NannyClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        NannyFragment nannyFragment = new NannyFragment(nannies.get(position));
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        if (fragmentManager.findFragmentByTag("list_fragment") != null)
                            fragmentTransaction.replace(R.id.main_container, nannyFragment, "nanny_fragment").addToBackStack("list_fragment");
                        fragmentTransaction.commit();
                    }};
                mAdapter = new MyAdapter(nannies, getContext(), mListener);
                recyclerView.setAdapter(mAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Volley error " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        MySingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }
}
