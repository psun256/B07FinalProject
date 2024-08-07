package org.group43.finalproject.Model;

import com.google.firebase.auth.FirebaseAuth;

import org.group43.finalproject.Presenter.AdminLoginContract;
import org.group43.finalproject.Presenter.AdminLoginPresenter;

public class AdminLoginModel implements AdminLoginContract.Model {
    public void performAdminLogin(String username, String password, AdminLoginPresenter presenter) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                presenter.onAdminLoginSuccess();
            } else {
                presenter.onAdminLoginFailure();
            }
        });
    }

    public void performPasswordReset(String email, AdminLoginPresenter presenter) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            presenter.onPasswordResetSuccess();
        });
    }
}
