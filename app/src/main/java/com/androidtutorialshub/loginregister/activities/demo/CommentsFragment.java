package com.androidtutorialshub.loginregister.activities.demo;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.activities.InventoryCommentsActivity;
import com.androidtutorialshub.loginregister.model.Comment;
import com.androidtutorialshub.loginregister.model.User;
import com.androidtutorialshub.loginregister.sql.CommentSqlCommander;
import com.androidtutorialshub.loginregister.sql.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommentsFragment extends Fragment implements TaskProvider {

    private ListView commentsListView;
    private SimpleAdapter adapter;
    private Button btnCommentSend;
    private EditText etCommentbody;
    private InventoryCommentsActivity mActivity;
    private DatabaseHelper databaseHelper;
    private CommentSqlCommander commentSqlCommander;
    private User authenticatedUser;

    private List<Comment> commentsList;
    private List<Comment> oldCommentsList = new ArrayList<>();

    private PeriodicAsyncTask<GetEquipmentCommentsTask> periodicCommentsRefresh;

    public CommentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = (InventoryCommentsActivity) getActivity();
        databaseHelper = new DatabaseHelper(getContext());
        commentSqlCommander = new CommentSqlCommander(databaseHelper);
        Intent currentIntent = Objects.requireNonNull(getActivity()).getIntent();
        String authenticatedEmail = currentIntent.getStringExtra("EMAIL");
        authenticatedUser = databaseHelper.findUserByEmail(authenticatedEmail);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (periodicCommentsRefresh == null) {
            periodicCommentsRefresh =
                    new PeriodicAsyncTask<>(this);
        }
        periodicCommentsRefresh.start(5000);
    }

    @Override
    public void onPause() {
        super.onPause();
        periodicCommentsRefresh.stop();
    }

    @Override
    public AsyncTask<Void, ?, ?> getTask(int num) {
        return new GetEquipmentCommentsTask();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_equipment_comments, container, false);
        commentsListView = (ListView) rootView.findViewById(R.id.listview_comments);


        btnCommentSend = (Button) rootView.findViewById(R.id.btnCommentSend);
        btnCommentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentBody = etCommentbody.getText().toString();
                if (commentBody.isEmpty()) {
                    Toast.makeText(mActivity, "Please Enter a Comment", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                AddCommentForEquipmentTask addCommentForEquipmentTask = new AddCommentForEquipmentTask();
                addCommentForEquipmentTask.execute(commentBody);
            }
        });
        etCommentbody = (EditText) rootView.findViewById(R.id.etCommentBody);


        return rootView;
    }

    private void updateComments() {

        if (commentsList.size() != oldCommentsList.size()) {
            List<HashMap<String, String>> listValues = new ArrayList<>();
            for (Comment comment : commentsList) {
                HashMap<String, String> commentHashMap = new HashMap<>();
                commentHashMap.put("author_name", comment.getAuthorName());
                commentHashMap.put("created_at", comment.getDateTimeString());
                commentHashMap.put("content", comment.getContent());

                listValues.add(commentHashMap);
            }

            String[] from = new String[]{
                    "author_name", "created_at", "content"
            };
            int[] to = new int[]{
                    R.id.tvAuthorName, R.id.tvDate, R.id.tvCommentBody
            };

            adapter = new SimpleAdapter(getActivity(), listValues,
                    R.layout.fragment_equipment_comments_list_items, from, to);

            commentsListView.setAdapter(adapter);
            setListViewHeightBasedOnChildren(commentsListView);

            oldCommentsList.clear();
            oldCommentsList.addAll(commentsList);
        }
    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LinearLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public String createJSONFromCommentsList(List<Comment> commentsList) throws JSONException {
        final JSONArray comments = new JSONArray();
        for (Comment comment : commentsList) {
            final JSONObject c = new JSONObject();
            c.put("id", comment.getId());
            c.put("equipment_id", comment.getEquipmentID());
            c.put("author_id", comment.getAuthorID());
            c.put("content", comment.getContent());
            c.put("author_name", comment.getAuthorName());
            c.put("created_at", comment.getCreatedAtTimestamp());
            comments.put(c);
        }
        return new JSONObject()
                .put("comments", comments)
                .toString();
    }

    public String createJSONFromComment(Comment comment) throws JSONException {
        final JSONObject c = new JSONObject();
        c.put("id", comment.getId());
        c.put("equipment_id", comment.getEquipmentID());
        c.put("author_id", comment.getAuthorID());
        c.put("content", comment.getContent());
        c.put("author_name", comment.getAuthorName());
        c.put("created_at", comment.getCreatedAtTimestamp());
        JSONObject mainObj = new JSONObject();
        mainObj.put("comment", c);
        return mainObj.toString();
    }


    private class GetEquipmentCommentsTask extends AsyncTask<Void, Void, String> {

        private final String LOG_TAG = GetEquipmentCommentsTask.class.getSimpleName();

        // constructor to solve instantiation problem in PeriodicAsyncTask
        public GetEquipmentCommentsTask() {}

        @Override
        protected String doInBackground(Void... params) {
            List<Comment> commentListFromDb = commentSqlCommander.getCommentsForEquipmentId(mActivity.getSelectedEquipmentId());
            String jsonResponse = null;
            try {
                jsonResponse = createJSONFromCommentsList(commentListFromDb);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.v(LOG_TAG, "GetCommentsForEquipments jsonResponse: " + jsonResponse);
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            if (jsonStr != null) {
                try {
                    List<Comment> commentsList = JsonParser.parseComments(jsonStr);

                    if (commentsList != null) {
                        CommentsFragment.this.commentsList = commentsList;
                        CommentsFragment.this.updateComments();
                    }

                } catch (JsonParser.JsonParserException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private class AddCommentForEquipmentTask extends AsyncTask<String, Void, String> {
        private final String LOG_TAG = AddCommentForEquipmentTask.class.getSimpleName();

        @Override
        protected String doInBackground(String... params) {
            String content = params[0];
            int equipmentId = mActivity.getSelectedEquipmentId();
            Comment newComment = new Comment();
            newComment.setAuthorID(authenticatedUser.getId());
            newComment.setAuthorName(authenticatedUser.getName());
            newComment.setContent(content);
            newComment.setCreatedAtTimestamp(System.currentTimeMillis());
            newComment.setEquipmentID(equipmentId);
            commentSqlCommander.addComment(newComment);
            //final Comment addedComment = commentSqlCommander.getLastAddedComment(equipmentId);
            String jsonResponse = null;
            try {
                jsonResponse = createJSONFromComment(newComment);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.v(LOG_TAG, "AddedComment jsonResponse: " + jsonResponse);
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            if (jsonStr != null) {
                try {
                    Comment comment = JsonParser.parseAddComment(jsonStr);
                    if (comment != null) {
                        Toast.makeText(mActivity, "Comment Posted", Toast.LENGTH_SHORT)
                                .show();
                        CommentsFragment.this.commentsList.add(0, comment);
                        CommentsFragment.this.updateComments();
                        etCommentbody.setText("");
                    }

                } catch (JsonParser.JsonParserException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
                }
            }
        }
    }


}
