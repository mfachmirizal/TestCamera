package com.mfachmirizal.test.testcamera;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;

/**
 * Created by Edwin on 15/02/2015.
 */
public class Tab3 extends Fragment {
    Spinner spinIP;
    EditText edObName;
    Button butSet;
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_3,container,false);

        edObName = (EditText) v.findViewById(R.id.edObName);

        spinIP = (Spinner) v.findViewById(R.id.spinIP);

        butSet = (Button) v.findViewById(R.id.butSet);

        String[] spinnerArray = {"awwwwww","uuuuuuuuu","test"};


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinIP.setAdapter(spinnerArrayAdapter);


        butSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),"Halo : "+edObName.getText().toString()+" : "+spinIP.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
            }
        });



        return v;
    }
}