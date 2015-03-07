package com.foodapp.foodnearme;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.foodapp.foodnearme.utils.AppConstants;
import com.foodapp.foodnearme.utils.AppController;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class LoginFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String IS_LOGIN = "is_login";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private boolean isLogin = true;

    private OnFragmentInteractionListener mListener;

    private Context context;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFrag newInstance(String param1, String param2) {
        LoginFrag fragment = new LoginFrag(null);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    public LoginFrag(Context c) {
        context=c;
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            if(getArguments().containsKey(IS_LOGIN))
            {
                isLogin = getArguments().getBoolean(IS_LOGIN);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        Button LoginBtn = (Button) rootView.findViewById(R.id.NavigationButtonRight);

        if(!isLogin)
        {
            convertToCreateAccount(rootView);
        }
        AccountManager am = AccountManager.get(context);
        Account[] accounts = am.getAccounts();

        final Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Set<String> emailAdapter = new HashSet<String>();
        for (Account ac : accounts) {
            if(emailPattern.matcher(ac.name).matches())
            {
                emailAdapter.add(ac.name);
            }
        }

        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) rootView.findViewById(R.id.emailLogin);
        autoCompleteTextView.setAdapter(new ArrayAdapter(context,android.R.layout.simple_dropdown_item_1line,emailAdapter.toArray()));

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  if(MobileNoEdi.getText().equals())
                getActivity().startActivity(new Intent(getActivity(),MainActivity.class));
                getActivity().finish();
            }
        });
        final ViewSwitcher viewSwitcher = (ViewSwitcher) rootView.findViewById(R.id.SwitchAcSetup);
        final Button backbtn = (Button) rootView.findViewById(R.id.NavigationButtonLeft);
        final Button nextbtn = (Button) rootView.findViewById(R.id.NavigationButtonRight);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailinput = autoCompleteTextView.getText().toString().trim();
                if(emailinput.length()>0 && emailPattern.matcher(emailinput).matches()) {
                    if (viewSwitcher.getDisplayedChild() == 0) {
                        viewSwitcher.showNext();
                        ((Button) v).setText("Finish");
                        backbtn.setEnabled(true);
                        ((TextView) rootView.findViewById(R.id.topHeaderText)).setText(emailinput);
                    } else {
                        if(((EditText)rootView.findViewById(R.id.passwordEdt)).getText().toString().trim().length()>=3)
                        {
                            final Map<String,String> params = new HashMap<String, String>();
                            params.put("action","login");
                            params.put("email",emailinput);
                            params.put("pass",((EditText)rootView.findViewById(R.id.passwordEdt)).getText().toString().trim());

                            final ProgressDialog pgd = new ProgressDialog(getActivity());
                            StringRequest loginrequest = new StringRequest(Request.Method.POST,AppConstants.SERVER_URL,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.d("harsh",response);
                                            try {
                                                JSONObject obj = new JSONObject(response);
                                                if(obj.getBoolean("status"))
                                                {
                                                    SnackbarManager.show(Snackbar.with(getActivity()).text("success"));
                                                    getActivity().startActivity(new Intent(getActivity(),GCMRegAct.class));
                                                }
                                                else {
                                                    SnackbarManager.show(Snackbar.with(getActivity()).text(obj.getString("err")));
                                                }
                                            } catch (JSONException e) {
                                                SnackbarManager.show(Snackbar.with(getActivity()).text("Error in json"));
                                            }
                                            pgd.hide();
                                        }
                                    },new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("harsh",error.getMessage());
                                    pgd.hide();
                                }
                            }){
                                @Override
                                protected Map<String,String> getParams(){


                                    return params;
                                }
                            };
                               pgd.show();
                            AppController.getInstance().addToRequestQueue(loginrequest);
                        }
                        else {
                            ((EditText)rootView.findViewById(R.id.passwordEdt)).setError("Minimum 3 characters");
                        }
                    }
                }
                else {
                    autoCompleteTextView.setError("Enter Valid Email");
                }

            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewSwitcher.getDisplayedChild()==1)
                {
                    viewSwitcher.showPrevious();
                    backbtn.setEnabled(false);
                    nextbtn.setText("Next");
                    ((TextView) rootView.findViewById(R.id.topHeaderText)).setText("Account Setup");
                }
            }
        });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private void convertToCreateAccount(View v)
    {
        v.findViewById(R.id.nameLogin).setVisibility(View.VISIBLE);
        v.findViewById(R.id.numberLogin).setVisibility(View.VISIBLE);
        ((TextView)v.findViewById(R.id.switchRegisterText)).setText(getResources().getString(R.string.login_to_register_text));
        ((TextView)v.findViewById(R.id.infoTextview)).setText(getResources().getString(R.string.register_info_text));
    }

}
