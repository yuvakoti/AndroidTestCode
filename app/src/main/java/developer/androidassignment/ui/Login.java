package developer.androidassignment.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class Login extends AppCompatActivity implements View.OnClickListener, NetworkOperationListener {
    SharedPreferences sharedPreferences;
    EditText user_name_et, password_et;
    TextView login_tv, signup_tv;
    String PROJECT_NUMBER="123456789";
    String reg_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user_name_et=(EditText)findViewById(R.id.user_name_et);
        password_et=(EditText)findViewById(R.id.password_et);
        login_tv=(TextView) findViewById(R.id.login_tv);
        signup_tv=(TextView)findViewById(R.id.signup_tv);
        sharedPreferences=getSharedPreferences("Userdetails", Context.MODE_PRIVATE);
        login_tv.setOnClickListener(this);
        signup_tv.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.login_tv) {

            if (user_name_et.getText().toString().length() == 0||password_et.getText().toString().length() == 0) {
                Toast.makeText(Login.this,"please enter the username and password", Toast.LENGTH_LONG).show();
            } else {
                Common.showDialog(this);
                HttpAdapter.login(this,"login",user_name_et.getText().toString(),password_et.getText().toString());
            }
        }
        else if(view.getId()==R.id.signup_tv){
            Intent signup=new Intent(Login.this,SignUp.class);
            startActivity(signup);

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

                    Intent code_et = new Intent(Login.this,Home.class);
                    startActivity(code_et);
                    finishAffinity();
                }
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
