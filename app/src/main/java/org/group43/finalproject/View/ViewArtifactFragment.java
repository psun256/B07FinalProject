package org.group43.finalproject.View;

import android.os.Bundle;
import android.view.*;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.group43.finalproject.Model.Artifact;
import org.group43.finalproject.Model.SelectedArtifactModel;
import org.group43.finalproject.R;
import java.util.List;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ViewArtifactFragment extends Fragment {

    private TextView lotNumber;
    private TextView name;
    private TextView category;
    private TextView period;
    private TextView description;
    private ImageView image;
    private VideoView video;
    private Button backButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_artifact, container, false);

        lotNumber = view.findViewById(R.id.detailViewLotNumber);
        name = view.findViewById(R.id.detailViewName);
        category = view.findViewById(R.id.detailViewCategory);
        period = view.findViewById(R.id.detailViewPeriod);
        description = view.findViewById(R.id.detailViewDescription);
        image = view.findViewById(R.id.detailViewImage);
        video = view.findViewById(R.id.detailViewVideo);
        backButton = view.findViewById(R.id.backButton);

        // Fetch the selected artifact
        Artifact artifact = SelectedArtifactModel.getSelectedArtifact();


        if (artifact != null ) {
            lotNumber.setText(String.valueOf(artifact.getLotNumber()));
            name.setText(artifact.getName());
            category.setText(artifact.getCategory());
            period.setText(artifact.getPeriod());
            description.setText(artifact.getDescription());

            String fileType = artifact.getFileType();
            String filePath = artifact.getFile();


            /* Reference for Accessing Firebase storage and loading images into Views:
            * https://www.youtube.com/watch?v=DRqObCUCGl0
            * https://www.youtube.com/watch?v=_eTZowmape8
            * */


            if (fileType.equals("image")) {

                image.setVisibility(View.VISIBLE);
                video.setVisibility(View.GONE);

                // images are stored in img/filename
                StorageReference fbStorage = FirebaseStorage.getInstance().getReference().child("img/" + filePath);
                fbStorage.getDownloadUrl().addOnSuccessListener(uri ->
                        Picasso.get().load(uri).into(image)
                );

            } else if (fileType.equals("video")) {
                image.setVisibility(View.GONE);
                video.setVisibility(View.VISIBLE);

                // videos are stored in vid/filename
                StorageReference fbStorage = FirebaseStorage.getInstance().getReference().child("vid/" + filePath);
                fbStorage.getDownloadUrl().addOnSuccessListener(uri -> {
                    video.setVideoURI(uri);
                    video.start();
                });


            }
        }

        // Reference: https://www.youtube.com/watch?v=FcPUFp8Qrps
        backButton.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        return view;
    }


}
