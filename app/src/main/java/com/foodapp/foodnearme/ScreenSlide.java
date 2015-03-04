package com.foodapp.foodnearme;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ScreenSlide extends Fragment {

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_splash, container, false);

        ImageView img=(ImageView)rootView.findViewById(R.id.slideImage);
        img.setImageResource(this.getArguments().getInt("img"));

        TextView tv=(TextView)rootView.findViewById(R.id.txtEmail);
        tv.setText(this.getArguments().getString("email"));

        Button login=(Button)rootView.findViewById(R.id.btn_Login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  if(MobileNoEdi.getText().equals())
                getActivity().startActivity(new Intent(getActivity(),MainActivity.class));
                getActivity().finish();
            }
        });

        return rootView;
    }
}