package org.group43.finalproject.View;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import org.group43.finalproject.R;

import java.util.Objects;

public class ExitAdminFragment extends Fragment {

    private final String TAG = "ExitAdminFragment";

    private FirebaseAuth mAuth;

    Button signOutButton;
    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exit_admin, container, false);

        signOutButton = view.findViewById(R.id.signInButton);
        toolbar = view.findViewById(R.id.toolbar);

        mAuth = FirebaseAuth.getInstance();

        AppCompatActivity activityToolBar = (AppCompatActivity)getActivity();
        activityToolBar.setSupportActionBar(toolbar);
        activityToolBar.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activityToolBar.getSupportActionBar().setTitle(getString(R.string.signOutToolbar));
        Objects.requireNonNull(toolbar.getNavigationIcon())
                    .setColorFilter(ContextCompat.getColor(requireContext(),
                            R.color.backgroundLight), PorterDuff.Mode.SRC_IN);
        toolbar.setNavigationOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth != null && mAuth.getCurrentUser() != null) {
                    Log.i(TAG, "User " + mAuth.getCurrentUser().getEmail() + " logged out");
                    mAuth.signOut();
                }
                Toast.makeText(getContext(), getString(R.string.signOutMessage), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}