package org.group43.finalproject.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import org.group43.finalproject.Model.Admin;
import org.group43.finalproject.Model.AdminLoginModel;
import org.group43.finalproject.Presenter.AdminLoginContract;
import org.group43.finalproject.Presenter.AdminLoginPresenter;
import org.group43.finalproject.R;

public class AdminLoginFragment extends Fragment implements View.OnClickListener, AdminLoginContract.View {

    EditText loginEmail, loginPassword;
    Button loginButton;
    FirebaseAuth mAuth;

    private AdminLoginPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_login, container, false);

        // get login email button
        loginEmail = view.findViewById(R.id.loginEmail);
        loginPassword = view.findViewById(R.id.loginPassword);
        loginButton = view.findViewById(R.id.loginButton);

        // set onclick listener
        loginButton.setOnClickListener(this);

        presenter = new AdminLoginPresenter(this, new AdminLoginModel());

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loginButton) {
            loginCheck();
        }
    }

    private void loginCheck() {
        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            loginEmail.setError("Please enter email");
            loginEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            loginPassword.setError("Please enter password");
            loginPassword.requestFocus();
        } else {
            presenter.handleAdminLogin(email, password);
        }
    }

    @Override
    public void viewAdminLoginSuccess(String message) {
        Log.i("AdminLoginFragment", message);
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void viewAdminLoginFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
