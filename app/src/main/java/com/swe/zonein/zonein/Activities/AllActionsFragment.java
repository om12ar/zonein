package com.swe.zonein.zonein.Activities;

import android.app.Notification;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.swe.zonein.zonein.Adapters.ActionAdapter;
import com.swe.zonein.zonein.Adapters.UserAdapter;
import com.swe.zonein.zonein.Controllers.MainController;
import com.swe.zonein.zonein.Controllers.VolleyController;
import com.swe.zonein.zonein.Models.Action;
import com.swe.zonein.zonein.Models.User;
import com.swe.zonein.zonein.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Noha on 4/27/2016.
 */
public class AllActionsFragment extends  android.app.Fragment {

    List<Action> actions;
    ListView actionsListView;
    ActionAdapter actionsAdapter ;


    public static AllActionsFragment newInstance() {
        AllActionsFragment fragment = new AllActionsFragment();
        Bundle args = new Bundle();

        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_view, container, false);

        actionsListView = (ListView) v.findViewById(R.id.list_view);
        actions = new ArrayList<>() ;
        actionsAdapter = new ActionAdapter(actions ,getActivity());

        actionsListView.setAdapter(actionsAdapter);



        final String url = VolleyController.baseURL + "getactions";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsnObject = new JSONObject(response);
                    JSONArray jsonArray = jsnObject.getJSONArray("actionsList");
                    if(jsonArray!=null){

                        for (int i=0 ;i < jsonArray.length() ;i++){
                            try {
                                Action tempAction = new Action(jsonArray.getJSONObject(i));
                                actions.add(tempAction);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                        Log.e("actionsList size", actions.size() + "");

                        actionsAdapter.notifyDataSetChanged();


                        actionsAdapter.notifyDataSetChanged();

                        Log.e("AF", actions.toString());
                        actionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            }
                        });

                    } else {

                    }
                }catch(Exception e){
                    e.printStackTrace();
                    e.getMessage();
                    System.out.println("ERROR Exception!");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR!");
            }
        }){
            @Override
            protected HashMap<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userID", "" + MainController.user.getID());
                return params;
            }

        };


        VolleyController.getInstance().addToRequestQueue(request);

        return v;
    }
}
