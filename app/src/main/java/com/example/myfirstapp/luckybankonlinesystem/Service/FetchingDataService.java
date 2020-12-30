package com.example.myfirstapp.luckybankonlinesystem.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.myfirstapp.luckybankonlinesystem.Model.CustomerModel;
import com.example.myfirstapp.luckybankonlinesystem.Model.TransactionModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.MetadataChanges;

import java.util.ArrayList;
import java.util.logging.Logger;

public class FetchingDataService extends Service {

    public static final String INTENT_KEY = "FireStore.Data";
    public static final String USER_INFO_KEY = "UserInfo";
    public static final String TRANSACTION_HISTORY_KEY = "Transactions";
    public static final String SEND_USER_UID_KEY = "USER_UID";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        String uid = intent.getStringExtra(SEND_USER_UID_KEY);
        FirebaseFirestore db = FirebaseFirestore.getInstance(FirebaseApp.initializeApp(this));
        db.collection("users")
                .document(uid)
                .addSnapshotListener(MetadataChanges.INCLUDE, (data, error) -> {
                    Logger.getLogger("DEBUG").warning("user info occurred");
                    if (error == null) {
                        Intent dataIntent = new Intent(INTENT_KEY + "." + USER_INFO_KEY);
                        CustomerModel userInfo = data.toObject(CustomerModel.class);
                        userInfo.setCustomerId(data.getId());
                        dataIntent.setAction(INTENT_KEY + "." + USER_INFO_KEY).putExtra(USER_INFO_KEY, userInfo);
                        LocalBroadcastManager.getInstance(this).sendBroadcast(dataIntent);
                    } else {
                        Logger.getLogger("DEBUG").warning("user info error: " + error.getMessage());
                    }
                });
        db.collection("transactions")
                .whereEqualTo("senderUID", uid)
                .addSnapshotListener(MetadataChanges.INCLUDE, (data, error) -> {
                    Logger.getLogger("DEBUG").warning("transaction event occurred");
                    if (error == null) {
                        Intent dataIntent = new Intent(INTENT_KEY + "." + TRANSACTION_HISTORY_KEY);
                        ArrayList<TransactionModel> transactions = new ArrayList<>();
                        for (DocumentSnapshot snapshot : data.getDocuments()) {
                            TransactionModel model = snapshot.toObject(TransactionModel.class);
                            model.setTransactionID(snapshot.getId());
                            transactions.add(model);
                        }
                        dataIntent.setAction(INTENT_KEY + "." + TRANSACTION_HISTORY_KEY);
                        dataIntent.putExtra(TRANSACTION_HISTORY_KEY, transactions);
                        LocalBroadcastManager.getInstance(this).sendBroadcast(dataIntent);
                    } else {
                        Logger.getLogger("DEBUG").warning("transaction error: " + error.getMessage());
                    }
                });
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.getLogger("DEBUG").warning("service killed by system");
    }
}