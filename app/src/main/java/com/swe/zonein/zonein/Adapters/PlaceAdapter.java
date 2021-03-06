package com.swe.zonein.zonein.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.swe.zonein.zonein.Activities.PlaceFragment;
import com.swe.zonein.zonein.Controllers.MainController;
import com.swe.zonein.zonein.Controllers.Requests;
import com.swe.zonein.zonein.Controllers.VolleyController;
import com.swe.zonein.zonein.Models.Place;
import com.swe.zonein.zonein.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by om12ar on 4/20/16.
 */
public class PlaceAdapter extends BaseAdapter{
    final String TAG = "Place Adapter";
    List<Place> list;
    Context context;
    boolean buttonStatus = true;
    public PlaceAdapter(List<Place> list, Context context) {
        this.list = list;
        this.context = context;
    }
    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder = new Holder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.place_item, null);

        TextView pName = (TextView) convertView.findViewById(R.id.placeItemNameTV);
        TextView pDesc = (TextView) convertView.findViewById(R.id.placeDescTV);
        RatingBar pRating = (RatingBar) convertView.findViewById(R.id.placeRatingBar);
        final Button pSave = (Button) convertView.findViewById(R.id.placeSaveBtn);

        holder.placeName = pName;
        holder.placeDesc = pDesc;
        holder.rating = pRating;
        holder.save=pSave ;
        holder.rating.setEnabled(false);
        int place = list.get(position).getID();
        boolean isSaved  ;
        isSaved = MainController.user.isPlaceSaved(place);
        if (isSaved == true) {
            holder.save.setText("UnSave");
        }
        else{
            holder.save.setText("Save");
        }

        holder.placeName.setText(list.get(position).getName());
        holder.placeDesc.setText(list.get(position).getDescription());
        holder.rating.setRating(list.get(position).getRating());

        pSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                final int place = list.get(position).getID();
                final boolean isSaved;
                isSaved = MainController.user.isPlaceSaved(place);
                String fn = "";
                if (isSaved) {
                    fn = "unsavePlace";
                } else {
                    fn = "saveplace";

                }

                final String url = VolleyController.baseURL + fn;

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsnObject = new JSONObject(response);
                            Log.i(TAG, url + " " + jsnObject.toString());
                            if (jsnObject != null) {

                                if (isSaved) {
                                    pSave.setText("Save");
                                    MainController.user.unSavePlace(list.get(position).getID());

                            } else {
                                    pSave.setText("unSave");
                                    MainController.user.SavePlace(list.get(position).getID());
                            }

                            } else {

                        }
                        } catch (Exception e) {
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
                }) {
                    @Override
                    protected HashMap<String, String> getParams() {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("userID", "" + MainController.user.getID());
                        params.put("placeID", "" + place);
                        Log.e("PLACE Fragment url ", params.toString());
                        return params;
                    }

                };


                VolleyController.getInstance().addToRequestQueue(request);

                if(isSaved == false){

                    String actionType = "saveplace";
                    String description = "You saved " + list.get(position).getName();

                    Requests addaction = new Requests();
                    addaction.addAction(actionType, description, place);
                }

            }
        });

        pName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("allplaces :", "ON CLICK ");
                Bundle bundle = new Bundle();
                TextView name = (TextView) v.findViewById(R.id.placeItemNameTV);
     //           bundle.putString("placeName", name.getText().toString());
                bundle.putString("placeName", list.get(position).getName());
                bundle.putInt("placeID", list.get(position).getID());
                bundle.putFloat("rating", list.get(position).getRating());
                bundle.putString("desc", list.get(position).getDescription());
                bundle.putInt("numOfCheckins", list.get(position).getNumberOfCheckIn());
                Log.e("PLACEE ADAPTER", bundle.toString());
                PlaceFragment nextFrag = new PlaceFragment();
                nextFrag.setArguments(bundle);
                ((Activity) context).getFragmentManager().beginTransaction()
                        .replace(R.id.view_content, nextFrag)
                        .addToBackStack(null)
                        .commit();

            }
        });
        return convertView;
    }


    public void setButtons(boolean b) {
        buttonStatus = b ;
    }


    class Holder {
        TextView placeName;
        TextView placeDesc;
        RatingBar rating ;
        Button save;
    }

}

