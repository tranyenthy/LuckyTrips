package com.example.myfirstapp.luckybankonlinesystem.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myfirstapp.luckybankonlinesystem.Adapter.CardAdapter;
import com.example.myfirstapp.luckybankonlinesystem.Class.DepthZoomOutPageTransformer;
import com.example.myfirstapp.luckybankonlinesystem.Model.AccountModel;
import com.example.myfirstapp.luckybankonlinesystem.Model.CustomerModel;
import com.example.myfirstapp.luckybankonlinesystem.R;
import com.example.myfirstapp.luckybankonlinesystem.SplashScreenActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * A simple {@link MainFragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    private ViewPager2 viewPager2;
    private View v;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TextView hiTv;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.main_fragment, container, false);

        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        CustomerModel cusm = getActivity().getIntent().getExtras().getParcelable(SplashScreenActivity.USER_INFO_KEY);
        ArrayList<AccountModel> userAccounts = cusm.getAccounts();
        AccountModel primeAcc = userAccounts.get(0);
//        AccountModel savAcc = userAccounts.get(1);
        String primeAccNumber = primeAcc.getAccountNumber();
//        String savingAccNumber = savAcc.getAccountNumber();
        viewPager2 = v.findViewById(R.id.viewPager);
        viewPager2.setCurrentItem(R.layout.primary_card_view);
        viewPager2.setAdapter(new CardAdapter(getActivity()));
        viewPager2.setPageTransformer(new DepthZoomOutPageTransformer());

        String cusmName = cusm.getFullName().toString();
        hiTv = (TextView) getView().findViewById(R.id.tvHi);
        String yourTotal = getString(R.string.total_balance);
        String hello = "Hi " + cusmName + yourTotal;
        hiTv.setText(hello);


    }
}