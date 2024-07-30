package org.group43.finalproject.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.group43.finalproject.Presenter.ArtifactViewHolderPresenter;
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
        holder.presenter.setArtifact(artifact);
    }

    public int getItemCount() {
        return artifacts.size();
    }

    public static class ArtifactViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox selected;
        private final TextView lotNumText;
        private final TextView nameText;
        private final TextView categoryText;
        private final TextView periodText;
        private final ArtifactViewHolderPresenter presenter;

        public ArtifactViewHolder(@NonNull View view) {
            super(view);
            selected = view.findViewById(R.id.selected);
            lotNumText = view.findViewById(R.id.lotNumber);
            nameText = view.findViewById(R.id.name);
            categoryText = view.findViewById(R.id.category);
            periodText = view.findViewById(R.id.period);

            presenter = new ArtifactViewHolderPresenter(this);
        }

        public CheckBox getSelected() {
            return selected;
        }

        public TextView getLotNumText() {
            return lotNumText;
        }

        public TextView getNameText() {
            return nameText;
        }

        public TextView getCategoryText() {
            return categoryText;
        }

        public TextView getPeriodText() {
            return periodText;
        }
    }
}
