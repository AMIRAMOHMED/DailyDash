package com.example.dailydash.planer.data.firebase;

import com.example.dailydash.planer.data.database.MealPlan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirebaseManager {

    private final FirebaseFirestore firestore;
    private final FirebaseAuth auth;

    public FirebaseManager() {
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    private String getUserId() {
        FirebaseUser user = auth.getCurrentUser();
        return user != null ? user.getUid() : null;
    }

    public Completable addMealPlan(MealPlan mealPlan) {
        String userId = getUserId();
        if (userId == null) {
            return Completable.error(new Throwable("User not logged in"));
        }

        return Completable.create(emitter -> {
            firestore.collection("users")
                    .document(userId)
                    .collection("userMeals")
                    .document(mealPlan.getMealId())
                    .set(mealPlan)
                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }


    public Single<List<MealPlan>> fetchMealPlans() {
        String userId = getUserId();
        if (userId == null) {
            return Single.error(new Throwable("User not logged in"));
        }

        return Single.create(emitter -> {
            firestore.collection("users")
                    .document(userId)
                    .collection("userMeals")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<MealPlan> mealPlans = queryDocumentSnapshots.toObjects(MealPlan.class);
                        emitter.onSuccess(mealPlans);
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }


    public Completable removeMealPlan(MealPlan mealPlan) {
        String userId = getUserId();
        if (userId == null) {
            return Completable.error(new Throwable("User not logged in"));
        }

        return Completable.create(emitter -> {
            firestore.collection("users")
                    .document(userId)
                    .collection("userMeals")
                    .document(mealPlan.getMealId())
                    .delete()
                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Completable clearMealPlans() {
        String userId = getUserId();
        if (userId == null) {
            return Completable.error(new Throwable("User not logged in"));
        }

        return Completable.create(emitter -> {
            firestore.collection("users")
                    .document(userId)
                    .collection("userMeals")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            document.getReference().delete();
                        }
                        emitter.onComplete();
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

}
