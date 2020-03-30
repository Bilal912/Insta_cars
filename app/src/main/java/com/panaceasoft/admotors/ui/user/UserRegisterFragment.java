package com.panaceasoft.admotors.ui.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.panaceasoft.admotors.MainActivity;
import com.panaceasoft.admotors.R;
import com.panaceasoft.admotors.binding.FragmentDataBindingComponent;
import com.panaceasoft.admotors.databinding.FragmentUserLoginBinding;
import com.panaceasoft.admotors.databinding.FragmentUserRegisterBinding;
import com.panaceasoft.admotors.ui.common.PSFragment;
import com.panaceasoft.admotors.utils.AutoClearedValue;
import com.panaceasoft.admotors.utils.Constants;
import com.panaceasoft.admotors.utils.PSDialogMsg;
import com.panaceasoft.admotors.utils.Utils;
import com.panaceasoft.admotors.viewmodel.user.UserViewModel;
import com.panaceasoft.admotors.viewobject.User;

import org.json.JSONException;

import java.util.Arrays;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

/**
 * UserRegisterFragment
 *
 */

public class UserRegisterFragment extends PSFragment {


    //region Variables

    private final androidx.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    private UserViewModel userViewModel;
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;

    private String token;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private PSDialogMsg psDialogMsg;

    private boolean checkFlag;
    private final int GOOGLE_SIGN = 123;

    private GoogleSignInClient mGoogleSignInClient;

    @VisibleForTesting
    private AutoClearedValue<FragmentUserRegisterBinding> binding;

    private AutoClearedValue<ProgressDialog> prgDialog;

    //endregion


    //region Override Methods

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        FragmentUserRegisterBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_register, container, false, dataBindingComponent);

        FacebookSdk.sdkInitialize(getActivity());

        binding = new AutoClearedValue<>(this, dataBinding);
        callbackManager = CallbackManager.Factory.create();

        return binding.get().getRoot();
    }


    @Override
    protected void initUIAndActions() {

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = firebaseAuth -> {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                int a;
//                Toast.makeText(UserLoginFragment.this.getContext(), "Successfully singned in with" + currentUser.getUid(), Toast.LENGTH_SHORT).show();
            }
        };

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        if (getActivity() != null) {
            mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), googleSignInOptions);
        }

        psDialogMsg = new PSDialogMsg(getActivity(), false);

        // Init Dialog
        prgDialog = new AutoClearedValue<>(this, new ProgressDialog(getActivity()));
        //prgDialog.get().setMessage(getString(R.string.message__please_wait));

        prgDialog.get().setMessage((Utils.getSpannableString(getContext(), getString(R.string.message__please_wait), Utils.Fonts.MM_FONT)));
        prgDialog.get().setCancelable(false);

        //fadeIn Animation
        fadeIn(binding.get().getRoot());

        binding.get().loginButton.setOnClickListener(view -> {


//            if (connectivity.isConnected()) {
//
//                Utils.navigateAfterLogin(UserRegisterFragment.this.getActivity(), navigationController);
//
//            } else {
//
//                psDialogMsg.showWarningDialog(getString(R.string.no_internet_error), getString(R.string.app__ok));
//
//                psDialogMsg.show();
//            }

            Utils.navigateAfterLogin(UserRegisterFragment.this.getActivity(), navigationController);

        });

        binding.get().googleLoginButton.setOnClickListener(v -> signIn());
        if (mAuth.getCurrentUser() != null) {
            FirebaseUser user = mAuth.getCurrentUser();
        }

        binding.get().txt1.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "By clicking 'Sign Up' you are agree to our ".concat("<u><b> terms and conditions </b></u>").concat(" as well as our ").concat("<u><b>privacy policy</b></u>");
        binding.get().txt1.setText(Html.fromHtml(text));

        binding.get().txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationController.navigateToPrivacyPolicyActivity(getActivity());
            }
        });

        binding.get().back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connectivity.isConnected()) {

//                    Utils.navigateAfterLogin(UserRegisterFragment.this.getActivity(), navigationController);

                } else {


                    psDialogMsg.showWarningDialog(getString(R.string.no_internet_error), getString(R.string.app__ok));

                    psDialogMsg.show();
                }
                Utils.navigateAfterLogin(UserRegisterFragment.this.getActivity(), navigationController);

            }
        });

//        binding.get().policyAndPrivacyCheckBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (binding.get().policyAndPrivacyCheckBox.isChecked()) {
//                    navigationController.navigateToPrivacyPolicyActivity(getActivity());
//                    checkFlag = true;
//                } else {
//                    checkFlag = false;
//                }
//            }
//        });


        binding.get().registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    UserRegisterFragment.this.registerUser();
            }
        });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN);
    }

    @Override
    protected void initViewModels() {
        userViewModel = new ViewModelProvider(this, viewModelFactory).get(UserViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.addAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void initAdapters() {

    }

    @Override
    protected void initData() {

        bindingData();

        userViewModel.getRegisterUser().observe(this, listResource -> {

            if (listResource != null) {

                Utils.psLog("Got Data" + listResource.message + listResource.toString());

                switch (listResource.status) {
                    case LOADING:
                        // Loading State
                        // Data are from Local DB

                        prgDialog.get().show();

                        break;
                    case SUCCESS:
                        // Success State
                        // Data are from Server

                        if (listResource.data != null) {

                            if (getActivity() != null) {

                                Utils.registerUserLoginData(pref,listResource.data,binding.get().passwordEditText.getText().toString());
                                Utils.navigateAfterUserRegister(getActivity(),navigationController);

                            }

                            userViewModel.isLoading = false;
                            prgDialog.get().cancel();
                            updateRegisterBtnStatus();

                        }

                        break;
                    case ERROR:
                        // Error State

                        psDialogMsg.showWarningDialog(listResource.message, getString(R.string.app__ok));
                        binding.get().registerButton.setText(getResources().getString(R.string.register__register));
                        psDialogMsg.show();

                        userViewModel.isLoading = false;
                        prgDialog.get().cancel();

                        break;
                    default:
                        // Default
                        userViewModel.isLoading = false;
                        prgDialog.get().cancel();
                        break;
                }

            } else {

                // Init Object or Empty Data
                Utils.psLog("Empty Data");

            }
        });


        String token = pref.getString(Constants.NOTI_TOKEN, Constants.USER_NO_DEVICE_TOKEN);
        String tokeen= FirebaseInstanceId.getInstance().getToken();
//        Toast.makeText(getActivity(),token, Toast.LENGTH_SHORT).show();
//
        binding.get().fbLoginButton.setFragment(this);
        binding.get().fbLoginButton.setPermissions(Arrays.asList("email"));
        binding.get().fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                // Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        (object, response) -> {


                            String name = "";
                            String email = "";
                            String id = "";
                            String imageURL = "";
                            try {
                                if (object != null) {

                                    name = object.getString("name");

                                }
                                //link.setText(object.getString("link"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                if (object != null) {

                                    email = object.getString("email");

                                }
                                //link.setText(object.getString("link"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                if (object != null) {

                                    id = object.getString("id");

                                }
                                //link.setText(object.getString("link"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            if (!id.equals("")) {
                                prgDialog.get().show();
                                userViewModel.registerFBUser(id, name, email, imageURL, token);
                            }

                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "email,name,id");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                //  Toast.makeText(getActivity(), "canceled", Toast.LENGTH_SHORT).show();

                Utils.psLog("OnCancel.");
            }

            @Override
            public void onError(FacebookException e) {
                e.printStackTrace();
                // Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                Utils.psLog("OnError." + e);
            }


        });

        userViewModel.getRegisterFBUserData().observe(this, listResource -> {

            if (listResource != null) {

                Utils.psLog("Got Data" + listResource.message + listResource.toString());

                switch (listResource.status) {
                    case LOADING:
                        // Loading State
                        // Data are from Local DB

                        prgDialog.get().show();

                        break;
                    case SUCCESS:
                        // Success State
                        // Data are from Server

                        if (listResource.data != null) {
                            try {
                                if (getActivity() != null) {

                                    Utils.updateUserLoginData(pref, listResource.data.user);
//                                     Intent i=new Intent(UserLoginFragment.this.getActivity(),MainActivity.class);
//                                    startActivity(i);
                                    navigationController.navigateToCategory((MainActivity) UserRegisterFragment.this.getActivity());

                                }
                                else {
                                    Utils.updateUserLoginData(pref, listResource.data.user);
//                                    Intent i=new Intent(UserLoginFragment.this.getActivity(),MainActivity.class);
//                                    startActivity(i);
                                    navigationController.navigateToCategory((MainActivity) UserRegisterFragment.this.getActivity());

                                }
                            } catch (NullPointerException ne) {
                                Utils.psErrorLog("Null Pointer Exception.", ne);
                            } catch (Exception e) {
                                Utils.psErrorLog("Error in getting notification flag data.", e);
                            }

                            userViewModel.isLoading = false;
                            prgDialog.get().cancel();

                        }

                        break;
                    case ERROR:
                        // Error State

                        userViewModel.isLoading = false;
                        prgDialog.get().cancel();

                        psDialogMsg.showErrorDialog(listResource.message, getString(R.string.app__ok));
                        psDialogMsg.show();

                        break;
                    default:
                        // Default
                        //userViewModel.isLoading = false;
                        //prgDialog.get().cancel();
                        break;
                }

            } else {

                // Init Object or Empty Data
                Utils.psLog("Empty Data");

            }

        });

    }

    private void bindingData() {

        if (!userEmailToVerify.isEmpty()) {
            binding.get().emailEditText.setText(userEmailToVerify);
        }
        if (!userPasswordToVerify.isEmpty()) {
            binding.get().passwordEditText.setText(userPasswordToVerify);
        }
        if (!userNameToVerify.isEmpty()) {
            binding.get().nameEditText.setText(userNameToVerify);
        }
    }

    //endregion


    //region Private Methods

    private void updateRegisterBtnStatus() {
        if (userViewModel.isLoading) {
            binding.get().registerButton.setText(getResources().getString(R.string.message__loading));
        } else {
            binding.get().registerButton.setText(getResources().getString(R.string.register__register));
        }
    }

    private void registerUser() {

        Utils.hideKeyboard(getActivity());

        String userName = binding.get().nameEditText.getText().toString().trim();
        if (userName.equals("")) {

            psDialogMsg.showWarningDialog(getString(R.string.error_message__blank_name), getString(R.string.app__ok));

            psDialogMsg.show();
            return;
        }

        String userEmail = binding.get().emailEditText.getText().toString().trim();
        if (userEmail.equals("")) {

            psDialogMsg.showWarningDialog(getString(R.string.error_message__blank_email), getString(R.string.app__ok));

            psDialogMsg.show();
            return;
        }

        String userPassword = binding.get().passwordEditText.getText().toString().trim();
        if (userPassword.equals("")) {

            psDialogMsg.showWarningDialog(getString(R.string.error_message__blank_password), getString(R.string.app__ok));

            psDialogMsg.show();
            return;
        }


        userViewModel.isLoading = true;
        updateRegisterBtnStatus();

        String token = pref.getString(Constants.NOTI_TOKEN, Constants.USER_NO_DEVICE_TOKEN);

        userViewModel.setRegisterUser(new User(
                "",
                "",
                "",
                "",
                "",
                "",
                userName,
                userEmail,
                "",
                "",
                "",
                userPassword,
                "",
                "",
                "",
                "",
                "",
                "",
                "", token, "", "", "", "", "", "", "", "", "", "", "", "", "", "", null));

        Toast.makeText(UserRegisterFragment.this.getActivity(),"You Are Registered",Toast.LENGTH_LONG).show();
        Utils.navigateAfterLogin(UserRegisterFragment.this.getActivity(), navigationController);
    }

    //endregion
    private void doSubmit(String email, String password) {

        String token = pref.getString(Constants.NOTI_TOKEN, Constants.USER_NO_DEVICE_TOKEN);

        //prgDialog.get().show();
        userViewModel.setUserLogin(new User(
                "",
                "",
                "",
                "",
                "",
                "",
                email,
                email,
                "",
                ",",
                "",
                password,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                token,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                null));

        userViewModel.isLoading = true;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account);
                }
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        if (getActivity() != null) {
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(getActivity(), task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                userViewModel.setGoogleLoginUser(user.getUid(), user.getDisplayName(), user.getEmail(), String.valueOf(user.getPhotoUrl()), token);

                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(UserRegisterFragment.this.getActivity(), "SignIn Failed", Toast.LENGTH_LONG).show();

                        }
                    });
        }
    }

}
