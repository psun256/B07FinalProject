package org.group43.finalproject.Presenter;

public interface AdminLoginContract {
    public interface Model {
        void performAdminLogin(String username, String password, AdminLoginPresenter presenter);
    }

    public interface View {
        void viewAdminLoginSuccess(String message);
        void viewAdminLoginFailure(String message);
    }

    public interface Presenter {
        void handleAdminLogin(String username, String password);
        void onAdminLoginSuccess();
        void onAdminLoginFailure(String message);
    }
}
