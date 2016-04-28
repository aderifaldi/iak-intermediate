package com.iak.intermediate.session1.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.iak.intermediate.session1.R;
import com.iak.intermediate.session1.app.model.api.Member;

/**
 * Created by aderifaldi on 09-Apr-16.
 */
public class MemberDetailActivity extends AppCompatActivity {

    private Member member;
    private Bundle extras;

    private TextView txt_member_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);

        extras = getIntent().getExtras();
        member = (Member) extras.getSerializable("member");

        txt_member_name = (TextView) findViewById(R.id.txt_member_name);

        txt_member_name.setText(member.getGeneral_info().getName());

    }
}
