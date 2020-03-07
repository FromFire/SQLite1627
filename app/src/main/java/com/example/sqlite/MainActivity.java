package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private EditText mEditText1;
    private  EditText mEditText2;
    private  EditText mEditText3;
    private  EditText mEditText4;
    private  String name;
    private  String email;
    private  String mNumber;
    private DBHelper helper;
    private SQLiteDatabase db;
    private  Cursor c;
    private ContentValues mValues;
    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private TextView mTextView4;
    private TextView mTextView5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper=new DBHelper(this,"User.db",null,1);
        init();
        event();
    }

    private void init() {
        //function--getID
        mButton1=(Button)findViewById(R.id.write);
        mButton2=(Button)findViewById(R.id.read);
        mButton3=(Button)findViewById(R.id.update);
        mButton4=(Button)findViewById(R.id.remove);
        mEditText1=(EditText)findViewById(R.id.name);
        mEditText2=(EditText)findViewById(R.id.email);
        mEditText3=(EditText)findViewById(R.id.moblieNumber);
        mEditText4=(EditText)findViewById(R.id.id);
        mTextView1=(TextView)findViewById(R.id.tv_1);
        mTextView2=(TextView)findViewById(R.id.tv_2);
        mTextView3=(TextView)findViewById(R.id.tv_3);
        mTextView4=(TextView)findViewById(R.id.tv_4);
        mTextView5=(TextView)findViewById(R.id.tv_id);
    }

    private void event() {
        //set button event
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        db=helper.getWritableDatabase();
        switch (v.getId()){
            case R.id.write:    //write
                mTextView1.setVisibility(View.INVISIBLE);
                mTextView2.setVisibility(View.VISIBLE);
                mTextView3.setVisibility(View.VISIBLE);
                mTextView4.setVisibility(View.VISIBLE);
                name=mEditText1.getText().toString();
                email=mEditText2.getText().toString();
                mNumber=mEditText3.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(MainActivity.this,"Please input NAME", Toast.LENGTH_LONG).show();
                }
                else if(email.isEmpty()){
                    Toast.makeText(MainActivity.this,"Please input EMAILE", Toast.LENGTH_LONG).show();
                }
                else if(mNumber.isEmpty()){
                    Toast.makeText(MainActivity.this,"Please input MOBILENUMBER", Toast.LENGTH_LONG).show();
                }
                else if(!name.isEmpty()||!email.isEmpty()||!mNumber.isEmpty()){
                    try{
                        db.execSQL("INSERT INTO User VALUES(NULL,?,?,?)",new Object[]{
                                name,email,mNumber});

                    }catch (SQLException e){
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,"Database error!", Toast.LENGTH_LONG).show();
                        break;
                    }
                    Toast.makeText(MainActivity.this,"Write data successfully！", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.read:   //read
                String sql="SELECT* FROM user";
                c=db.rawQuery(sql,new String[]{});
                if(c.getCount()!=0){
                    mTextView1.setVisibility(View.VISIBLE);
                    mTextView2.setVisibility(View.INVISIBLE);
                    mTextView3.setVisibility(View.INVISIBLE);
                    mTextView4.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this,"Read data successfully!", Toast.LENGTH_LONG).show();
                    String data="";
                    while(c.moveToNext()){
                        int ID=c.getInt(c.getColumnIndex("_id"));
                        String name=c.getString(c.getColumnIndex("name"));
                        String email=c.getString(c.getColumnIndex("email"));
                        int mnumber=c.getInt(c.getColumnIndex("mNumber"));
                        data=data +"\n"+"name:"+name+"\n"+"email:"+email+"\n"+"mNumber:"+mnumber+"\n";
                    }
                    mTextView1.setText(data);
                }
                break;
            case R.id.update: //update
                name=mEditText1.getText().toString();
                email=mEditText2.getText().toString();
                mNumber=mEditText3.getText().toString();
                if(mNumber.isEmpty()){
                    Toast.makeText(MainActivity.this,"Please input mobile number！", Toast.LENGTH_LONG).show();
                }else{
                    sql="SELECT * FROM user where mNumber="+mNumber;
                    c=db.rawQuery(sql,new String[]{});
                    if(c.getCount()==0){
                        Toast.makeText(MainActivity.this,"The number does't exist!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        try{

                            if(name.isEmpty()&&email.isEmpty()){
                                Toast.makeText(MainActivity.this,"Please enter something to modify!", Toast.LENGTH_LONG).show();
                            }
                            if(!name.isEmpty()&&!mNumber.isEmpty()){
                                sql="update user set name="+name+" where mNumber="+mNumber;
                                db.execSQL(sql);
                                Toast.makeText(MainActivity.this,"Update successfully！", Toast.LENGTH_LONG).show();
                            }
                            if(!email.isEmpty()&&!mNumber.isEmpty()){
                                sql="update user set email="+email+" where mNumber="+mNumber;
                                db.execSQL(sql);
                                Toast.makeText(MainActivity.this,"Update successfully！", Toast.LENGTH_LONG).show();
                            }
                        }catch (SQLException e){
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this,"Update failed！", Toast.LENGTH_LONG).show();
                        }

                    }
                }
                break;
            case R.id.remove:   //remove all in the user table
                try{
                    sql="DELETE FROM user";
                    db.execSQL(sql);
                    mTextView1.setVisibility(View.INVISIBLE);
                    mTextView2.setVisibility(View.VISIBLE);
                    mTextView3.setVisibility(View.VISIBLE);
                    mTextView4.setVisibility(View.VISIBLE);
                }catch (SQLException e){
                    e.printStackTrace();
                    break;
                }
                Toast.makeText(MainActivity.this,"All contents have been successfully cleared！", Toast.LENGTH_LONG).show();
                break;
        }
    }
}


