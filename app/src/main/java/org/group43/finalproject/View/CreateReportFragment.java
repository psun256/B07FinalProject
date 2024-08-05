package org.group43.finalproject.View;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.group43.finalproject.Model.CategoryModel;
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

    private EditText lotNumFilter;
    private EditText nameFilter;
    private AutoCompleteTextView categoryFilter;
    private Spinner periodFilter;

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
        initializeCategoryFilter();
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

        lotNumFilter = view.findViewById(R.id.lotNumFilter);
        nameFilter = view.findViewById(R.id.nameFilter);
        categoryFilter = view.findViewById(R.id.categoryFilter);
        periodFilter = view.findViewById(R.id.periodFilter);

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

    @SuppressLint("ClickableViewAccessibility")
    private void initializeCategoryFilter() {
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this.requireContext(),
                android.R.layout.simple_dropdown_item_1line, CategoryModel.getInstance().getCategories());
        categoryFilter.setAdapter(categoryAdapter);

        categoryFilter.setOnTouchListener((view, motionEvent) -> {
            categoryFilter.showDropDown();
            return false;
        });
    }

    private void initializeReportButton(View view) {
        generateReportButton.setOnClickListener(v -> {
            Button checkedButton = view.findViewById(reportOptions.getCheckedRadioButtonId());
            String checkedButtonText = getFilterFromOption(checkedButton);

            if (createReportPresenter.validateInput(checkedButton, checkedButtonText)) {
                createReportPresenter.generateReport(checkedButton, checkedButtonText,
                        checkPicDescOnly.isChecked());
            }
        });
    }

    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private String getFilterFromOption(Button button) {
        if (button == reportByLotNumButton)
            return lotNumFilter.getText().toString().trim();
        if (button == reportByNameButton)
            return nameFilter.getText().toString().trim();
        if (button == reportByCategoryButton)
            return categoryFilter.getText().toString().trim();
        if (button == reportByPeriodButton)
            return periodFilter.getSelectedItem().toString();
        return null;
    }
}
