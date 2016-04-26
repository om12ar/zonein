package com.swe.zonein.zonein.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.swe.zonein.zonein.Adapters.CheckinAdapter;
import com.swe.zonein.zonein.Controllers.VolleyController;
import com.swe.zonein.zonein.Models.CheckIn;
import com.swe.zonein.zonein.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by om12ar on 4/20/16.
 */
public class PlaceFragment extends  android.app.Fragment {
    static Bundle args ;
    List<CheckIn> checkIns;
    ListView listview;
    CheckinAdapter adapter ;

    public PlaceFragment newInstance() {
        PlaceFragment fragment = new PlaceFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_place, container, false);

        final String name = getArguments().getString("placeName");
        final int placeID = getArguments().getInt("placeID");
        final float rating = getArguments().getFloat("rating");
        TextView nameTv = (TextView) v.findViewById(R.id.placeItemNameTV);
        RatingBar rateingbar = (RatingBar) v.findViewById(R.id.placeRatingBar);
        rateingbar.setRating(rating);
        nameTv.setText(name);

        listview = (ListView) v.findViewById(R.id.placeCheckinsList);
        checkIns = new ArrayList<>() ;
        adapter = new CheckinAdapter(checkIns ,getActivity());
        listview.setAdapter(adapter);

        final String url = VolleyController.baseURL + "getCheckinsByPlace";
        Log.e("Place Fragment ", url + " " + placeID);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsnObject = new JSONObject(response);

                    JSONArray jsonArray = jsnObject.getJSONArray("placeList");

                    if (jsonArray != null) {
                        Log.i("Place Fragmrnt ", response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                CheckIn tempCheckin = new CheckIn(jsonArray.getJSONObject(i));
                                checkIns.add(tempCheckin);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR!");
            }
        }) {
            @Override
            protected HashMap<String, String> getParams() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("placeID", "" + placeID);

                return params;
            }

        };

        VolleyController.getInstance().addToRequestQueue(request);


        adapter.notifyDataSetChanged();

        Button checkin = (Button) v.findViewById(R.id.plcChechInBtn);
        checkin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("placeID", placeID);
                CheckInFragment nextFrag = new CheckInFragment();
                nextFrag.setArguments(bundle);
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.view_content, nextFrag)
                        .addToBackStack(null)
                        .commit();
            }
        });









        return v;
    }
}

