package com.androidtutorialshub.loginregister.activities.util;

import com.androidtutorialshub.loginregister.model.Comment;
import com.androidtutorialshub.loginregister.model.Reservation;
import com.androidtutorialshub.loginregister.model.User;
import com.androidtutorialshub.loginregister.sql.DatabaseHelper;
import com.androidtutorialshub.loginregister.sql.ReservationSqlCommander;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Madalina Bara on 7/17/2019.
 */
public class JsonParser {


    private static final String API_ERROR = "error";
    private static final String API_ERROR_MESSAGE = "message";
    private static final String API_MESSAGE = "message";
    private static final String API_EVENTS_ARRAY = "events";
    private static final String API_EVENT = "event";
    private static final String API_MEMBERS_ARRAY = "members";
    private static final String API_COMMENTS_ARRAY = "comments";
    private static final String API_COMMENT = "comment";
    private static final String API_FREE_TIMES_ARRAY = "free_times";

    public static class JsonParserException extends Exception {
        public JsonParserException(String detailMessage) {
            super(detailMessage);
        }
    }

    /**
     *
     * @param jsonStr response JSON string
     * @return Event List contains data about the events
     * for the user who made the request
     */
    public static List<Event> parseReservationEvents(String jsonStr) throws JsonParserException {

        List<Event> eventsList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            checkError(jsonObject);

            JSONArray eventsJsonArray = jsonObject.getJSONArray(API_EVENTS_ARRAY);
            for (int i = 0; i < eventsJsonArray.length(); i++) {
                JSONObject eventJson = eventsJsonArray.getJSONObject(i);
                Event event = helperParseEvent(eventJson);
                eventsList.add(event);
            }
            return eventsList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Event> parseReservationEvents(DatabaseHelper databaseHelper) {
        ReservationSqlCommander sqlCommander = new ReservationSqlCommander(databaseHelper);
        List<Reservation> reservationList = sqlCommander.getAllReservations();
        List<Event> eventsList = new ArrayList<>(reservationList.size());

        for (int i = 0; i < reservationList.size(); i++) {
            Event event = helperParseEvent(reservationList.get(i).getTitle(), databaseHelper);
            eventsList.add(event);
        }
        return eventsList;
    }


    /**
     * parses response from /eventsched/v1/priv
     * response example:
     * {
     * "error":false,
     * "members":[
     * {"id":"3","name":"someone","email":"someone@gmail.com",
     * "gender":"M"},
     * {"id":"4","name":"someone2",...}
     * ]
     * }
     *
     * @param jsonStr response JSON string
     * @return Members List contains data about members whom the current user
     * has privileges on
     */
    public static List<Member> parseListOfMembers(String jsonStr) {
        List<Member> membersList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);

            JSONArray membersJsonArray = jsonObject.getJSONArray(API_MEMBERS_ARRAY);
            for (int i = 0; i < membersJsonArray.length(); i++) {
                JSONObject memberJson = membersJsonArray.getJSONObject(i);

                int id = memberJson.getInt(Member.API_ID);
                String name = memberJson.getString(Member.API_NAME);
                String email = memberJson.getString(Member.API_EMAIL);
                String gender = memberJson.getString(Member.API_GENDER);

                membersList.add(
                        new Member(id, name, email, gender)
                );
            }
            return membersList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Event parseCreateEvent(String reservationEventTitle, DatabaseHelper databaseHelper) {
        return helperParseEvent(reservationEventTitle, databaseHelper);

    }

    public static List<Comment> parseComments(String jsonStr) throws JsonParserException {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray commentsJsonArray = jsonObject.getJSONArray(API_COMMENTS_ARRAY);

            List<Comment> commentsList = new ArrayList<>();
            for (int i = 0; i < commentsJsonArray.length(); i++) {
                JSONObject commentJson = commentsJsonArray.getJSONObject(i);

                Comment comment = helperParseComment(commentJson);
                commentsList.add(comment);
            }
            return commentsList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Comment parseAddComment(String jsonStr) throws JsonParserException {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);

            JSONObject commentJson = jsonObject.getJSONObject(API_COMMENT);
            return helperParseComment(commentJson);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<FreeTime> parseFreeTimes(String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);

            JSONArray freeTimesJsonArray = jsonObject.getJSONArray(API_FREE_TIMES_ARRAY);

            List<FreeTime> freeTimesList = new ArrayList<>();
            for (int i = 0; i < freeTimesJsonArray.length(); i++) {
                JSONObject freeTimeJson = freeTimesJsonArray.getJSONObject(i);

                FreeTime freeTime = helperParseFreeTime(freeTimeJson);
                freeTimesList.add(freeTime);
            }
            return freeTimesList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    private static Comment helperParseComment(JSONObject commentJson) throws JSONException {
        int id = commentJson.getInt(Comment.API_ID);
        int equipmentId = commentJson.getInt(Comment.API_EQUIPMENT_ID);
        int authorId = commentJson.getInt(Comment.API_AUTHOR_ID);
        String content = commentJson.getString(Comment.API_CONTENT);
        String authorName = commentJson.getString(Comment.API_AUTHOR_NAME);
        Long createdAtTimestamp = commentJson.getLong(Comment.API_CREATED_AT);

        return new Comment(id, authorId, equipmentId, content,
                authorName, createdAtTimestamp);
    }

    private static FreeTime helperParseFreeTime(JSONObject freeTimeJson) throws JSONException {

        long startTimeStamp = freeTimeJson.getLong(FreeTime.API_START_TIMESTAMP);
        int timesFit = freeTimeJson.getInt(FreeTime.API_TIMES_FIT);

        return new FreeTime(timesFit, startTimeStamp);

    }

    private static Event helperParseEvent(JSONObject eventJson) throws JSONException {

        int id = eventJson.getInt(Event.API_ID);
        int owner_id = eventJson.getInt(Event.API_OWNER_ID);
        int duration = eventJson.getInt(Event.API_DURATION);
        String title = eventJson.getString(Event.API_TITLE);
        String details = eventJson.getString(Event.API_DETAILS);
        String location = eventJson.getString(Event.API_LOCATION);
        long start_time = eventJson.getLong(Event.API_START_TIMESTAMP);
        String reservedByUser = eventJson.getString(Event.API_RESERVED_BY_USER);
        int equipmentId = eventJson.getInt(Event.API_RESERVED_EQUIPMENT_ID);

        return new Event(id, owner_id, title, details,
                location, start_time, duration, reservedByUser, equipmentId);
    }

    private static Event helperParseEvent(String reservationEventTitle, DatabaseHelper databaseHelper) {
        ReservationSqlCommander sqlCommander = new ReservationSqlCommander(databaseHelper);
        final Reservation createdReservation = sqlCommander.getCreatedReservation(reservationEventTitle);
        if (createdReservation == null) {
            return null;
        }
        User userOwner = databaseHelper.findUserByEmail(createdReservation.getUserEmail());
        // int id, int owner_id, String title, String details, String location, long start_timestamp, int duration
        return new Event(createdReservation.getId(), userOwner.getId(), createdReservation.getTitle(), createdReservation.getDetails(),
                createdReservation.getLocation(), Long.parseLong(createdReservation.getStartTime()), Integer.parseInt(createdReservation.getDuration()),
                createdReservation.getUserEmail(), createdReservation.getEquipmentId());
    }

    private static void checkError(JSONObject jsonObject) throws JSONException, JsonParserException {
        if (containsError(jsonObject)) {
            JsonParserException e = new JsonParserException(getErrorMessage(jsonObject));
            throw e;
        }
    }


    private static boolean containsError(JSONObject jsonObject) throws JSONException {
        return jsonObject.getBoolean(API_ERROR);
    }

    private static String getErrorMessage(JSONObject jsonObject) throws JSONException {
        return jsonObject.getString(API_ERROR_MESSAGE);
    }


}
