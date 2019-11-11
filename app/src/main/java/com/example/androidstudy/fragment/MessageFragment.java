package com.example.androidstudy.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidstudy.R;

public class MessageFragment extends Fragment {
      private static MessageFragment messagef;

      public static MessageFragment getMessageFragment(){
          if (messagef == null){
              messagef = new MessageFragment();
          }
          return messagef;
      }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message,container,false);
        return view;
    }
}
