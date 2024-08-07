package org.group43.finalproject.Presenter;

import org.group43.finalproject.Model.AdminLoginModel;

public class AdminLoginPresenter implements AdminLoginContract.Presenter {
    private AdminLoginModel model;
    private AdminLoginContract.View view;

    public AdminLoginPresenter(AdminLoginContract.View view, AdminLoginModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void handleAdminLogin(String email, String password) {
        model.performAdminLogin(email, password, this);
    }

    @Override
    public void onAdminLoginSuccess() {
        view.viewAdminLoginSuccess("Sucessfully signed in");
    }

    @Override
    public void onAdminLoginFailure() {
        view.viewAdminLoginFailure("Incorrect email or password");
    }

    @Override
    public void resetPassword(String email) { model.performPasswordReset(email, this); }

    @Override
    public void onPasswordResetSuccess() { view.viewPasswordResetSuccess("Password reset email sent"); }
}
