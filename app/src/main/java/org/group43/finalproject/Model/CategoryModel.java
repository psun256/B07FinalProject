package org.group43.finalproject.Model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryModel {
    private static CategoryModel categoryModel;

    private CategoryModel() {}

    public static CategoryModel getInstance() {
        if (categoryModel == null) {
            categoryModel = new CategoryModel();
        }
        return categoryModel;
    }

    public ArrayList<String> getCategories() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = db.getReference("categories/");
        ArrayList<String> categories = new ArrayList<>();

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String category = snapshot.getKey();

                    if (!categories.contains(category)) {
                        categories.add(category);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        return categories;
    }

    public void updateCategories(Artifact artifact) {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://b07finalproject-81ec0-default-rtdb.firebaseio.com/");
        DatabaseReference dbRef = db.getReference("categories/");

        dbRef.child(artifact.getCategory()).setValue(new Category(artifact.getCategory()));
    }
}
