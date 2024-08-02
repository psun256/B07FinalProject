package org.group43.finalproject.Model;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.group43.finalproject.Presenter.ArtifactViewHolderPresenter;
import org.group43.finalproject.R;

import java.util.ArrayList;
import java.util.List;

public class ArtifactAdapter extends RecyclerView.Adapter<ArtifactAdapter.ArtifactViewHolder> {
    private List<Artifact> artifacts;

    public ArtifactAdapter() {
        this(new ArrayList<>());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setArtifactsToDisplay(List<Artifact> artifacts) {
        this.artifacts = artifacts;
        notifyDataSetChanged();
    }

    public ArtifactAdapter(List<Artifact> artifacts) {
        setArtifactsToDisplay(artifacts);
    }

    @NonNull
    @Override
    public ArtifactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artifact_table_row, parent, false);
        return new ArtifactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtifactViewHolder holder, int position) {
        Artifact artifact = artifacts.get(position);
        holder.presenter.setArtifact(artifact);
        holder.presenter.setAdapter(this);
    }

    public int getItemCount() {
        return artifacts.size();
    }

    public static class ArtifactViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox selectedCheckBox;
        private final TextView lotNumText;
        private final TextView nameText;
        private final TextView categoryText;
        private final TextView periodText;
        private final ArtifactViewHolderPresenter presenter;

        public ArtifactViewHolder(@NonNull View view) {
            super(view);
            selectedCheckBox = view.findViewById(R.id.selected);
            lotNumText = view.findViewById(R.id.lotNumber);
            nameText = view.findViewById(R.id.name);
            categoryText = view.findViewById(R.id.category);
            periodText = view.findViewById(R.id.period);

            presenter = new ArtifactViewHolderPresenter(this);
        }

        public void displayArtifact(Artifact artifact, boolean selected) {
            lotNumText.setText(String.valueOf(artifact.getLotNumber()));
            nameText.setText(artifact.getName());
            categoryText.setText(artifact.getCategory());
            periodText.setText(artifact.getPeriod());

            selectedCheckBox.setOnCheckedChangeListener(null);
            selectedCheckBox.setChecked(selected);
            selectedCheckBox.setOnCheckedChangeListener((compoundButton, checked) -> {
                if (checked) {
                    presenter.onSelect();
                } else {
                    presenter.onDeselect();
                }
            });
        }
    }
}
