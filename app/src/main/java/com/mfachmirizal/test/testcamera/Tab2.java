package com.mfachmirizal.test.testcamera;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.image.SmartImageView;

/**
 * Created by Edwin on 15/02/2015.
 */
public class Tab2 extends Fragment {
 //   SmartImageView siv_gambar;
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_2,container,false);

   //     siv_gambar = (SmartImageView) v.findViewById(R.id.siv_gambar);
//
//        siv_gambar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                siv_gambar.setImageUrl("http://192.168.1.168:8585/obdev/web/com.tripad.tetanggaku.security.mobile/dataimages/Tulips.jpg");
//            }
//        });

        return v;
    }
}