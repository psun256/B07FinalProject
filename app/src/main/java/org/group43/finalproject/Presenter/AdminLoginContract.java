package org.group43.finalproject.Presenter;

public interface AdminLoginContract {
    interface Model {
        void performAdminLogin(String username, String password, AdminLoginPresenter presenter);
    }

    interface View {
        void viewAdminLoginSuccess(String message);
        void viewAdminLoginFailure(String message);
    }

    interface Presenter {
        void handleAdminLogin(String username, String password);
        void onAdminLoginSuccess();
        void onAdminLoginFailure(String message);
    }
}
