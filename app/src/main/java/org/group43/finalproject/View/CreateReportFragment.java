package org.group43.finalproject.View;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.group43.finalproject.Presenter.CreateReportPresenter;
import org.group43.finalproject.R;

import java.util.Objects;

public class CreateReportFragment extends Fragment {
    private Toolbar reportToolbar;

    private RadioGroup reportOptions;

    private RadioButton reportByLotNumButton;
    private RadioButton reportByNameButton;
    private RadioButton reportByCategoryButton;
    private RadioButton reportByPeriodButton;

    private EditText editTextLotNum;
    private EditText editTextName;
    private EditText editTextCategory;
    private EditText editTextPeriod;

    private CheckBox checkPicDescOnly;

    private Button generateReportButton;

    private CreateReportPresenter createReportPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_report, container, false);
        createReportPresenter = new CreateReportPresenter(this);

        initializeView(view);
        initializeToolbar();
        initializeReportButton(view);

        return view;
    }

    private void initializeView(View view) {
        reportToolbar = view.findViewById(R.id.reportToolbar);

        reportOptions = view.findViewById(R.id.reportOptions);

        reportByLotNumButton = view.findViewById(R.id.reportByLotNumButton);
        reportByNameButton = view.findViewById(R.id.reportByNameButton);
        reportByCategoryButton = view.findViewById(R.id.reportByCategoryButton);
        reportByPeriodButton = view.findViewById(R.id.reportByPeriodButton);

        editTextLotNum = view.findViewById(R.id.editTextLotNum);
        editTextName = view.findViewById(R.id.editTextName);
        editTextCategory = view.findViewById(R.id.editTextCategory);
        editTextPeriod = view.findViewById(R.id.editTextPeriod);

        checkPicDescOnly = view.findViewById(R.id.checkPicDescOnly);

        generateReportButton = view.findViewById(R.id.generateReportButton);
    }

    private void initializeToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        Objects.requireNonNull(activity).setSupportActionBar(reportToolbar);

        if (activity.getSupportActionBar() != null) {
            activity.setSupportActionBar(reportToolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setHomeButtonEnabled(true);
            Objects.requireNonNull(reportToolbar.getNavigationIcon())
                    .setColorFilter(ContextCompat.getColor(requireContext(),
                            R.color.backgroundLight), PorterDuff.Mode.SRC_IN);
        }

        reportToolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
    }

    private void initializeReportButton(View view) {
        generateReportButton.setOnClickListener(v -> {
            Button checkedButton = view.findViewById(reportOptions.getCheckedRadioButtonId());
            EditText checkedButtonText = getEditTextFromButton(checkedButton);

            if (createReportPresenter.validateInput(checkedButton, checkedButtonText)) {
                createReportPresenter.generateReport(checkedButton, checkedButtonText,
                        checkPicDescOnly.isChecked());
            }
        });
    }

    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private EditText getEditTextFromButton(Button button) {
        if (button == reportByLotNumButton)
            return editTextLotNum;
        if (button == reportByNameButton)
            return editTextName;
        if (button == reportByCategoryButton)
            return editTextCategory;
        if (button == reportByPeriodButton)
            return editTextPeriod;
        return null;
    }
}
