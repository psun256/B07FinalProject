package org.group43.finalproject;

import org.group43.finalproject.Model.AdminLoginModel;
import org.group43.finalproject.Presenter.AdminLoginContract;
import org.group43.finalproject.Presenter.AdminLoginPresenter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AdminLoginTest {
    AdminLoginContract.View view;

    AdminLoginModel model;

    @Test
    public void test_performAdminLogin() {
        view = Mockito.mock(AdminLoginContract.View.class);
        model = Mockito.mock(AdminLoginModel.class);
        AdminLoginPresenter presenter = new AdminLoginPresenter(view, model);
        presenter.handleAdminLogin("bob1234@gmail.com", "123456");
        Mockito.verify(model).performAdminLogin("bob1234@gmail.com", "123456", presenter);
    }

    @Test
    public void test_onAdminLoginSuccess() {
        view = Mockito.mock(AdminLoginContract.View.class);
        model = Mockito.mock(AdminLoginModel.class);
        AdminLoginPresenter presenter = new AdminLoginPresenter(view, model);
        presenter.onAdminLoginSuccess();
        Mockito.verify(view).viewAdminLoginSuccess("Sucessfully signed in");
    }

    @Test
    public void test_onAdminLoginFailure() {
        view = Mockito.mock(AdminLoginContract.View.class);
        model = Mockito.mock(AdminLoginModel.class);
        AdminLoginPresenter presenter = new AdminLoginPresenter(view, model);
        presenter.onAdminLoginFailure();
        Mockito.verify(view).viewAdminLoginFailure("Incorrect Email or Password");
    }
}