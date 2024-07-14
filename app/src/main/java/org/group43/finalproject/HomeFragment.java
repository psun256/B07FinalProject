package org.group43.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);

        Button addButton = view.findViewById((R.id.addButton));
        Button adminButton = view.findViewById((R.id.adminButton));
        Button backButton = view.findViewById((R.id.backButton));
        Button removeButton = view.findViewById((R.id.removeButton));
        Button reportButton = view.findViewById(R.id.reportButton);
        Button searchButton = view.findViewById((R.id.searchButton));
        Button viewButton = view.findViewById((R.id.viewButton));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { loadFragment(new AddArtifactFragment());}
        });

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { loadFragment(new AdminLoginFragment());}
        });

        backButton.setOnClickListener(new View.OnClickListener() {
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
            public void onClick(View v) { loadFragment(new SearchArtifactFragment());}
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { loadFragment(new ViewArtifactFragment());}
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
