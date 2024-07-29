package org.group43.finalproject.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.group43.finalproject.R;

import java.util.List;

public class ArtifactAdapter extends RecyclerView.Adapter<ArtifactAdapter.ArtifactViewHolder> {
    private final List<Artifact> artifacts;

    public ArtifactAdapter(List<Artifact> artifacts) {
        this.artifacts = artifacts;
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
        holder.lotNumText.setText(String.valueOf(artifact.getLotNumber()));
        holder.nameText.setText(artifact.getName());
        holder.categoryText.setText(artifact.getCategory());
        holder.periodText.setText(artifact.getPeriod());
    }

    public int getItemCount() {
        return artifacts.size();
    }

    public static class ArtifactViewHolder extends RecyclerView.ViewHolder {
        TextView lotNumText;
        TextView nameText;
        TextView categoryText;
        TextView periodText;

        public ArtifactViewHolder(@NonNull View view) {
            super(view);
            lotNumText = view.findViewById(R.id.lotNumber);
            nameText = view.findViewById(R.id.name);
            categoryText = view.findViewById(R.id.category);
            periodText = view.findViewById(R.id.period);
        }
    }
}
