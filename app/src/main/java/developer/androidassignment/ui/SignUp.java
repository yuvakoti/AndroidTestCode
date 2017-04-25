package developer.androidassignment.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import developer.androidassignment.R;
import developer.androidassignment.models.UserData;
import developer.androidassignment.network.Common;
import developer.androidassignment.network.HttpAdapter;
import developer.androidassignment.network.NetworkOperationListener;
import developer.androidassignment.network.NetworkResponse;

public class SignUp extends AppCompatActivity implements View.OnClickListener, NetworkOperationListener {
    SharedPreferences sharedPreferences;
    EditText user_name_et,password_et,mobile_et,name_et;
    TextView signup_tv,signin_tv;

    String PROJECT_NUMBER="123456789";
    String reg_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sharedPreferences=getSharedPreferences("Userdetails", Context.MODE_PRIVATE);

        user_name_et=(EditText)findViewById(R.id.user_name_et);
        password_et=(EditText)findViewById(R.id.password_et);
        mobile_et=(EditText)findViewById(R.id.mobile_et);
        signup_tv=(TextView) findViewById(R.id.signup_tv);
        signin_tv=(TextView) findViewById(R.id.signin_tv);
        name_et=(EditText) findViewById(R.id.name_et);
        signup_tv.setOnClickListener(this);
        signin_tv.setOnClickListener(this);

        GCMClientManager pushClientManager = new GCMClientManager(this, PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {

                Log.d("Registration id", registrationId);
                reg_id= registrationId;
                //send this registrationId to your server
            }

            @Override
            public void onFailure(String ex) {
                super.onFailure(ex);
                Log.d("Registration id failed", "failed");
            }
        });

    }
    //String username,String password,
//String phoneno,String name,String email,String regId
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.signup_tv){

            HttpAdapter.registration(this,"registration",user_name_et.getText().toString(),password_et.getText().toString(),
                    mobile_et.getText().toString(),name_et.getText().toString(),user_name_et.getText().toString(),reg_id);
        }else {
           finish();
        }
    }

    @Override
    public void operationCompleted(NetworkResponse networkResponse) {

        Common.disMissDialog();
        try {
            JSONObject jsonObjects = new JSONObject(networkResponse.getResponseString());
            if (jsonObjects.getJSONObject("ResponseStatus").getString("statuscode").equals("0")) {
                JSONArray result = jsonObjects.getJSONArray("Data");
                for (int i = 0; i < result.length(); i++) {

                    UserData profileDTO = (UserData) new Gson().fromJson(result.getJSONObject(i).toString(), UserData.class);

                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("username",profileDTO.email);
                    editor.putString("phoneno",profileDTO.phoneno);
                    editor.putString("name",profileDTO.name);
                    editor.putString("regId",profileDTO.regId);
                    editor.commit();
                    Intent signUp=new Intent(SignUp.this,Home.class);
                    startActivity(signUp);
                    finishAffinity();

                }
                return;
            }else{

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
