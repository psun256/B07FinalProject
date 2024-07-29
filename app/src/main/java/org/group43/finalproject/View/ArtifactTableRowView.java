package org.group43.finalproject.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.group43.finalproject.R;

public class ArtifactTableRowView extends LinearLayout {
    private TextView LotNumText;
    private TextView nameText;
    private TextView categoryText;
    private TextView periodText;

    public ArtifactTableRowView(Context context) {
        super(context);
        init(context);
    }

    public ArtifactTableRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ArtifactTableRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.artifact_table_row, this, true);

        LotNumText = findViewById(R.id.lotNumber);
        nameText = findViewById(R.id.name);
        categoryText = findViewById(R.id.category);
        periodText = findViewById(R.id.period);
    }

    public void setLotNumText(String text) {
        LotNumText.setText(text);
    }

    public void setNameText(String text) {
        nameText.setText(text);
    }

    public void setCategoryText(String text) {
        categoryText.setText(text);
    }

    public void setPeriodText(String text) {
        periodText.setText(text);
    }
}
