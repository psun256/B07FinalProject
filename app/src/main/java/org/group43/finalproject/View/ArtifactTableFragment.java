package org.group43.finalproject.View;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.group43.finalproject.Model.Artifact;
import org.group43.finalproject.Model.ArtifactAdapter;
import org.group43.finalproject.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArtifactTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArtifactTableFragment extends Fragment {
    ArrayList<Artifact> artifacts;
    ArtifactAdapter artifactAdapter;
    public ArtifactTableFragment() {
        // Required empty public constructor
    }

    public static ArtifactTableFragment newInstance() {
        return new ArtifactTableFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artifact_table, container, false);
        RecyclerView table = view.findViewById(R.id.artifactTable);
        table.setLayoutManager(new LinearLayoutManager(getContext()));

        artifacts = new ArrayList<>();
        artifactAdapter = new ArtifactAdapter(artifacts);
        table.setAdapter(artifactAdapter);

        // TODO: Refactor this in MVP
        FirebaseDatabase db = FirebaseDatabase.getInstance(
                "https://b07finalproject-81ec0-default-rtdb.firebaseio.com/");
        DatabaseReference artifactsRef = db.getReference("artifacts/");

        artifactsRef.orderByChild("lotNumber").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                artifacts.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Artifact artifact = Objects.requireNonNull(snapshot.getValue(Artifact.class));
                    artifacts.add(artifact);
                    /*ArtifactTableRowView row = new ArtifactTableRowView(getContext());
                    row.setLotNumText(String.valueOf(artifact.getLotNumber()));
                    row.setNameText(artifact.getName());
                    row.setCategoryText(artifact.getCategory());
                    row.setPeriodText(artifact.getPeriod());
                    table.addView(row);*/
                }
                artifactAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
            }
        });
        return view;
    }
}