package org.group43.finalproject.View;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.group43.finalproject.R;

public class ExitAdminFragment extends Fragment {

    private FirebaseAuth mAuth;

    Button signOutButton;
    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exit_admin, container, false);

        signOutButton = view.findViewById(R.id.signOutButton);
        toolbar = view.findViewById(R.id.toolbar);

        mAuth = FirebaseAuth.getInstance();

        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    signOutComplete();
                }
                else {

                }
            }
        };

        mAuth.addAuthStateListener(authStateListener);

        AppCompatActivity activityToolBar = (AppCompatActivity)getActivity();
        activityToolBar.setSupportActionBar(toolbar);
        activityToolBar.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activityToolBar.getSupportActionBar().setTitle("Amogous Acid");
        toolbar.setNavigationOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });

        return view;
    }

    private void signOutComplete() {
        Log.i("AdminLogenModel", "logged out");
        Toast.makeText(getContext(), "Successfully signed out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }
}
