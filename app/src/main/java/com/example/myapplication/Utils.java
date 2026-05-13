package com.example.myapplication;

import com.google.firebase.auth.FirebaseAuth;

public class Utils {
    public static final Utils INSTANCE = new Utils();
    private Utils() {}
    
    private FirebaseAuth firebaseAuthInstance = null;

    public FirebaseAuth getFirebaseAuthInstance() {
        if (firebaseAuthInstance == null) {
            firebaseAuthInstance = FirebaseAuth.getInstance();
        }
        return firebaseAuthInstance;
    }
}