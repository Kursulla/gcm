gcm
===

Small wrapper around Google Cloud Messaging



* Put in libs gcm.jar
* Paste complete package  rs.webnet.gcm into your project
* Copy GCMIntentService_templete from rs.webnet.gcm into your base package, and remove _template sufix.
* In your starting activity, just add following line:

```java
 GCM gcm = new GCM(this);
```

* In manifest, you must add following permissions, and be careful with latest permission and read comment above it:

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
* In Parameters.java edit following:
```java
    public static final String SERVER_URL = "http://yurapp.com/gcm";
    public static final String SENDER_ID = "11111111";
```
Where SERVER_URL is URL to the server script that should receive device's registrationId, and SENDER_ID is id you got after registering app on google developers portal (also known as Project Number).








If you want to test application, start following script:
```php

<?php

	$title = "Some title";
	$message = "Message you want to publish to the user";
	$somethingElse = "movie_id";
	$force = true;

// $apiKey = "AIzaSyCHY6YfbhshBPw7IevhBxKKXl3SGOOxbos";//Lude noci
$apiKey = "AI3245er657ugub6tbfyyn6u6gnihkuli";//API Key you got at the Google Developer Portal

// Replace with real device registration IDs 
$registrationIDs = array( "APA91bGYDDkLl1SbdiRMSeToY2pAuT3HAHfWmjDmqktCbLgTpawxZVIf6Ayv1cKQHBywmg43_Fu3jlY_ro-unv75NmCZuEINgPfAuF2wpXr-bch9YEPlFYaAGP2nUo2Z_1yMzJPPJ4sri0EF5oumyd8tnlqbiU_rHg" );




// Message to be sent
// function sendMessage($type,$title,$message){
// Set POST variables
$url = 'https://android.googleapis.com/gcm/send';

$fields = array(
                'registration_ids'  => $registrationIDs,
                'data'              => array( 
					"title" => $title,
					"message" => $message,
					"something_else"=>$somethingElse),
                );

$headers = array( 
                    'Authorization: key=' . $apiKey,
                    'Content-Type: application/json'
                );

// Open connection
$ch = curl_init();

// Set the url, number of POST vars, POST data
curl_setopt( $ch, CURLOPT_URL, $url );

curl_setopt( $ch, CURLOPT_POST, true );
curl_setopt( $ch, CURLOPT_HTTPHEADER, $headers);
curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );

curl_setopt( $ch, CURLOPT_POSTFIELDS, json_encode( $fields ) );

// Execute post
$result = curl_exec($ch);

// Close connection
curl_close($ch);

echo $result;

```

