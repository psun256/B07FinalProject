package org.group43.finalproject;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

import org.group43.finalproject.Model.AdminLoginModel;
import org.group43.finalproject.Presenter.AdminLoginContract;
import org.group43.finalproject.Presenter.AdminLoginPresenter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AdminLoginTest {
    AdminLoginContract.View view;

    AdminLoginModel model;

    @Test
    public void test_performAdminLogin() {
        view = Mockito.mock();
        model = Mockito.mock();
        AdminLoginPresenter presenter = new AdminLoginPresenter(view, model);
        presenter.handleAdminLogin("bob1234@gmail.com", "123456");
        verify(model).performAdminLogin("bob1234@gmail.com", "123456", presenter);
    }

    @Test
    public void test_onAdminLoginSuccess() {
        view = Mockito.mock();
        model = Mockito.mock();
        AdminLoginPresenter presenter = new AdminLoginPresenter(view, model);
        presenter.onAdminLoginSuccess();
        verify(view).viewAdminLoginSuccess("Login Success");
    }

    @Test
    public void test_onAdminLoginFailure() {
        view = Mockito.mock();
        model = Mockito.mock();
        AdminLoginPresenter presenter = new AdminLoginPresenter(view, model);
        presenter.onAdminLoginFailure("Incorrect Email or Password");
        verify(view).viewAdminLoginFailure("Incorrect Email or Password");
    }
}