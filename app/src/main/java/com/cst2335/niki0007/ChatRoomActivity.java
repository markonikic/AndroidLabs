package com.cst2335.niki0007;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    MyListAdapter myAdapter;

    ArrayList <Message> saveChat = new ArrayList < >();
    Button sendBtn;
    Button recBtn;
    EditText editText;
    TextView message;
    private Object MyListAdapter;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        saveChat = new ArrayList();
        sendBtn = (Button) findViewById(R.id.buttonSend);
        recBtn = (Button) findViewById(R.id.buttonRec);
        editText = (EditText) findViewById(R.id.editTextChat);
        ListView chat = findViewById(R.id.myList);
        chat.setAdapter(myAdapter = new MyListAdapter());

        DatabaseHelper openDB = new DatabaseHelper(this);
        db = openDB.getWritableDatabase();

        String [] columns = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_MESSAGE, DatabaseHelper.COLUMN_MESSAGE_TYPE};
        Cursor result = db.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null,null);

        printCursor(result);

        int idIndex = result.getColumnIndex(DatabaseHelper.COLUMN_ID);
        int chatIndex = result.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE);
        int chatTypeIndex = result.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_TYPE);

        result.moveToPosition(-1);
        while(result.moveToNext()){
            String message = result.getString(chatIndex);
            Boolean msgType = result.getInt(chatTypeIndex) == 1;
            long id = result.getLong(idIndex);
            saveChat.add(new Message(message, msgType, id));
        }

        myAdapter = new MyListAdapter();
        chat.setAdapter(myAdapter);

        sendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String chatText = editText.getText().toString();
                    ContentValues content = new ContentValues();
                    content.put(DatabaseHelper.COLUMN_MESSAGE, chatText);
                    content.put(DatabaseHelper.COLUMN_MESSAGE_TYPE, 1);
                    long newId = db.insert(DatabaseHelper.TABLE_NAME, null, content);

                    Message msg = new Message(chatText, true, newId);
                    saveChat.add(msg);
                    myAdapter.notifyDataSetChanged();
                    editText.setText("");
                }
            });

            recBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String chatText = editText.getText().toString();
                    ContentValues content = new ContentValues();
                    content.put(DatabaseHelper.COLUMN_MESSAGE, chatText);
                    content.put(DatabaseHelper.COLUMN_MESSAGE_TYPE, 0);
                    long newId = db.insert(DatabaseHelper.TABLE_NAME, null, content);

                    Message msg = new Message(chatText, false, newId);
                    saveChat.add(msg);
                    myAdapter.notifyDataSetChanged();
                    editText.setText("");
                }
            });
    }

    public void printCursor(Cursor c){
        Log.e("Database Version: ", String.valueOf(DatabaseHelper.VERSION_NUMBER));
        Log.e("Number of columns: ", String.valueOf(c.getColumnCount()));
        for (int i=0; i<c.getColumnCount(); i++){
            Log.e("Column " + i, c.getColumnName(i));
        }
        Log.e("Result: ", String.valueOf(c.getCount()));

        int idIndex = c.getColumnIndex(DatabaseHelper.COLUMN_ID);
        int chatIndex = c.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE);
        int chatTypeIndex = c.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_TYPE);
        c.moveToFirst();
        while(!c.isAfterLast()){
            Long id = c.getLong(idIndex);
            String chat = c.getString(chatIndex);
            String chatType = c.getString(chatTypeIndex);

            Log.e("ID: ", String.valueOf(id));
            Log.e("Message: ", chat);
            Log.e("Sent: ", chatType);

            c.moveToNext();
        }
    }

    class Message{
        public Message(String m, boolean send, long id){
            message = m;
            isSend = send;
        }
        public boolean isSend;
        public String message;
    }

    class MyListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return saveChat.size();
        }

        @Override
        public Message getItem(int position) {
            return saveChat.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatRoomActivity.this.getLayoutInflater();
            View result;
            if (!getItem(position).isSend){
                result = inflater.inflate(R.layout.chat_receive, null);
            }else{
                result = inflater.inflate(R.layout.chat_send,null);
            }
            TextView message = (TextView) result.findViewById(R.id.messageText);
            message.setText(getItem(position).message);
            return result;
        }
    }
}