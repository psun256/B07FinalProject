package org.group43.finalproject.View;
import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import org.group43.finalproject.Presenter.AddArtifactPresenter;
import org.group43.finalproject.R;

import java.util.Objects;

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

        AppCompatActivity activityToolBar = (AppCompatActivity)getActivity();
        activityToolBar.setSupportActionBar(toolbar);
        activityToolBar.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activityToolBar.getSupportActionBar().setTitle("Amogous Acid");
        Objects.requireNonNull(toolbar.getNavigationIcon())
                    .setColorFilter(ContextCompat.getColor(requireContext(),
                            R.color.backgroundLight), PorterDuff.Mode.SRC_IN);
        toolbar.setNavigationOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Log.i("AdminLogenModel", "logged out");
                Toast.makeText(getContext(), "Successfully signed out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
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