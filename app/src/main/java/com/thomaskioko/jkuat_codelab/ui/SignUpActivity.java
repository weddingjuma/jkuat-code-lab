package com.thomaskioko.jkuat_codelab.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.thomaskioko.jkuat_codelab.R;
import com.thomaskioko.jkuat_codelab.util.DeviceUtils;
import com.thomaskioko.jkuat_codelab.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * This activity allows a user to register via {@link FirebaseAuth}
 *
 * @author kioko
 */
public class SignUpActivity extends AppCompatActivity {

    private static final String LOG_TAG = SignUpActivity.class.getSimpleName();
    //Bind views with ButterKnife.
    @BindView(R.id.email)
    EditText mEtEmail;
    @BindView(R.id.password)
    EditText mEtPassword;
    @BindView(R.id.sign_in_button)
    Button mBtnSignIn;
    @BindView(R.id.sign_up_button)
    Button mBtnSignUp;
    @BindView(R.id.btn_reset_password)
    Button mBtnResetPassword;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private FirebaseAuth mFirebaseAuth;
    private String mStrEmail, mStrPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        mFirebaseAuth = FirebaseAuth.getInstance();

        if (!DeviceUtils.isNetworkConnected(this)) {
            mBtnSignIn.setEnabled(false);
            mBtnSignUp.setEnabled(false);
            mBtnResetPassword.setEnabled(false);
        }

    }

    /**
     * Helper method to handle on click events using ButterKnife annotation.
     *
     * @param view {@link View}
     */
    @OnClick({R.id.sign_in_button, R.id.sign_up_button, R.id.btn_reset_password})
    void viewClickListener(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                finish();
                break;
            case R.id.sign_up_button:

                if (isInputValid()) {
                    progressBar.setVisibility(View.VISIBLE);
                    //create user
                    mFirebaseAuth.createUserWithEmailAndPassword(mStrEmail, mStrPassword)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(SignUpActivity.this, "Status:: " + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                    Timber.i(LOG_TAG, "@onComplete:: Status:: " + task.isSuccessful());
                                    progressBar.setVisibility(View.GONE);
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Timber.e(LOG_TAG, "@onComplete:: " + task.getException());
                                    } else {
                                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }
                            });
                }
                break;
            case R.id.btn_reset_password:
                startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Helper method to check if user input is valid
     *
     * @return {@link Boolean} True/False
     */
    private boolean isInputValid() {

        mStrEmail = mEtEmail.getText().toString().trim();
        mStrPassword = mEtPassword.getText().toString().trim();

        //Check if the email is empty.
        if (TextUtils.isEmpty(mStrEmail)) {
            mEtEmail.setError("Enter an email address!");
            return false;
        }

        //Check if password is empty.
        if (TextUtils.isEmpty(mStrPassword)) {
            mEtPassword.setError("Enter a Password!");
            return false;
        }

        /**
         * Validate password {@link StringUtils#validatePassword(String)}
         */
        if (StringUtils.validatePassword(mStrPassword)) {
            mEtPassword.setError("Password too short, enter minimum 6 characters!");
            return false;
        }

        /**
         * Validate Email Address {@link StringUtils#validateEmailAddress(String)}
         */
        if (StringUtils.validateEmailAddress(mStrEmail)) {
            mEtEmail.setError("Please enter a valid E-mail address!");
            return false;
        }

        return true;
    }

}