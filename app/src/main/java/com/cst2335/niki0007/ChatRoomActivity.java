package com.cst2335.niki0007;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatRoomActivity extends AppCompatActivity {
    MyListAdapter myAdapter;

    ArrayList <Message> saveChat = new ArrayList < >();
    Button sendBtn;
    Button recBtn;
    EditText editText;
    TextView message;
    private Object MyListAdapter;

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
        
            sendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String chatText = editText.getText().toString();
                    saveChat.add(new Message(chatText, true));
                    myAdapter.notifyDataSetChanged();
                    editText.setText("");
                }
            });

            recBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String chatText = editText.getText().toString();
                    saveChat.add(new Message(chatText, false));
                    myAdapter.notifyDataSetChanged();
                    editText.setText("");
                }
            });


    }

    class Message{
        public Message(String m, boolean send){
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