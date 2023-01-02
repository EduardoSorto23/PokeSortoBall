# FirebaseUI for Kotlin — UI Bindings for Firebase

[![FirebaseOpensource.com](https://img.shields.io/badge/Docs-firebaseopensource.com-orange.svg)](
https://firebaseopensource.com/projects/firebase/firebaseui-android
)
[![Actions Status][gh-actions-badge]][gh-actions]

FirebaseUI is an open-source library for Android that allows you to
quickly connect common UI elements to [Firebase](https://firebase.google.com) APIs.

A compatible FirebaseUI client is also available for [iOS](https://github.com/firebase/firebaseui-ios).

## Table of contents

1. [Usage](#usage)
1. [Installation](#installation)
1. [Dependencies](#dependencies)
   1. [Compatibility](#compatibility-with-firebase--google-play-services-libraries)
   1. [Upgrading dependencies](#upgrading-dependencies)
1. [Sample App](#sample-app)
1. [evaluation points](#points)


## Usage

FirebaseUI has separate modules for using Firebase Realtime Database, Cloud Firestore,
Firebase Auth, and Cloud Storage. To get started, see the individual instructions for each module:

* [FirebaseUI Auth]()
* [FirebaseUI Firestore]()
* [FirebaseUI Database]()
* [FirebaseUI Storage]()

## Installation

FirebaseUI is published as a collection of libraries separated by the
Firebase API they target. Each FirebaseUI library has a transitive
dependency on the appropriate Firebase SDK so there is no need to include
those separately in your app.

In your `app/build.gradle` file add a dependency on one of the FirebaseUI
libraries.

```groovy
dependencies {
    // FirebaseUI for Firebase Realtime Database
    implementation 'com.firebaseui:firebase-ui-database:19.2.0'

    // FirebaseUI for Cloud Firestore
    implementation 'com.firebaseui:firebase-ui-firestore:19.2.0'

    // FirebaseUI for Firebase Auth
    implementation 'com.firebaseui:firebase-ui-auth:19.2.0'

    // FirebaseUI for Cloud Storage
    implementation 'com.firebaseui:firebase-ui-storage:19.2.0'
}
```

If you're including the `firebase-ui-auth` dependency, there's a little
[more setup](https://firebase.google.com/docs/android/setup) required.

After the project is synchronized, we're ready to start using Firebase functionality in our app.


## Dependencies

### Compatibility with Firebase / Google Play Services libraries

FirebaseUI libraries have the following transitive dependencies on the Firebase SDK:
```
firebase-ui-auth
|--- com.google.firebase:firebase-auth
|--- com.google.android.gms:play-services-auth

firebase-ui-database
|--- com.google.firebase:firebase-database

firebase-ui-firestore
|--- com.google.firebase:firebase-firestore

firebase-ui-storage
|--- com.google.firebase:firebase-storage
```

You can see the specific dependencies associated with each release on the 
[Releases page](https://firebase.google.com/support/releases).

### Upgrading dependencies

If you would like to use a newer version of one of FirebaseUI's transitive dependencies, such
as Firebase, Play services, or the Android support libraries, you need to add explicit
`implementation` declarations in your `build.gradle` for all of FirebaseUI's dependencies at the version
you want to use. Here are some examples listing all of the critical dependencies:

#### Auth

```groovy
implementation "com.google.firebase:firebase-auth:$X.Y.Z"
implementation "com.google.android.gms:play-services-auth:$X.Y.Z"

implementation "androidx.lifecycle:lifecycle-extensions:$X.Y.Z"
implementation "androidx.browser:browser:$X.Y.Z"
implementation "androidx.cardview:cardview:$X.Y.Z"
implementation "androidx.constraintlayout:constraintlayout:$X.Y.Z"
implementation "androidx.legacy:legacy-support-v4:$X.Y.Z"
implementation "com.google.android.material:material:$X.Y.Z"
```

#### Firestore

```groovy
implementation "com.google.firebase:firebase-firestore:$X.Y.Z"

implementation "androidx.legacy:legacy-support-v4:$X.Y.Z"
implementation "androidx.recyclerview:recyclerview:$X.Y.Z"
```

#### Realtime Database

```groovy
implementation "com.google.firebase:firebase-database:$X.Y.Z"

implementation "androidx.legacy:legacy-support-v4:$X.Y.Z"
implementation "androidx.recyclerview:recyclerview:$X.Y.Z"
```

#### Storage

```groovy
implementation "com.google.firebase:firebase-storage:$X.Y.Z"

implementation "androidx.legacy:legacy-support-v4:$X.Y.Z"
```


## Sample app

There is a sample app in the [`app/`](app) directory that demonstrates most
of the features of FirebaseUI used in this project adding access through social networks. 
Load the project in Android Studio and run it on your Android device to see a the proof developed.

Before you can run the app, developed and integrated with firebase should load 
all dependencies and verify that there are no dependency loading problems 
in case we have different versions of andoid studio.

If you encounter a version incompatibility error between Android Studio
and Gradle while trying to run the sample app, try disabling the Instant
Run feature of Android Studio. Alternatively, update Android Studio and
Gradle to their latest versions.


## Evaluation Points

1. Using the PokéAPI v2: https://pokeapi.co/.
1. Firebase Auth implementation for user authentication via Google or Facebook:.
1. persistence of any type of data.
1. Creation of teams.
1. Crud created teams, with the option to modify or delete them.
1. Pokedex complete list of pokemon with their respective detail.
1. pokemon search.

Important notes for crud teams:
1. `Creation:`
  To create a new team, we click on the application, a floating action button is displayed that takes us to the form to create a new team
1. `Eliminations:`
If we want to delete a record from the base in the list of created teams, we move to the right or left an element of the list and it is automatically deleted.
1. `Modification:`
To modify a team we make a long touch on the screen and the form will open with the information of the selected team to edit

[gh-actions]: https://github.com/firebase/FirebaseUI-Android/actions
[gh-actions-badge]: https://github.com/firebase/FirebaseUI-Android/workflows/Android%20CI/badge.svg
