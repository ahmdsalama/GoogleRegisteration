package com.salama.googleregisteration;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private SignInButton signInButton;
    private GoogleSignInClient googleSignInClient;
    public static final String TAG="AndroidClarified";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInButton=findViewById(R.id.sign_in_button);
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        // GoogleSignIn is Entry point for the Google Sign In API
        googleSignInClient= GoogleSignIn.getClient(this,gso);
        // googleSignInClient A client for interacting with the Google Sign In API.GoogleSignInClient is
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent=googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent,1);

            }
        });
    }

    // i do this as when start this one check if the user alrady logged or no if logged so go to profile with GooggleAccount


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
            switch (requestCode)
            {
                case 1:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        onLoggedIn(account);
                        String idToken = account.getIdToken();
                    /*
                     Write to the logic send this id token to server using HTTPS
                     */
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                    }
                    break;
            }
    }



    // Google Sign in account is the account which holds the account information of the user

   private void onLoggedIn(GoogleSignInAccount googleSignInAccount)
   {
       Intent intent=new Intent(this,ProfileActiviti.class);
       intent.putExtra(ProfileActiviti.GOOGLE_ACCOUNT,googleSignInAccount); // so i send it to the profile Activity to take the info from it
       startActivity(intent);
   }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        GoogleSignInAccount alreadyloggedAccount=GoogleSignIn.getLastSignedInAccount(this);
//        if(alreadyloggedAccount != null){
//            onLoggedIn(alreadyloggedAccount);
//        }
//        else
//            Log.d(TAG, "Not logged in");
//
//    }









}


