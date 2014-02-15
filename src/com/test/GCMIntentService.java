/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

import rs.webnet.gcm.BLog;
import rs.webnet.gcm.Parameters;
import rs.webnet.gcm.ProcessMessage;
import rs.webnet.gcm.ServerUtilities;


/**
 * IntentService responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {

    @SuppressWarnings("hiding")
    private static final String TAG = "GCMIntentService";

    public GCMIntentService() {
        super(Parameters.SENDER_ID);
    }

    @Override
    protected void onRegistered(Context context, String registrationId) {
        BLog.i(TAG, "Device registered: regId = " + registrationId,context);
        ServerUtilities.register(context, registrationId);
    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {
        BLog.i(TAG, "Device unregistered",context);
        if (GCMRegistrar.isRegisteredOnServer(context)) {
            ServerUtilities.unregister(context, registrationId);
        } else {
            Log.i(TAG, "Ignoring unregister callback");
        }
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        BLog.i(TAG, "Received message",context);
        Bundle extras = intent.getExtras();
        ProcessMessage.process(context, extras);
    }

    @Override
    protected void onDeletedMessages(Context context, int total) {
        BLog.i(TAG, "Received deleted messages notification",context);
    }

    @Override
    public void onError(Context context, String errorId) {
        BLog.i(TAG, "Received error: " + errorId,context);
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        BLog.i(TAG, "Received recoverable error: " + errorId,context);
        return super.onRecoverableError(context, errorId);
    }

}
