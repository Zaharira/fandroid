package com.example.a5androidintern1.p1071_actionbaritems;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by Admin on 11-12-2015.
 */
public class TabFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.tabs, container, false);
    }
}