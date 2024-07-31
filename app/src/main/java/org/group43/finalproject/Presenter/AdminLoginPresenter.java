package org.group43.finalproject.Presenter;

import static android.app.PendingIntent.getActivity;

import org.group43.finalproject.Presenter.AdminLoginContract;
import org.group43.finalproject.Model.AdminLoginModel;

public class AdminLoginPresenter implements AdminLoginContract.Presenter {
    private AdminLoginModel model;
    private AdminLoginContract.View view;

    public AdminLoginPresenter(AdminLoginContract.View view, AdminLoginModel model) {
        this.view = view;
        this.model = model;
    }
    @Override
    public void handleAdminLogin(String username, String password) {
        // call model
        AdminLoginModel model = new AdminLoginModel();
        model.performAdminLogin(username, password, this);
    }

    @Override
    public void onAdminLoginSuccess() {
        view.viewAdminLoginSuccess("Login Success");
    }

    @Override
    public void onAdminLoginFailure(String message) {
        view.viewAdminLoginFailure("Incorrect Email or Password");
    }
}
