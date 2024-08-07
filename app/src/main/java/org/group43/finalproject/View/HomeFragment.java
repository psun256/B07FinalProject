package org.group43.finalproject.View;

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

import org.group43.finalproject.Model.Artifact;
import org.group43.finalproject.Model.SelectedArtifactModel;
import org.group43.finalproject.R;

public class HomeFragment extends Fragment {
    private final String TAG = "HomeFragment";
  
    private Button signInButton;
    private Button searchButton;
    private Button viewButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        signInButton = view.findViewById((R.id.signInButton));
        searchButton = view.findViewById((R.id.searchButton));
        viewButton = view.findViewById((R.id.viewButton));

        signInButton.setOnClickListener(v -> loadFragment(new AdminLoginFragment()));

        searchButton.setOnClickListener(v -> loadFragment(new SearchArtifactFragment()));

        viewButton.setOnClickListener(v -> {
            Artifact a = SelectedArtifactModel.getSelectedArtifact();
            if (a == null) {
                Toast.makeText(getContext(), "Please select an artifact", Toast.LENGTH_SHORT).show();
            } else {
                loadFragment(new ViewArtifactFragment());
            }
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
