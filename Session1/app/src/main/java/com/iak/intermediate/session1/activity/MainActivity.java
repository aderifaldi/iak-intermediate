package com.iak.intermediate.session1.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.iak.intermediate.session1.R;
import com.iak.intermediate.session1.app.adapter.MemberListAdapter;
import com.iak.intermediate.session1.app.adapter.MemberLocalAdapter;
import com.iak.intermediate.session1.app.api.ApiGetIAKMember;
import com.iak.intermediate.session1.app.model.api.Member;
import com.iak.intermediate.session1.app.model.local.MemberLocal;
import com.iak.intermediate.session1.app.util.ConnectionDetector;
import com.iak.intermediate.session1.app.util.sqlite.SQLiteQuery;
import com.radyalabs.irfan.util.AppUtility;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog loading;
    private TextView txt_title;
    private ImageView img_logo;
    private RecyclerView list_member;
    private LinearLayoutManager linearLayoutManager;
    private MemberListAdapter adapterAPI;
    private MemberLocalAdapter adapterLocal;

    private SQLiteQuery sqLiteQuery;
    private MemberLocal memberLocal;

    private ConnectionDetector connectionDetector;
    private boolean isInternetConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteQuery = new SQLiteQuery(getApplicationContext());
        memberLocal = new MemberLocal();

        connectionDetector = new ConnectionDetector(getApplicationContext());

        loading = new ProgressDialog(this);
        loading.show();

        txt_title = (TextView) findViewById(R.id.txt_title);
        img_logo = (ImageView) findViewById(R.id.img_logo);
        list_member = (RecyclerView) findViewById(R.id.list_member);

        linearLayoutManager = new LinearLayoutManager(this);
        list_member.setLayoutManager(linearLayoutManager);

        adapterAPI = new MemberListAdapter(this);
        adapterLocal = new MemberLocalAdapter(this);

        isInternetConnected = connectionDetector.isInternetConnected();

        if (isInternetConnected){
            list_member.setAdapter(adapterAPI);

            getDataFromAPI();
            AppUtility.logD("MainActivity", "internet connected");
        }else {
            list_member.setAdapter(adapterLocal);

            getDataFromLocalDB();
            AppUtility.logD("MainActivity", "internet not connected");
        }

        adapterAPI.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Member member = adapterAPI.getListMember().get(position);
                Intent intent = new Intent(getApplicationContext(), MemberDetailActivity.class);
                intent.putExtra("member", member);
                startActivity(intent);
            }
        });


    }

    /**
     * GET DATA FROM LOCAL DB
     */
    private void getDataFromLocalDB(){

        if (loading != null){
            loading.dismiss();
            loading = null;
        }

        adapterLocal.getListMember().clear();

        sqLiteQuery.open();
        List<MemberLocal> memberLocal = sqLiteQuery.selectMember();
        sqLiteQuery.close();

        adapterLocal.getListMember().addAll(memberLocal);
        adapterLocal.notifyDataSetChanged();

    }

    /**
     * GET DATA FROM API
     */
    private void getDataFromAPI(){

        ApiGetIAKMember  apiGetIAKMember = new ApiGetIAKMember(getApplicationContext()) {
            @Override
            public void onFinishRequest(boolean success, String returnItem) {

                if (loading != null){
                    loading.dismiss();
                    loading = null;
                }

                if (success){
                    if (data != null){
                        if (data.getAlert().getAlert_code() == 0){

                            sqLiteQuery.open();
                            sqLiteQuery.clearTableMember();

                            txt_title.setText(data.getSubject() + "\n" + data.getVenue() + "\n" + data.getCity());

                            Picasso.with(context).
                                    load(data.getLogo()).
                                    into(img_logo);

                            for (Member member : data.getMember()){
                                adapterAPI.getListMember().add(member);
                                adapterAPI.notifyDataSetChanged();

                                /**
                                 * INSERT DATA TO LOCAL DB
                                 */
                                memberLocal.setName(member.getGeneral_info().getName());
                                memberLocal.setAddress(member.getGeneral_info().getAddress());
                                memberLocal.setPhoto(member.getGeneral_info().getPhoto());

                                sqLiteQuery.insertMember(memberLocal);
                            }

                            sqLiteQuery.close();

                        }
                    }
                }
            }
        };
        apiGetIAKMember.executeAjax();
    }

}
