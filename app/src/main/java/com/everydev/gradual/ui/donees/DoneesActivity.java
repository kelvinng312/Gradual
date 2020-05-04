package com.everydev.gradual.ui.donees;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.everydev.gradual.R;
import com.everydev.gradual.data.network.pojo.Donee;
import com.everydev.gradual.ui.base.BaseActivity;
import com.everydev.gradual.ui.main.MainActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DoneesActivity extends BaseActivity implements DoneesMvpView {
    private RecyclerView rc_list;
    private MyAdapter m_adapter;
    public Long userId;
    static public DoneesActivity self;

    @Inject
    DoneesMvpPresenter<DoneesMvpView> mPresenter;

    public static Intent getStartIntent(Context context, Long userId) {
        Intent intent = new Intent(context, DoneesActivity.class);
        intent.putExtra("USERID", userId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donees);

        getActivityComponent().inject(this);
        mPresenter.onAttach(DoneesActivity.this);
        setUp();
        Intent intent = getIntent();
        if (intent != null) {
            this.userId = intent.getExtras().getLong("USERID");
        }
        // displayDonees
        mPresenter.fetchDonees();
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void displayDonees(List<Donee> donees) {
        self = this;
        rc_list = findViewById(R.id.donees_list);

        ArrayList<Donee> list =  new ArrayList<Donee>();
        list.clear();
        for (Donee donee : donees) {
            if(this.userId == donee.getID())
                continue;
            list.add(donee);
        }

        try {
            m_adapter = new MyAdapter(self, list);
            rc_list.setAdapter(m_adapter);
            rc_list.setLayoutManager(new GridLayoutManager(this, 2));
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

class MyAdapter extends RecyclerView.Adapter
{
    DoneesActivity _context;

    List<Donee> m_list;

    public MyAdapter(DoneesActivity context, List<Donee> list) {
        this._context = context;
        this.m_list = list;
    }

    @Override
    public MyItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_item, parent, false);
        return new MyItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final MyItemViewHolder vh = (MyItemViewHolder) holder;

        final Donee item = this.m_list.get(position);

        final String name =  item.getName();
        final String pubkey =  item.getPubkey();
        final String avatar;
        final Long sendUserId = item.getID();
        if(sendUserId == _context.userId)
        {
            return;
        }

        // TODO: image loading
        String av =  item.getAvatar();
        if(av == null || av == "") {
            av = "https://getgradual.com/images/default-avatar.jpg";
        }
        avatar = av;

        vh.tv_name.setText(name);
        Glide.with(vh.iv_image).load(avatar).into(vh.iv_image);

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    _context.startActivity(MainActivity.getStartIntent(_context, name, avatar, pubkey));
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.m_list.size();
    }
}

class MyItemViewHolder extends RecyclerView.ViewHolder {

    public View itemView;
    public ImageView iv_image;
    public TextView tv_name;
    public MyItemViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        this.iv_image = itemView.findViewById(R.id.imageView);
        this.tv_name = itemView.findViewById(R.id.tbName);
    }
}
