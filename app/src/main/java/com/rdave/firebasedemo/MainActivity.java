package com.rdave.firebasedemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends BaseActivity {

    private static final int RC_SIGN_IN = 21;
    private static final String TAG = "MainActivity";
    protected EditText etEmail;
    protected EditText etPass;
    protected Button btnLogin;
    protected Button rgBtn;

    private SignInButton signInButton;
    private Button logoutBtn, btnRevokeAccess;

    private TextView txtName, txtEmail;

    private boolean Registered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        signInButton = (SignInButton) findViewById(R.id.btn_sign_in);
        logoutBtn = (Button) findViewById(R.id.btn_sign_out);
//        btnRevokeAccess = (Button) findViewById(R.id.btn);
//        llProfileLayout = (LinearLayout) findViewById(R.id.llProfile);
//        imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
//        mDatabase = FirebaseDatabase.getInstance();
//        mRef = mDatabase.getReference();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Registered = preferences.getBoolean("Registered", false);

        if (Registered) {
            signInButton.setVisibility(View.INVISIBLE);
            logoutBtn.setVisibility(View.VISIBLE);
            txtName.setVisibility(View.VISIBLE);
            txtEmail.setVisibility(View.VISIBLE);

//            txtName.setText("wellcome" + mAuth.getCurrentUser().getDisplayName());


        } else {
            signInButton.setVisibility(View.VISIBLE);
            logoutBtn.setVisibility(View.INVISIBLE);
            txtName.setVisibility(View.INVISIBLE);
            txtEmail.setVisibility(View.INVISIBLE);
        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                logout();
                dismissProgressDialog();

            }
        });


//        mAuth = FirebaseAuth.getInstance();
//

//
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        //Now we will attach a click listener to the sign_in_button
//        //and inside onClick() method we are calling the signIn() method that will open
//        //google sign in intent
//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signIn();
//            }
//        });
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        //if the user is already signed in
//        //we will close this activity
//        //and take the user to profile activity
//        if (mAuth.getCurrentUser() != null) {
//            finish();
//            startActivity(new Intent(this, RegisterActivity.class));
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        //if the requestCode is the Google Sign In code that we defined at starting
//        if (requestCode == RC_SIGN_IN) {
//
//            //Getting the GoogleSignIn Task
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                //Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//
//                //authenticating with firebase
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
//
//        //getting the auth credential
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//
//        //Now using firebase we are signing in the user here
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//
//                            Toast.makeText(MainActivity.this, "User Signed In", Toast.LENGTH_SHORT).show();
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        // ...
//                    }
//                });
        initView();
    }

    private void logout() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        editor.apply();

        signInButton.setVisibility(View.VISIBLE);
        logoutBtn.setVisibility(View.INVISIBLE);
        txtName.setVisibility(View.INVISIBLE);
        txtEmail.setVisibility(View.INVISIBLE);

        signOut();

    }


    private void signIn() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.e(TAG, "signIn: sign is creating");
    }

//}
//

//        Auth = FirebaseAuth.getInstance();
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//
//                .requestEmail()
//                .build();
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
//
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//
//        // Customizing G+ button
//        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
//        btnSignIn.setScopes(gso.getScopeArray());
//    }
//
//
//    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//
//
//    private void revokeAccess() {
//        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
//                new ResultCallback<Status>() {
//                    @Override
//                    public void onResult(Status status) {
//                        updateUI(false);
//                    }
//                });
//    }
//


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: is being created");

        if (resultCode == Activity.RESULT_OK) {

            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {

                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    GoogleSignInAccount account = result.getSignInAccount();
                    handleSignInResult(account);

                }

            }
        }
    }

    private void handleSignInResult(GoogleSignInAccount account) {
        showProgressDialog();

        Log.e(TAG, "handleSignInResult:");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {
                    // Signed in successfully, show authenticated UI.


                    Log.e(TAG, "display name: ");

                    String personName = mAuth.getCurrentUser().getDisplayName();
//                    String personPhotoUrl =
                    String email = mAuth.getCurrentUser().getEmail();

                    Log.e(TAG, "Name: " + personName + ", email: " + email
                    );

                    txtName.setText(personName);
                    txtEmail.setText(email);
                    logoutBtn.setVisibility(View.VISIBLE);
                    signInButton.setVisibility(View.INVISIBLE);

                    txtName.setVisibility(View.VISIBLE);
                    txtEmail.setVisibility(View.VISIBLE);

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("Registered", true);
                    editor.putString("userName", personName);
                    editor.putString("userEmail", email);
                    editor.apply();

                    Log.e(TAG, "onComplete shared preference Value is saved");
                    dismissProgressDialog();


                }
            }
        });
    }

    private void initView() {
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPass);
        btnLogin = (Button) findViewById(R.id.btn_login);
        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        rgBtn = (Button) findViewById(R.id.rg_btn);
        rgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s= new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(s);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String logEmail = etEmail.getText().toString().trim();
                String logPass = etPass.getText().toString().trim();
                
                loginUser(logEmail,logPass);
                
            }
        });
    

   
    }

    private void loginUser(String logEmail, String logPass) {
        mAuth.signInWithEmailAndPassword(logEmail,logPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"successful log in",Toast.LENGTH_SHORT).show();
                    Intent l = new Intent(MainActivity.this,WellcomeActivity.class);
                    l.putExtra("name",mAuth.getCurrentUser().getEmail());
                    startActivity(l);
                }
                else {
                    Toast.makeText(MainActivity.this,"error"+task.getException(),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}

//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
//        if (opr.isDone()) {
//            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
//            // and the GoogleSignInResult will be available instantly.
//            Log.d(TAG, "Got cached sign-in");
//            GoogleSignInAccount result = opr.get();
//            handleSignInResult(result);
//        } else {
//            // If the user has not previously signed in on this device or the sign-in has expired,
//            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
//            // single sign-on will occur in this branch.
//            showProgressDialog();
//            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
//                @Override
//                public void onResult(GoogleSignInResult googleSignInResult) {
//                    dismissProgressDialog();
//                    handleSignInResult(googleSignInResult);
//                }
//            });
//        }
//    }
//
//

//
//
//    private void updateUI(boolean isSignedIn) {
//        if (isSignedIn) {
//            btnSignIn.setVisibility(View.GONE);
//            btnSignOut.setVisibility(View.VISIBLE);
//            btnRevokeAccess.setVisibility(View.VISIBLE);
//            llProfileLayout.setVisibility(View.VISIBLE);
//        } else {
//            btnSignIn.setVisibility(View.VISIBLE);
//            btnSignOut.setVisibility(View.GONE);
//            btnRevokeAccess.setVisibility(View.GONE);
//            llProfileLayout.setVisibility(View.GONE);
//        }
//    }




