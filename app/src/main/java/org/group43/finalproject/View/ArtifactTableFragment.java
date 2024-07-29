package org.group43.finalproject.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import org.group43.finalproject.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArtifactTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArtifactTableFragment extends Fragment {
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
        TableLayout table = view.findViewById(R.id.artifactTable);

        // TODO: Refactor this in MVP
        FirebaseDatabase db = FirebaseDatabase.getInstance(
                "https://b07finalproject-81ec0-default-rtdb.firebaseio.com/");
        DatabaseReference artifactsRef = db.getReference("artifacts/");

        artifactsRef.orderByChild("lotNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                table.removeAllViews();
                TableRow headings = new TableRow(getContext());
                TextView checkboxHeading = new TextView(getContext());
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Artifact artifact = Objects.requireNonNull(snapshot.getValue(Artifact.class));
                    TableRow row = new TableRow(getContext());
                    row.setPadding(2, 3, 2, 3);

                    TableRow.LayoutParams checkBoxParams = new TableRow.LayoutParams(
                            0,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            0.08f
                    );

                    CheckBox checkBox = new CheckBox(getContext());
                    checkBox.setLayoutParams(checkBoxParams);
                    checkBox.setGravity(Gravity.CENTER_VERTICAL);
                    row.addView(checkBox);

                    TextView lotNumText = new TextView(getContext());
                    lotNumText.setText(String.valueOf(artifact.getLotNumber()));
                    TableRow.LayoutParams lotNumParams = new TableRow.LayoutParams(
                            0,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            0.15f
                    );
                    lotNumText.setLayoutParams(lotNumParams);
                    row.addView(lotNumText);

                    TextView nameText = new TextView(getContext());
                    nameText.setText(artifact.getName());
                    nameText.setPadding(2,2,2,2);
                    TableRow.LayoutParams nameParams = new TableRow.LayoutParams(
                            0,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            0.35f
                    );
                    nameText.setLayoutParams(nameParams);

                    row.addView(nameText);

                    TextView categoryText = new TextView(getContext());
                    TableRow.LayoutParams categoryParams = new TableRow.LayoutParams(
                            0,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            0.25f
                    );
                    categoryText.setText(artifact.getCategory());
                    categoryText.setLayoutParams(categoryParams);
                    row.addView(categoryText);

                    TextView periodText = new TextView(getContext());
                    periodText.setText(artifact.getPeriod());
                    TableRow.LayoutParams periodParams = new TableRow.LayoutParams(
                            0,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            0.2f
                    );
                    periodText.setLayoutParams(periodParams);
                    row.addView(periodText);

                    table.addView(row);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
            }
        });
        return view;
    }
}