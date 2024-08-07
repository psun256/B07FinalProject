package org.group43.finalproject.Model;

import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;

public class ReportPage {
    private final PdfDocument pdf;
    private final PdfDocument.Page page;
    private final Canvas canvas;
    private final Artifact artifact;

    public ReportPage(PdfDocument pdf, PdfDocument.Page page, Canvas canvas, Artifact artifact) {
        this.pdf = pdf;
        this.page = page;
        this.canvas = canvas;
        this.artifact = artifact;
    }

    public PdfDocument getPdf() {
        return pdf;
    }

    public PdfDocument.Page getPage() {
        return page;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Artifact getArtifact() {
        return artifact;
    }
}
