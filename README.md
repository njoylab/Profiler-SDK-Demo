# 42matters Profiler SDK for Android

42matters profiler sdk (beta) delivers insights about Android users based on the apps they have. The sdk has a tiny footprint under 40k, and the integration requires only several lines of code. There’s also no dubious permissions required other than accessing internet and networks states.

## Demo

This repo is a demo app printing the debug info from the profiler sdk. The app is built with [Android Studio](https://developer.android.com/sdk/installing/studio.html).

## How to integrate in your app

### Prerequisites
* Your app has a minimum sdk level of 10 (Android 2.3.3+).
* You registered your app on the https://42matters.com/profiler/, and you have its app id.
* The app's status is `ON`.

### Integrate the library

1. Download the [latest jar (1.0.1-beta)](https://s3.amazonaws.com/profiler.42matters.com/42matters-profiler-1.0.1-beta.jar)

2. Put the jar into your project and correctly reference it.

3. Configure your app's `AndroidManifest.xml` like this

  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <manifest ... >
    ...
    <!-- Permissions -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

    <application ... >
    	...
    	<!-- You app id  -->
    	<meta-data
    		android:name="42:appid"
    		android:value="YOUR_APP_ID" />
    </application>

  </manifest>
  ```

    Replace **YOUR_APP_ID** with the app id you get on your app page

4. If you use proguard, add these into your `proguard-rules.pro`

  ```
  -dontwarn com.google.android.gms.**
  ```

5. Call `Profiler.getProfile(Context context)` to get the inferred profile about the user installed your app. This call returns immediately and start a background thread to fetch the profile if none is cached locally.

### Privacy

You need to provide a preference in the default shared preferences for users to opt out at any time. The default key to use is `com.core42matters.android.profiler.personalized`.
This demo also shows how to implement this in the preferences.


## What's inside a profile (1.0.1-beta)

### Gender

`profile.isMale()` and `profile.isFemale()` tells the inferred gender. It could happen both of them return `false`, when we are not confident about our prediction for example.

### Interests

`profile.getInterests()` would return a `List<String>` containing the interests of this user we predict.

## Use AdMobPlugin to decorate an ad request

You could use our `AdMobPlugin` to attach inferred profile to an AdMob ad request to increase the chance getting better targeted ads.

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  ...
  AdView adView = (AdView) findViewById(R.id.adView);
  adView.loadAd(AdMobPlugin.buildRequest(this));
  ...
}
```
Alternatively, you could use `AdMobPlugin.decorate(Context context, AdRequest.Builder builder)` to finish your request building.

Calls to `Profiler` are done within the `AdMobPlugin`, so you do not have to call them explicitly.

## Contact us

If you have any questions when implementing these, feel free to [drop us an email](mailto:developers@42matters.com).

## License

    Copyright 2014 42matters AG.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
