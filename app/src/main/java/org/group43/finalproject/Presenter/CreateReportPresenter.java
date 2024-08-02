package org.group43.finalproject.Presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.group43.finalproject.R;
import org.group43.finalproject.Model.Artifact;
import org.group43.finalproject.View.CreateReportFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class CreateReportPresenter {
    private static final int PAGE_WIDTH = 612;
    private static final int PAGE_HEIGHT = 400;
    private static final int IMAGE_WIDTH = 200;
    private static final int IMAGE_HEIGHT = 200;

    private final CreateReportFragment view;

    public CreateReportPresenter(CreateReportFragment view) {
        this.view = view;
    }

    public void generateReport(Button checkedButton, EditText checkedButtonText, boolean picDescOnly) {
        Query query = getArtifactsToInclude(checkedButton.getText().toString(), checkedButtonText);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    createPDF(snapshot, picDescOnly);
                } else {
                    view.showMessage("Error: No artifacts found matching filter. PDF Report not generated.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void createPDF(DataSnapshot snapshot, boolean picDescOnly) {
        PdfDocument pdf = new PdfDocument();
        int pageNum = 1;

        for (DataSnapshot artifactSnapshot : snapshot.getChildren()) {
            addPageToReport(pdf, Objects.requireNonNull(artifactSnapshot
                    .getValue(Artifact.class)), picDescOnly, pageNum);
            pageNum++;
        }

        downloadPDF(pdf);
    }

    private Query getArtifactsToInclude(String option, EditText checkedButtonText) {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://b07finalproject-81ec0-default-rtdb.firebaseio.com/");
        DatabaseReference dbRef = db.getReference("artifacts/");

        if (option.equals(view.getResources().getString(R.string.allArtifacts))) {
            return dbRef;
        }
        if (option.equals(view.getResources().getString(R.string.lotNum))) {
            return dbRef.orderByChild("lotNumber/")
                    .equalTo(Integer.parseInt(checkedButtonText.getText().toString().trim()))
                    .limitToFirst(1);
        }

        return dbRef.orderByChild(option.toLowerCase())
                .equalTo(checkedButtonText.getText().toString().trim());
    }

    private void addPageToReport(PdfDocument pdf, Artifact artifact, boolean PicDescOnly, int pageNum) {
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, pageNum).create();
        PdfDocument.Page page = pdf.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        TextPaint mTextPaint = new TextPaint();
        Paint paint = new Paint();

        if (artifact.getFileType().equals(view.getResources().getString(R.string.image))) {
            //drawImageToPdf(canvas, artifact);
        }

        if (!PicDescOnly) {
            drawArtifactInfoToPDF(canvas, artifact, paint);
        }

        StaticLayout descLayout = new StaticLayout(artifact.getDescription(), mTextPaint, 320, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        canvas.save();
        canvas.translate(250, 30);
        descLayout.draw(canvas);
        canvas.restore();

        canvas.drawText(view.getResources().getString(R.string.app_name), 10, 390, paint);
        canvas.drawText(String.valueOf(pageNum), 580, 390, paint);

        pdf.finishPage(page);
    }

    private void drawImageToPdf(Canvas canvas, Artifact artifact) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageRef.child("img/" + artifact.getFile());

        imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            Bitmap resizeBitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_WIDTH, IMAGE_HEIGHT, true);

            canvas.drawBitmap(resizeBitmap, 0, 0, null);
        }).addOnFailureListener(exception -> {});
    }

    private void drawArtifactInfoToPDF(Canvas canvas, Artifact artifact, Paint paint) {
        TextPaint nameTextPaint = new TextPaint();
        StaticLayout layout;

        nameTextPaint.setTextSize(16);

        layout = new StaticLayout(artifact.getName(), nameTextPaint, 210, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        canvas.save();
        canvas.translate(10, 210);
        layout.draw(canvas);
        canvas.restore();

        canvas.drawText(view.getResources().getString(R.string.lotNumShort) + artifact.getLotNumber(), 10, 280, paint);
        canvas.drawText(view.getResources().getString(R.string.period) + ": " + artifact.getPeriod(), 10, 300, paint);

        canvas.drawText(view.getResources().getString(R.string.category) + ": ", 10, 321, paint);
        layout = new StaticLayout(artifact.getCategory(), new TextPaint(), 150, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        canvas.save();
        canvas.translate(65, 310);
        layout.draw(canvas);
        canvas.restore();
    }

    private void downloadPDF(PdfDocument pdf) {
        try {
            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(downloadsDir, "TAAM-Report-" + UUID.randomUUID().toString() + ".pdf");
            FileOutputStream output = new FileOutputStream(file);

            pdf.writeTo(output);
            pdf.close();
            output.close();
            view.showMessage("Report PDF downloaded!");
        } catch (IOException e) {
            view.showMessage("Error: There was an error downloading the file.");
        }
    }

    public boolean validateInput(Button checkedButton, EditText editTextFromButton) {
        if (checkedButton == null) {
            view.showMessage("Please select an option to generate a report!");
            return false;
        }

        if (editTextFromButton != null && editTextFromButton.getText().toString().trim().isEmpty()) {
            view.showMessage("Please enter the text to filter the report by!");
            return false;
        }

        return true;
    }

}
