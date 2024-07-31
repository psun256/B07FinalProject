package org.group43.finalproject.Model;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.group43.finalproject.Presenter.AdminLoginContract;
import org.group43.finalproject.Presenter.AdminLoginPresenter;

public class AdminLoginModel implements AdminLoginContract.Model {
    FirebaseAuth mAuth;
    public void performAdminLogin(String username, String password, AdminLoginPresenter presenter) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.i("AdminLogenModel", "logged in ig");
                    presenter.onAdminLoginSuccess();
                } else {
                    presenter.onAdminLoginFailure("upsi");
                }
            }
        });
    }
}
