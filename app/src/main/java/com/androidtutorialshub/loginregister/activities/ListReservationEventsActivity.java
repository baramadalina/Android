package com.androidtutorialshub.loginregister.activities;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.activities.util.Event;
import com.androidtutorialshub.loginregister.activities.util.Event.EventHash;
import com.androidtutorialshub.loginregister.activities.util.JsonParser;
import com.androidtutorialshub.loginregister.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListReservationEventsActivity extends Fragment {

    private ListView mEventsListView;
    private SimpleAdapter mAdapter;
    private DatabaseHelper databaseHelper;

    public ListReservationEventsActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_reservation_events, container, false);
        this.mEventsListView = (ListView) rootView.findViewById(R.id.listview_events);
        databaseHelper = new DatabaseHelper(this.getContext());
        mEventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> eventHash = (HashMap<String, String>) mAdapter.getItem(position);
                Event ev = EventHash.getEventFromHash(eventHash);

                Intent intent = new Intent(getActivity(), ReservationEventDetailsActivity.class);
                intent.putExtra("event", ev);
                startActivity(intent);
            }
        });


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        ListReservationEventsTask task = new ListReservationEventsTask();
        task.execute();
    }

    private void updateEvents(List<Event> events) {

        List<HashMap<String, String>> listValues = new ArrayList<>();
        for (Event ev : events) {
            HashMap<String, String> eventHashMap =
                    EventHash.getHashFromEvent(ev);
            listValues.add(eventHashMap);
        }

        String[] from = new String[]{
                EventHash.KEY_TITLE,
                EventHash.KEY_DATE,
                EventHash.KEY_TIME
        };


        int[] to = new int[]{R.id.tvEventTitle, R.id.tvEventDate, R.id.tvEventTime};

        mAdapter = new SimpleAdapter(getActivity(), listValues,
                R.layout.fragment_list_item_reservation_event, from, to);

        mEventsListView.setAdapter(mAdapter);

    }

    private class ListReservationEventsTask extends AsyncTask<Void, Void, String> {
        private String LOG_TAG = ListReservationEventsTask.class.getSimpleName();

        @Override
        protected String doInBackground(Void... params) {
            Log.v(LOG_TAG, "GetReservationEvents from database");
            return "";
        }

        @Override
        protected void onPostExecute(String jsonResponse) {
            if (jsonResponse != null) {
                try {
                    List<Event> eventsList = JsonParser.parseReservationEvents(databaseHelper);
                    if (eventsList != null) {
                        updateEvents(eventsList);
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
                }
            }
        }
    }


}
