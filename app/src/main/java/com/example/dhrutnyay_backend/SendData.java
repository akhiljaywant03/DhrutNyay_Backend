package com.example.dhrutnyay_backend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SendData extends AppCompatActivity {
    String data = new String();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data);

        final EditText Complaint_id=(EditText)findViewById(R.id.Complaint_ID);
        final EditText Complainant_name=(EditText)findViewById(R.id.Complaint_Name);
        final EditText Complaint_type=(EditText)findViewById(R.id.Complaint_Type);
        final EditText Location=(EditText)findViewById(R.id.Location);
        final EditText Date=(EditText)findViewById(R.id.Date);
        final EditText time1=(EditText)findViewById(R.id.Time);

        Button add=(Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String complaint_id=Complaint_id.getText().toString();
                String complainant_name=Complainant_name.getText().toString();
                String complaint_type=Complaint_type.getText().toString();
                String location=Location.getText().toString();
                String date=Date.getText().toString();
                String Time1=time1.getText().toString();

                GetAllItemsAsyncTask getAllDevicesTask = new GetAllItemsAsyncTask(complaint_id ,complainant_name,complaint_type,location,date,Time1);

                try {

                    data=getAllDevicesTask.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //AddItem(complaint_id,complainant_name,complaint_type,location,date,Time1);


            }
        });

    }


    private class GetAllItemsAsyncTask extends AsyncTask<String, Void, String> {

        private String complaint_id;
        private String complainant_name;
        private String complaint_type;
        private String location;
        private String date;
        private String Time1;

        public GetAllItemsAsyncTask(String complaint_id, String complainant_name,String complaint_type,String location,String date,String Time1){
            this.complaint_id=complaint_id;
            this.complainant_name=complainant_name;
            this.complaint_type=complaint_type;
            this.location=location;
            this.date=date;
            this.Time1=Time1;

        }

        @Override
        protected String doInBackground(String... strings) {

            DatabaseAccess databaseAccess=DatabaseAccess.getInstance(SendData.this);
            Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();


            item.put("complaint_id",new AttributeValue().withS(complaint_id));
            item.put("complainant",new AttributeValue().withS(complainant_name));
            item.put("complaint_type",new AttributeValue().withS(complaint_type));
            item.put("location",new AttributeValue().withS(location));
            item.put("date",new AttributeValue().withS(date));
            item.put("Time1",new AttributeValue().withS(Time1));


            PutItemRequest putItemRequest=new PutItemRequest("Complaint",item);

            PutItemResult putItemResult=databaseAccess.dbClient.putItem(putItemRequest);

            return null;
        }
    }

//    public void AddItem(String complaint_id, String complainant, String complaint_type, String location, String date, String Time1){
//
//        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(SendData.this);
//
//        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
//
//
//        item.put("complaint_id",new AttributeValue().withS(complaint_id));
//        item.put("complainant",new AttributeValue().withS(complainant));
//        item.put("complaint_type",new AttributeValue().withS(complaint_type));
//        item.put("location",new AttributeValue().withS(location));
//        item.put("date",new AttributeValue().withS(date));
//        item.put("Time1",new AttributeValue().withS(Time1));
//
////        databaseAccess.dbTable_Complaint.putItem(Document.fromAttributeMap(item));
//
//        PutItemRequest putItemRequest=new PutItemRequest("Complaint",item);
//
//        PutItemResult putItemResult=databaseAccess.dbClient.putItem(putItemRequest);
//
//
//    }
}