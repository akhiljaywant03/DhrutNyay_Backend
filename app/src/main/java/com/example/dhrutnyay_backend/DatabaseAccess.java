package com.example.dhrutnyay_backend;

import android.content.Context;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Primitive;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.List;
public class DatabaseAccess {
    private static final String COGNITO_POOL_ID = "ap-south-1:3fb56dc4-493c-482a-aec7-1ad0dbe7155b";
    private static final Regions MY_REGION = Regions.AP_SOUTH_1;
    public AmazonDynamoDBClient dbClient;
    public Table dbTable;
    public Table dbTable_Complaint;
    private Context context;
    private final String DYNAMODB_TABLE = "Test";
    private final String DDB_Complaint="Complaint";
    CognitoCachingCredentialsProvider credentialsProvider;


    private static volatile DatabaseAccess instance;
    private DatabaseAccess (Context context) {
        this.context =context;
        credentialsProvider = new CognitoCachingCredentialsProvider (context, COGNITO_POOL_ID, MY_REGION);
        dbClient = new AmazonDynamoDBClient(credentialsProvider);
        dbClient.setRegion(Region.getRegion(Regions.AP_SOUTH_1));
        dbTable = Table.loadTable(dbClient, DYNAMODB_TABLE);
        dbTable_Complaint=Table.loadTable(dbClient,DDB_Complaint);
    }
    public static synchronized DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }
    public Document getItem (String user_id){
        Document result = dbTable_Complaint.getItem(new Primitive(credentialsProvider.getCachedIdentityId()), new Primitive(user_id));
        return result;
    }
    public List<Document> getAllItems() {
        return dbTable_Complaint.query(new Primitive("1")).getAllResults();
    }

}