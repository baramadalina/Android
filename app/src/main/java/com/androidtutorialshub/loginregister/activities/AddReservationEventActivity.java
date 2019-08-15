package com.androidtutorialshub.loginregister.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.activities.demo.DateManager;
import com.androidtutorialshub.loginregister.activities.demo.Event;
import com.androidtutorialshub.loginregister.activities.demo.FilterDialog;
import com.androidtutorialshub.loginregister.activities.demo.FreeTime;
import com.androidtutorialshub.loginregister.activities.demo.JsonParser;
import com.androidtutorialshub.loginregister.activities.demo.Member;
import com.androidtutorialshub.loginregister.activities.demo.MenuActivity;
import com.androidtutorialshub.loginregister.model.BusyTimeSlot;
import com.androidtutorialshub.loginregister.model.Equipment;
import com.androidtutorialshub.loginregister.model.Reservation;
import com.androidtutorialshub.loginregister.model.ReservationInterval;
import com.androidtutorialshub.loginregister.model.User;
import com.androidtutorialshub.loginregister.sql.DatabaseHelper;
import com.androidtutorialshub.loginregister.sql.EquipmentSqlCommander;
import com.androidtutorialshub.loginregister.sql.ReservationSqlCommander;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AddReservationEventActivity extends MenuActivity {

    public String timeFromStr, timeToStr, dateFromStr, dateToStr;

    private Button btnFilterOptions, btnCreate, btnCancel, btnRefreshSuggestions;
    private EditText etEventTitle, etEventLocation, etEventDetails,
            etDurationHours, etDurationMinutes;
    private ViewGroup containerMembers;
    private Spinner spinTime;
    private List<Member> mMembers;
    private List<Member> mSelectedMembers;
    private Long mSelectedUnixTimeStamp;
    private String eventTitle, eventLocation, eventDetails;
    private int durationHours, durationMinutes, durationTotalMinutes;
    private String authenticatedEmail;
    private List<Long> freeTimes;
    private Spinner dropdownEquipment;
    // variable for equipments listing
    private DatabaseHelper databaseHelper;
    private EquipmentSqlCommander equipmentSqlCommander;
    private ReservationSqlCommander reservationSqlCommander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent currentIntent = getIntent();
        authenticatedEmail = currentIntent.getStringExtra("EMAIL");
        setContentView(R.layout.activity_reservation_add_new);

        btnFilterOptions = (Button) findViewById(R.id.btnFilterOptions);

        dropdownEquipment = (Spinner) findViewById(R.id.spinner_dropDown_equipments);

        databaseHelper = new DatabaseHelper(this);
        equipmentSqlCommander = new EquipmentSqlCommander(databaseHelper);
        reservationSqlCommander = new ReservationSqlCommander(databaseHelper);
        final List<Equipment> inventoryList = equipmentSqlCommander.getAllEquipments();
        List<String> equipmentNamesList = getAllEquipmentsDetails(inventoryList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_all_items, equipmentNamesList);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_selected_item);
        dropdownEquipment.setAdapter(adapter);

        btnFilterOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterDialog filterDialog = new FilterDialog(AddReservationEventActivity.this);
                filterDialog.show();
            }
        });

        btnCreate = (Button) findViewById(R.id.btnEventCreate);
        btnCancel = (Button) findViewById(R.id.btnEventCancel);
        btnRefreshSuggestions = (Button) findViewById(R.id.btnRefreshSuggestions);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    CreateReservationEventTask createReservationEventTask = new CreateReservationEventTask();
                    createReservationEventTask.execute();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRefreshSuggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshSuggestions();
            }
        });

        etEventTitle = (EditText) findViewById(R.id.etEventTitle);
        etEventLocation = (EditText) findViewById(R.id.etEventLocation);
        etEventDetails = (EditText) findViewById(R.id.etEventDetails);
        etDurationHours = (EditText) findViewById(R.id.etDurationHours);
        //etDurationMinutes = (EditText) findViewById(R.id.etDurationMinutes);

        spinTime = (Spinner) findViewById(R.id.spinTime);

        containerMembers = (ViewGroup) findViewById(R.id.containerMembers);
        if (containerMembers != null) {
            containerMembers.setVisibility(View.GONE);
        }

        mSelectedMembers = new ArrayList<>();
        GetListOfMembersTask getListOfMembersTask = new GetListOfMembersTask();
        getListOfMembersTask.execute();
    }

    public void refreshSuggestions() {
        GregorianCalendar cal = new GregorianCalendar();
        long currentTimeMillis = System.currentTimeMillis();
        cal.setTimeInMillis(currentTimeMillis);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);

        Date dateFrom, dateTo;
        if (dateFromStr != null && !dateFromStr.isEmpty()) {
            try {
                dateFrom = formatter.parse(dateFromStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            dateFrom = cal.getTime();
            dateFromStr = formatter.format(dateFrom);
        }

        if (dateToStr != null && !dateToStr.isEmpty()) {
            try {
                dateTo = formatter.parse(dateToStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            cal.add(Calendar.DAY_OF_MONTH, 7);
            dateTo = cal.getTime();
            dateToStr = formatter.format(dateTo);
        }

        if (timeFromStr == null || timeFromStr.isEmpty()) {
            timeFromStr = "00:00:00";
        }

        if (timeToStr == null || timeToStr.isEmpty()) {
            timeToStr = "23:59:00";
        }

        calcDuration();
        if (durationTotalMinutes <= 0) {
            Toast.makeText(this, "Please Choose a suitable duration", Toast.LENGTH_SHORT).show();
            return;
        }

        DisplayAvailableTimeSlotsTask displayAvailableTimeSlotsTask = new DisplayAvailableTimeSlotsTask();
        displayAvailableTimeSlotsTask.execute();

    }

    private boolean validateFields() {
        eventTitle = etEventTitle.getText().toString();
        if (eventTitle.isEmpty()) {
            Toast.makeText(this, "Please Fill in Title", Toast.LENGTH_SHORT).show();
            return false;
        }

        eventLocation = etEventLocation.getText().toString();
        if (eventLocation.isEmpty()) {
            Toast.makeText(this, "Please Fill in Location", Toast.LENGTH_SHORT).show();
            return false;
        }

        eventDetails = etEventDetails.getText().toString();
        if (eventDetails.isEmpty()) {
            Toast.makeText(this, "Please Fill in Reservation event details", Toast.LENGTH_SHORT).show();
            return false;
        }

        calcDuration();

        if (durationTotalMinutes <= 0) {
            Toast.makeText(this, "Please Choose a suitable duration", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mSelectedUnixTimeStamp == null) {
            Toast.makeText(this, "Please Choose a suitable Time", Toast.LENGTH_SHORT).show();
            return false;
        }

        // TODO fix this after dynamic fill
//        mSelectedUnixTimeStamp = mSelectedTimeStamp;
        return true;
    }

    private void calcDuration() {
        String durationHoursStr = etDurationHours.getText().toString();
        durationHours = durationHoursStr.isEmpty() ? 0 : Integer.parseInt(durationHoursStr);

        String durationMinutesStr = "0"; //etDurationMinutes.getText().toString();
        durationMinutes = durationMinutesStr.isEmpty() ? 0 : Integer.parseInt(durationMinutesStr);

        durationTotalMinutes = durationMinutes + durationHours * 60;
    }

    private void fillFreeTimesSpinner(List<FreeTime> freeTimesList) {
        this.freeTimes = new ArrayList<>();
        DateManager dateManager = new DateManager();

        ArrayList<String> arraySpinner = new ArrayList<>();
        for(FreeTime freeTime: freeTimesList) {
            long timeStamp = freeTime.getStartTimeStamp();
            this.freeTimes.add(timeStamp);
            arraySpinner.add(dateManager.getReadableDayDateTimeString(timeStamp));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_all_items, arraySpinner);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_selected_item);
        spinTime.setAdapter(adapter);
        spinTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AddReservationEventActivity.this.mSelectedUnixTimeStamp =
                        AddReservationEventActivity.this.freeTimes.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void displayMembersList() {
        mSelectedMembers = new ArrayList<>();

        if (mMembers.size() == 0) {
            containerMembers.setVisibility(View.GONE);
        } else {
            containerMembers.setVisibility(View.VISIBLE);
            MembersAdapter adapter = new MembersAdapter(this, R.layout.list1_item_member, mMembers);
            ListView membersListView = (ListView) findViewById(R.id.listview_members);
            if (membersListView != null) {
                membersListView.setAdapter(adapter);
            }
        }
    }

    private List<String> getAllEquipmentsDetails(List<Equipment> inventoryList) {
        List<String> equipmentNamesList = new ArrayList<>(inventoryList.size());
        for (Equipment equipment : inventoryList) {
            equipmentNamesList.add(equipment.getName());
        }
        return equipmentNamesList;
    }


    private class MembersAdapter extends ArrayAdapter<Member> {
        private ArrayList<MemberHolder> mMembersList;
        private final String LOG_TAG = MembersAdapter.class.getSimpleName();

        public MembersAdapter(Context context, int resource, List<Member> members) {
            super(context, resource, members);
            mMembersList = new ArrayList<>();
            for (Member m : members) {
                mMembersList.add(new MemberHolder(m, false));
            }
        }

        private class MemberHolder {
            Member member;
            boolean isChecked;

            public MemberHolder(Member member, boolean isChecked) {
                this.member = member;
                this.isChecked = isChecked;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            CheckBox cbMember = null;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                if (inflater != null) {
                    convertView = inflater.inflate(R.layout.list1_item_member, null, false);
                }

                if (convertView != null) {
                    cbMember = (CheckBox) convertView.findViewById(R.id.cbMember);
                    convertView.setTag(cbMember);
                    cbMember.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CheckBox cbMember = (CheckBox) v;
                            MemberHolder memberHolder = (MemberHolder) cbMember.getTag();
                            if (cbMember.isChecked()) {
                                mSelectedMembers.add(memberHolder.member);
                                memberHolder.isChecked = true;
                            } else {
                                mSelectedMembers.remove(memberHolder.member);
                                memberHolder.isChecked = false;
                            }
                            Log.v(LOG_TAG, "Selected Members: " + mSelectedMembers.toString());
                        }
                    });
                }
            } else {
                cbMember = (CheckBox) convertView.getTag();
            }

            MemberHolder memberHolder = this.mMembersList.get(position);
            Member member = memberHolder.member;
            if (cbMember != null) {
                cbMember.setText(String.format("%s", member.getEmail()));
                cbMember.setTag(memberHolder);
                cbMember.setChecked(memberHolder.isChecked);
            }
            return convertView;
        }
    }

    private class DisplayAvailableTimeSlotsTask extends AsyncTask<Void, Void, String> {
        private final String LOG_TAG = DisplayAvailableTimeSlotsTask.class.getSimpleName();

        private final String QUERY_MEMBERS = "members";
        private final String QUERY_DATE_START = "date_start";
        private final String QUERY_DATE_END = "date_end";
        private final String QUERY_TIME_START = "time_start";
        private final String QUERY_TIME_END = "time_end";
        private final String QUERY_DURATION = "duration";


        @Override
        protected String doInBackground(Void... params) {
            String membersStr = extractMemberId();
            HashMap<String, String> queryParams = new HashMap<>();
            if (!membersStr.isEmpty()) {
                queryParams.put(QUERY_MEMBERS, membersStr);
            }
            queryParams.put(QUERY_DATE_START, dateFromStr);
            queryParams.put(QUERY_DATE_END, dateToStr);
            queryParams.put(QUERY_TIME_START, timeFromStr);
            queryParams.put(QUERY_TIME_END, timeToStr);
            queryParams.put(QUERY_DURATION, Integer.toString(durationTotalMinutes));

            int equipmentId = getSelectedEquipmentId();
            List<Reservation> listReservationsForEquipment = reservationSqlCommander.getReservationsForEquipmentId(equipmentId);
            List<BusyTimeSlot> busyTimeSlotList = new ArrayList<>(listReservationsForEquipment.size());
            for (Reservation reservation : listReservationsForEquipment) {
                busyTimeSlotList.add(new BusyTimeSlot(reservation.getStartTime(), reservation.getDuration()));
            }
            // extract from busyTimeSlots the available time slots
            List<ReservationInterval> freeTimesIntervals = getFreeTimesIntervals(busyTimeSlotList, durationHours);

            String jsonResponse = null;
            try {
                jsonResponse = createJSONFromFreeTimeSlotsList(freeTimesIntervals);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.v(LOG_TAG, "RefreshTimes jsonResponse: " + jsonResponse);

            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String jsonResponse) {
            if (jsonResponse != null) {
                try {
                    List<FreeTime> freeTimesList = JsonParser.parseFreeTimes(jsonResponse);
                    if (freeTimesList != null) {
                        fillFreeTimesSpinner(freeTimesList);
                        Toast.makeText(AddReservationEventActivity.this,
                                "Suggested times refreshed", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(AddReservationEventActivity.this, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public List<ReservationInterval> getFreeTimesIntervals(final List<BusyTimeSlot> busyTimeSlotList, int preferredDurationHours) {
        Log.i("Preferred duration:h->", String.valueOf(preferredDurationHours));
        List<ReservationInterval> reservationIntervalList = new ArrayList<>(busyTimeSlotList.size());
        for (BusyTimeSlot busyTimeSlot : busyTimeSlotList) {
            final DateTime startDate = convertFromTimestampToDate(busyTimeSlot.getStartTime());
            int durationInMinutes = Integer.parseInt(busyTimeSlot.getDuration());
            long timestampEnd = Long.parseLong(busyTimeSlot.getStartTime()) + durationInMinutes * 60L;
            final DateTime endDate = convertFromTimestampToDate(String.valueOf(timestampEnd));
            ReservationInterval reservationInterval = new ReservationInterval(startDate, endDate);
            reservationIntervalList.add(reservationInterval);
        }
        Collections.sort(reservationIntervalList);
        final List<ReservationInterval> notReservedList = new ArrayList<>();

        if (reservationIntervalList.isEmpty()) {
            return getAllDayAvailability(preferredDurationHours);
        }
        for (ReservationInterval reservationInterval : reservationIntervalList) {
            Log.i("ReservationInterval:", reservationInterval.toString());
            DateTime aux = new DateTime(reservationInterval.getStartDate().toString());
            DateTime dateBeforeReservation = getFirstDateOfToday(aux);
            while (dateBeforeReservation.isBefore(reservationInterval.getStartDate())) {
                Log.i("Free startTime:", dateBeforeReservation.toString());
                DateTime endAvailableDate = generateDateInRange(dateBeforeReservation, preferredDurationHours);
                Log.i("Free endTime:", dateBeforeReservation.toString());
                notReservedList.add(new ReservationInterval(dateBeforeReservation, endAvailableDate));
                dateBeforeReservation = endAvailableDate;
            }
        }
        return notReservedList;
    }

    public DateTime getFirstDateOfToday(final DateTime todaySomeDate) {
        DateTime date = new DateTime(todaySomeDate.toString());
        date.withTimeAtStartOfDay();
        return date;
    }

    public DateTime generateDateInRange(final DateTime date, int preferredDurationHours) {
        long timestampForNextDate = date.getMillis() + preferredDurationHours * 3600 * 1000;
        return new DateTime(convertFromTimestampToDate(String.valueOf(timestampForNextDate)).toString());
    }

    private DateTime convertFromTimestampToDate(final String timestampToConvert) {
        Timestamp timestamp = new Timestamp(Long.parseLong(timestampToConvert) * 1000L);
        return new DateTime(timestamp.getTime());
    }

    private List<ReservationInterval> getAllDayAvailability(int preferredDurationHours) {
        List<ReservationInterval> restOfTheDayAvailability = new ArrayList<>();
        //Timestamp stamp = new Timestamp(System.currentTimeMillis());
        Timestamp timestamp = new Timestamp(1564635600000L); //today 8:00 morning 1 August 2019
        DateTime date = new DateTime(timestamp.getTime());
        DateTime todayStartAvailability = new DateTime(date.toString());
        DateTime todayEndAvailability = todayStartAvailability.plusHours(12);
        DateTime startTimeInterval = new DateTime(todayStartAvailability);
        DateTime endTimeInterval = new DateTime(todayStartAvailability.plusHours(preferredDurationHours));
        while (startTimeInterval.isBefore(todayEndAvailability)) {
            restOfTheDayAvailability.add(new ReservationInterval(startTimeInterval, endTimeInterval));
            startTimeInterval = endTimeInterval;
            endTimeInterval = startTimeInterval.plusHours(preferredDurationHours);
        }
        return restOfTheDayAvailability;
    }

    public String createJSONFromFreeTimeSlotsList(List<ReservationInterval> freeTimeSLots) throws JSONException {
        final JSONArray freeTimesArray = new JSONArray();
        for(ReservationInterval interval: freeTimeSLots) {
            final JSONObject timeSLot = new JSONObject();
            Timestamp startTimeStamp = new Timestamp(interval.getStartDate().getMillis());
            timeSLot.put("start_time", startTimeStamp.getTime());
            timeSLot.put("times_fit", startTimeStamp.getTime());
            freeTimesArray.put(timeSLot);
        }
        return new JSONObject()
                .put("free_times", freeTimesArray)
                .toString();
    }

    private class GetListOfMembersTask extends AsyncTask<Void, Void, String> {
        private final String LOG_TAG = GetListOfMembersTask.class.getSimpleName();

        @Override
        protected String doInBackground(Void... params) {
            String jsonResponse = null;
            final User userLogged = databaseHelper.findUserByEmail(authenticatedEmail);
            if (userLogged == null) {
                jsonResponse = "{\n" +
                        "\t\"error\": false,\n" +
                        "\t\"members\": [{\n" +
                        "\t\t\"id\": 2,\n" +
                        "\t\t\"name\": \"someone\",\n" +
                        "\t\t\"email\": \"test@gmail.com\",\n" +
                        "\t\t\"gender\": \"M\"\n" +
                        "\t}]\n" +
                        "}";
                return jsonResponse;
            }
            try {
                final JSONObject member = new JSONObject();
                member.put("id", userLogged.getId());
                member.put("name", userLogged.getName());
                member.put("email", userLogged.getEmail());
                member.put("gender", "M");
                JSONArray ja = new JSONArray();
                ja.put(member);
                jsonResponse = new JSONObject()
                        .put("members", ja)
                        .toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.v(LOG_TAG, "ListOfUsersMember jsonResponse: " + jsonResponse);

            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String jsonResponse) {
            if (jsonResponse != null) {
                try {
                    List<Member> membersList = JsonParser.parseListOfMembers(jsonResponse);
                    if (membersList != null) {
                        AddReservationEventActivity.this.mMembers = membersList;
                        AddReservationEventActivity.this.displayMembersList();
                    }
                } catch (Exception e) {
                    Toast.makeText(AddReservationEventActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                }
            }
        }
    }

    private class CreateReservationEventTask extends AsyncTask<Void, Void, String> {
        private final String LOG_TAG = CreateReservationEventTask.class.getSimpleName();

        @Override
        protected String doInBackground(Void... params) {
            Log.i("start_time: ", mSelectedUnixTimeStamp.toString());
            Log.i("duration: ", Integer.toString(durationTotalMinutes));
            final Reservation reservation = new Reservation();
            reservation.setTitle(eventTitle);
            reservation.setLocation(eventLocation);
            reservation.setDetails(eventDetails);
            reservation.setStartTime(mSelectedUnixTimeStamp.toString());
            reservation.setDuration(Integer.toString(durationTotalMinutes));
            final String memberId = extractMemberId();
            if (!memberId.isEmpty()) {
                User selectedUser = databaseHelper.findUserById(memberId);
                reservation.setUserEmail(selectedUser.getEmail());
            }
            int equipmentId = getSelectedEquipmentId();
            reservation.setEquipmentId(equipmentId);
            reservationSqlCommander.addReservation(reservation);
            Log.v(LOG_TAG, "CreateReservationEvent payload: " + reservation);
            return eventTitle;
        }

        @Override
        protected void onPostExecute(String jsonResponse) {
            if (jsonResponse == null) {
                return;
            }
            try {
                Event event = JsonParser.parseCreateEvent(jsonResponse, databaseHelper);
                if (event != null) {
                    Toast.makeText(AddReservationEventActivity.this, "Reservation event created successfully!", Toast.LENGTH_LONG)
                            .show();

                    Intent intent = new Intent(AddReservationEventActivity.this,
                            ReservationEventDetailsActivity.class);
                    intent.putExtra("event", event);
                    startActivity(intent);

                    finish();
                }
            } catch (Exception e) {
                Toast.makeText(AddReservationEventActivity.this, e.getMessage(), Toast.LENGTH_LONG);
            }
        }

    }

    private String extractMemberId() {
        StringBuilder buffer = new StringBuilder();
        boolean first = true;
        for (Member member : mSelectedMembers) {
            if (first) {
                first = false;
            } else {
                buffer.append(',');
            }

            buffer.append(member.getId());
        }
        return buffer.toString();

    }

    private int getSelectedEquipmentId() {
        return (int) dropdownEquipment.getSelectedItemId() + 1;
    }
}
