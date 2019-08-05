package com.androidtutorialshub.loginregister.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.androidtutorialshub.loginregister.R;

import java.util.ArrayList;
import java.util.List;

public class ActivitySendEmail extends AppCompatActivity {

    private Button btn_mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email_screen);



        btn_mail = findViewById(R.id.buttonMail);

        btn_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivitySendEmail shareBuilder = new ActivitySendEmail();

                shareBuilder.shareByEmail("Your subject","");
                Intent intent=shareBuilder.build("Sending email ",view.getContext());
                startActivity(intent);
            }
        });
    }


        public static ActivitySendEmail create() {
            ActivitySendEmail sendEmail = new ActivitySendEmail();
            return sendEmail;
        }

        public ActivitySendEmail shareByEmail(String subject, String text) {
            return shareByEmail(subject, text, null);
        }

        public ActivitySendEmail shareByEmail(String subject, String text, @Nullable String sendToEMail) {

            Intent emailIntent;
            if (sendToEMail != null) {
                emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", sendToEMail, null));
            } else {
                emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
            }

            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, text);

            _intents.add(emailIntent);
            return this;
        }

    //method to share by sms
        public ActivitySendEmail shareBySms(String textBody) {
            return shareBySms(textBody, null);
        }

        public ActivitySendEmail shareBySms(String textBody, @Nullable String sendToNumber) {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            String uri = "sms:";
            if (sendToNumber != null) {
                uri += sendToNumber;
            }

            smsIntent.setData(Uri.parse(uri));
            smsIntent.putExtra("sms_body", textBody);

            _intents.add(smsIntent);

            return this;
        }


       //method to share by messenger

        public ActivitySendEmail shareByMessanger(String text) {
            Intent messangerIntent = new Intent(Intent.ACTION_SEND);
            messangerIntent.putExtra(Intent.EXTRA_TEXT, text);
            messangerIntent.setType("text/plain");
            messangerIntent.setPackage("com.facebook.orca");

            _intents.add(messangerIntent);

            return this;
        }

        public Intent build(final String chooserTitle, final Context context) {
            if (_intents.isEmpty()) {
                throw new IllegalStateException("Please add shares!");
            }

            final PackageManager packageManager = context.getPackageManager();

            ArrayList<LabeledIntent> extraIntents = new ArrayList<>(_intents.size() * 2);
            Intent mainIntent = null;

            for (int i = 0; i < _intents.size(); ++i) {
                Intent intent = _intents.get(i);

                List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent, 0);
                if (resolveInfoList == null) {
                    continue;
                }
                if (mainIntent == null && resolveInfoList.isEmpty() == false) {
                    mainIntent = intent;

                    continue;
                }
                for (ResolveInfo info : resolveInfoList) {

                    String packageName = info.activityInfo.packageName;
                    intent.setComponent(new ComponentName(packageName, info.activityInfo.name));
                    extraIntents.add(new LabeledIntent(intent, packageName, info.loadLabel(packageManager), info.icon));
                }
            }

            if (mainIntent == null) {
                mainIntent = _intents.get(0);
                Log.e(ActivitySendEmail.class.getSimpleName(), "No app can't handle such share request");
            }


            final Intent chooser = Intent.createChooser(mainIntent, chooserTitle);
            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents.toArray(new LabeledIntent[extraIntents.size()]));
            return chooser;
        }

        private ArrayList<Intent> _intents = new ArrayList<>(8);
    }

















