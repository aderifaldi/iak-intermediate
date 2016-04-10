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
import com.iak.intermediate.session1.app.api.ApiGetIAKMember;
import com.iak.intermediate.session1.app.model.Member;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog loading;
    private TextView txt_title;
    private ImageView img_logo;
    private RecyclerView list_member;
    private LinearLayoutManager linearLayoutManager;
    private MemberListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loading = new ProgressDialog(this);
        loading.show();

        txt_title = (TextView) findViewById(R.id.txt_title);
        img_logo = (ImageView) findViewById(R.id.img_logo);
        list_member = (RecyclerView) findViewById(R.id.list_member);

        linearLayoutManager = new LinearLayoutManager(this);
        adapter = new MemberListAdapter(this);

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Member member = adapter.getListMember().get(position);
                Intent intent = new Intent(getApplicationContext(), MemberDetailActivity.class);
                intent.putExtra("member", member);
                startActivity(intent);
            }
        });

        getData();

    }

    private void getData(){
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

                            txt_title.setText(data.getSubject() + "\n" + data.getVenue() + "\n" + data.getCity());

                            Picasso.with(context).
                                    load(data.getLogo()).
                                    into(img_logo);

                            for (Member member : data.getMember()){
                                adapter.getListMember().add(member);
                                adapter.notifyDataSetChanged();
                            }

                            list_member.setLayoutManager(linearLayoutManager);
                            list_member.setAdapter(adapter);

                        }
                    }
                }
            }
        };
        apiGetIAKMember.executeAjax();
    }

}
