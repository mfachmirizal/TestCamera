package com.mfachmirizal.test.testcamera;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;
import com.mfachmirizal.test.testcamera.util.QuickstartPreferences;
import com.mfachmirizal.test.testcamera.util.TetanggakuGetIPList;

/**
 * Created by Edwin on 15/02/2015.
 */
public class Tab3 extends Fragment {
    //Spinner spinIP;
    EditText edObName;
    EditText edIP;
    Button butSet;
    Button butTest;
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_3,container,false);
//
//        edObName = (EditText) v.findViewById(R.id.edObName);
//        edIP = (EditText) v.findViewById(R.id.edIP);
//
//        //spinIP = (Spinner) v.findViewById(R.id.spinIP);
//
//        butSet = (Button) v.findViewById(R.id.butSet);
//        butTest = (Button) v.findViewById(R.id.butTest);
//
//        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
//
//
//        if (sharedPreferences.getString(QuickstartPreferences.OB_NAME, "") != "" ) {
//            edObName.setText(sharedPreferences.getString(QuickstartPreferences.OB_NAME, ""));
//        }
//        if (sharedPreferences.getString(QuickstartPreferences.IP_SERVER, "") != "" ) {
//            edIP.setText(sharedPreferences.getString(QuickstartPreferences.IP_SERVER, ""));
//        }
///*
//        String[] spinnerArray = {"awwwwww","uuuuuuuuu","test"}
//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, spinnerArray); //selected item will look like a spinner set from XML
//        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinIP.setAdapter(spinnerArrayAdapter);*/
//
//
//        butSet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                sharedPreferences.edit().putString(QuickstartPreferences.OB_NAME, edObName.getText().toString()).apply();
//                sharedPreferences.edit().putString(QuickstartPreferences.IP_SERVER, edIP.getText().toString()).apply();
//                Toast.makeText(getActivity().getApplicationContext(),"Server telah di set !", Toast.LENGTH_LONG).show();
//
//                //Toast.makeText(getActivity().getApplicationContext(), "Halo : " + edObName.getText().toString() + " : " + spinIP.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
////                try {
////                   String[] testList =  new TetanggakuGetIPList().getLocalIpAddress2();
////
////                   for(String zz : testList) {
////                        Log.i("RETURN",zz);
////                   }
////                } catch (Throwable x) {
////                    Log.e("ERR-TAG","Error : "+x.getMessage());
////                }
//            }
//        });
//
//        butTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Toast.makeText(getActivity().getApplicationContext(), "SReference : " + sharedPreferences.getString(QuickstartPreferences.OB_NAME, "")+
//                        " <> "+sharedPreferences.getString(QuickstartPreferences.IP_SERVER, ""), Toast.LENGTH_LONG).show();
//            }
//        });
//


        return v;
    }


}