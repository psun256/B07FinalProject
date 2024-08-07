package org.group43.finalproject.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.PorterDuff;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;

import org.group43.finalproject.Model.AdminLoginModel;
import org.group43.finalproject.Presenter.AdminLoginContract;
import org.group43.finalproject.Presenter.AdminLoginPresenter;
import org.group43.finalproject.R;

import java.util.Objects;

public class AdminLoginFragment extends Fragment implements View.OnClickListener, AdminLoginContract.View {
    private final String TAG = "AdminLoginFragment";

    private FirebaseAuth mAuth;

    private EditText loginEmail, loginPassword;
    private TextView forgotPassword;
    private Button loginButton;
    private Toolbar toolbar;

    private AdminLoginPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_login, container, false);

        loginEmail = view.findViewById(R.id.loginEmail);
        loginPassword = view.findViewById(R.id.loginPassword);
        loginButton = view.findViewById(R.id.loginButton);
        forgotPassword = view.findViewById(R.id.forgotPassword);

        toolbar = view.findViewById(R.id.toolbar);

        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);

        AppCompatActivity activityToolBar = (AppCompatActivity)getActivity();
        activityToolBar.setSupportActionBar(toolbar);
        activityToolBar.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activityToolBar.getSupportActionBar().setTitle(R.string.signInToolbar);
        Objects.requireNonNull(toolbar.getNavigationIcon())
                .setColorFilter(ContextCompat.getColor(requireContext(), R.color.backgroundLight), PorterDuff.Mode.SRC_IN);
        toolbar.setNavigationOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        presenter = new AdminLoginPresenter(this, new AdminLoginModel());

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loginButton) {
            loginCheck();
        }
        if (view.getId() == R.id.forgotPassword) {
            resetPassword();
        }
    }

    public void resetPassword() {
        // alert box to confirm password reset
        String email = loginEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            loginEmail.setError(getString(R.string.loginEmailEmpty));
            loginEmail.requestFocus();
            return;
        }
        Log.i(TAG, "Resetting password");
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.resetPassword)
                .setMessage(R.string.resetPasswordMessage)
                .setPositiveButton(R.string.resetPasswordOk, (dialog, which) -> {
                    if (TextUtils.isEmpty(email)) {
                        loginEmail.setError(getString(R.string.loginEmailEmpty));
                        loginEmail.requestFocus();
                    } else {
                        presenter.resetPassword(email);
                    }
                })
                .setNegativeButton(R.string.resetPasswordNo, (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void loginCheck() {
        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            loginEmail.setError(getString(R.string.loginEmailEmpty));
            loginEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            loginPassword.setError(getString(R.string.loginPasswordEmpty));
            loginPassword.requestFocus();
        } else {
            presenter.handleAdminLogin(email, password);
        }
    }

    @Override
    public void viewAdminLoginSuccess(String message) {
        Log.i(TAG, "User " + mAuth.getCurrentUser().getEmail() + " logged in");
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void viewAdminLoginFailure(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void viewPasswordResetSuccess(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
