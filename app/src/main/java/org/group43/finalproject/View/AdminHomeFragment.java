package org.group43.finalproject.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

import org.group43.finalproject.R;

public class AdminHomeFragment extends Fragment {

    private final String TAG = "AdminHomeFragment";

    private FirebaseAuth mAuth;

    Button addButton;
    Button signOutButton;
    Button removeButton;
    Button reportButton;
    Button searchButton;
    Button viewButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        addButton = view.findViewById((R.id.addButton));
        signOutButton = view.findViewById((R.id.signInButton));
        removeButton = view.findViewById((R.id.removeButton));
        reportButton = view.findViewById(R.id.reportButton);
        searchButton = view.findViewById((R.id.searchButton));
        viewButton = view.findViewById((R.id.viewButton));

        mAuth = FirebaseAuth.getInstance();

        if (mAuth == null || mAuth.getCurrentUser() == null) {
            Log.e(TAG, "User not logged in");
            addButton.setEnabled(false);
            removeButton.setEnabled(false);
            reportButton.setEnabled(false);
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { loadFragment(new AddArtifactFragment());}
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { loadFragment(new ExitAdminFragment());}
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { loadFragment(new RemoveArtifactFragment());}
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { loadFragment(new CreateReportFragment());}
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { loadFragment(new SearchArtifactFragment()); }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { loadFragment(new ViewArtifactFragment()); }
        });

        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
