package com.example.myapplication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Utils {
    public static final Utils INSTANCE = new Utils();
    private FirebaseAuth firebaseAuthInstance = null;
    
    private Utils() {}
    public FirebaseAuth getFirebaseAuthInstance() {
        if (firebaseAuthInstance == null) {
            firebaseAuthInstance = FirebaseAuth.getInstance();
        }
        return firebaseAuthInstance;
    }
    public String getUserID() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return "";
    }
}
