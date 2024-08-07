package org.group43.finalproject.Presenter;

public interface AdminLoginContract {
    interface Model {
        void performAdminLogin(String email, String password, AdminLoginPresenter presenter);
    }

    interface View {
        void viewAdminLoginSuccess(String message);
        void viewAdminLoginFailure(String message);
        void viewPasswordResetSuccess(String message);
    }

    interface Presenter {
        void handleAdminLogin(String email, String password);
        void onAdminLoginSuccess();
        void onAdminLoginFailure();
        void resetPassword(String email);
        void onPasswordResetSuccess();
    }
}
