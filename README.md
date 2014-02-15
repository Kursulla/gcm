gcm
===

Small wrapper around Google Cloud Messaging



1. Put in libs gcm.jar
2. Paste complete package  rs.webnet.gcm into your project
3. Copy GCMIntentService_templete from rs.webnet.gcm into your base package, and remove _template sufix.
4. In manifest, you must add following permissions, and be careful with latest permission and read comment above it:

```xml
<!--  Mandatory permissions dor GCM-->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.GET_ACCOUNTS" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="rs.webnet.gcm.permission.C2D_MESSAGE" />
<uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
<!-- Instead of com.test.permission.C2D_MESSAGE put your.package.permission.C2D_MESSAGE -->
<permission android:name="com.test.permission.C2D_MESSAGE" android:protectionLevel="signature" />
<!-- END Mandatory permissions dor GCM-->
```
and above of the closing application tag (</application>) add following:
```xml
<receiver
    android:name="com.google.android.gcm.GCMBroadcastReceiver"
    android:permission="com.google.android.c2dm.permission.SEND" >
    <intent-filter>
        <action android:name="com.google.android.c2dm.intent.RECEIVE" />
        <action android:name="com.google.android.c2dm.intent.REGISTRATION" />
        <category android:name="rs.webnet.gcm" />
     </intent-filter>
</receiver>
<!-- It must be in your package! -->
<service android:name=".GCMIntentService" />
```
