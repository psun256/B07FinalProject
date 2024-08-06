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

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.group43.finalproject.Model.ReportPage;
import org.group43.finalproject.R;
import org.group43.finalproject.Model.Artifact;
import org.group43.finalproject.View.CreateReportFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class CreateReportPresenter {
    private static final int PAGE_WIDTH = 612;
    private static final int PAGE_HEIGHT = 450;
    private static final int MAX_IMAGE_WIDTH = 200;
    private static final int MAX_IMAGE_HEIGHT = 180;

    private final CreateReportFragment view;
    private final Queue<Artifact> artifactQueue;
    private int pageNum;

    public CreateReportPresenter(CreateReportFragment view) {
        this.view = view;
        artifactQueue = new LinkedList<>();
        pageNum = -1;
    }

    public void generateReport(Button checkedButton, String filterText, boolean picDescOnly) {
        Query query = getArtifactsToInclude(checkedButton.getText().toString(), filterText);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    view.showMessage("Generating report...");
                    for (DataSnapshot artifactSnapshot : snapshot.getChildren()) {
                        artifactQueue.add(artifactSnapshot.getValue(Artifact.class));
                    }

                    pageNum = 1;
                    PdfDocument pdf = new PdfDocument();
                    addPageToPDF(pdf, picDescOnly);
                } else {
                    view.showMessage("Error: No artifacts found matching filter. " +
                            "PDF Report not generated.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void addPageToPDF(PdfDocument pdf, boolean picDescOnly) {
        Artifact artifact = artifactQueue.poll();

        if (artifact == null) {
            downloadPDF(pdf);
            return;
        }

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT,
                pageNum).create();
        PdfDocument.Page page = pdf.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        ReportPage newPage = new ReportPage(pdf, page, canvas, artifact);

        drawBasicReportInfo(canvas, artifact);

        if (artifact.getFileType().equals(view.getResources().getString(R.string.image))) {
            createPDFWithImage(newPage, picDescOnly);
        } else {
            completePDF(newPage, picDescOnly, null);
        }
    }

    private void createPDFWithImage(ReportPage newPage, boolean picDescOnly) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageRef.child("img/"
                + newPage.getArtifact().getFile());

        imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
            Bitmap resizedBitmap = resizeBitmap(bitmap);
           newPage.getCanvas().drawBitmap(resizedBitmap, 20, 30, null);

            completePDF(newPage, picDescOnly, resizedBitmap);
        });
    }

    private void completePDF(ReportPage newPage, boolean picDescOnly, Bitmap bitmap) {
        if (!picDescOnly) {
            drawArtifactInfoToPDF(newPage.getCanvas(), newPage.getArtifact(), bitmap);
        }

        newPage.getPdf().finishPage(newPage.getPage());
        pageNum++;
        addPageToPDF(newPage.getPdf(), picDescOnly);
    }

    private Query getArtifactsToInclude(String option, String filterText) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = db.getReference("artifacts/");

        if (option.equals(view.getResources().getString(R.string.allArtifacts))) {
            return dbRef;
        }
        if (option.equals(view.getResources().getString(R.string.lotNum))) {
            return dbRef.orderByChild("lotNumber/")
                    .equalTo(Integer.parseInt(filterText))
                    .limitToFirst(1);
        }

        return dbRef.orderByChild(option.toLowerCase())
                .equalTo(filterText);
    }

    private void drawBasicReportInfo(Canvas canvas, Artifact artifact) {
        Paint paint = new Paint();
        TextPaint textPaint = new TextPaint();

        if (artifact.getDescription().length() > 1700) {
            textPaint.setTextSize(10);
        } else if (artifact.getDescription().length() > 1500) {
            textPaint.setTextSize(11);
        }

        StaticLayout descLayout = new StaticLayout(artifact.getDescription(), textPaint, 340,
                Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        canvas.save();
        canvas.translate(240, 30);
        descLayout.draw(canvas);
        canvas.restore();

        canvas.drawText(view.getResources().getString(R.string.app_name), 10, PAGE_HEIGHT - 10, paint);
        canvas.drawText(String.valueOf(pageNum), PAGE_WIDTH - 32, PAGE_HEIGHT - 10, paint);
    }

    private void drawArtifactInfoToPDF(Canvas canvas, Artifact artifact, Bitmap bitmap) {
        TextPaint nameTextPaint = new TextPaint();
        Paint paint = new Paint();
        int nameHeight = 30;

        nameTextPaint.setTextSize(16);

        if (bitmap != null) {
            nameHeight = bitmap.getHeight() + 50;
        }

        int lotNumHeight = nameHeight + 80;
        int periodHeight = lotNumHeight + 20;
        int categoryHeight = periodHeight + 20;

        StaticLayout layout = new StaticLayout(artifact.getName(), nameTextPaint, 250,
                Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        canvas.save();
        canvas.translate(20, nameHeight);
        layout.draw(canvas);
        canvas.restore();

        canvas.drawText(view.getResources().getString(R.string.lotNumShort) + artifact.getLotNumber(), 20, lotNumHeight, paint);
        canvas.drawText(view.getResources().getString(R.string.period) + ": " + artifact.getPeriod(), 20, periodHeight, paint);

        canvas.drawText(view.getResources().getString(R.string.category) + ": ", 20, categoryHeight, paint);
        layout = new StaticLayout(artifact.getCategory(), new TextPaint(), 140,
                Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        canvas.save();
        canvas.translate(75, categoryHeight - 11);
        layout.draw(canvas);
        canvas.restore();
    }

    private void downloadPDF(PdfDocument pdf) {
        try {
            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(downloadsDir, "TAAM-Report-" + UUID.randomUUID().toString()
                    + ".pdf");
            FileOutputStream output = new FileOutputStream(file);

            pdf.writeTo(output);
            pdf.close();
            output.close();
            view.showMessage("Report PDF downloaded!");
        } catch (IOException e) {
            view.showMessage("Error: There was an error downloading the file.");
        }
    }

    public boolean validateInput(Button checkedButton, String filterFromOption) {
        if (checkedButton == null) {
            view.showMessage("Please select an option to generate a report!");
            return false;
        }

        if (filterFromOption != null && filterFromOption.isEmpty()) {
            view.showMessage("Please enter the text to filter the report by!");
            return false;
        }

        return true;
    }

    private Bitmap resizeBitmap(Bitmap bitmap) {
        int newWidth;
        int newHeight;

        if (bitmap == null) {
            return null;
        }

        int oldWidth = bitmap.getWidth();
        int oldHeight = bitmap.getHeight();

        if (oldWidth > oldHeight) {
            newWidth = MAX_IMAGE_WIDTH;
            newHeight = (oldHeight * MAX_IMAGE_HEIGHT) / oldWidth;
        } else {
            newHeight = MAX_IMAGE_HEIGHT;
            newWidth = (oldWidth * MAX_IMAGE_WIDTH) / oldHeight;
        }

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
    }
}