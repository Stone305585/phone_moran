package com.phone.moran.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.phone.moran.MainActivity;
import com.phone.moran.R;
import com.phone.moran.config.Constant;
import com.phone.moran.model.LocalMoods;
import com.phone.moran.model.LocalPaints;
import com.phone.moran.model.Mood;
import com.phone.moran.model.Paint;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartAnimActivity extends BaseActivity {

    @BindView(R.id.logo_iv)
    ImageView logoIv;
    @BindView(R.id.logo_LL)
    LinearLayout logoLL;

    Timer timer;
    @BindView(R.id.start_anim)
    ImageView startAnim;
    @BindView(R.id.finish_btn)
    TextView finishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_anim);
        ButterKnife.bind(this);

        timer = new Timer();

        initView();
        setListener();
    }

    @Override
    protected void initView() {
        super.initView();

        LocalPaints l = diskLruCacheHelper.getAsSerializable(Constant.LOCAL_MINE + userId);
        if(l == null)
            l = new LocalPaints();
        //获取本地的 local collect  为空则放入默认的画单  我的收藏
        if(l.getPaints() == null || l.getPaints().size() == 0) {
            ArrayList<Paint> paints = new ArrayList<>();
            Paint p = new Paint();
            p.setPaint_id(-1);
            p.setPaint_title("我的收藏");
            paints.add(p);
            l.setPaints(paints);
            diskLruCacheHelper.put(Constant.LOCAL_MINE + userId, l);
        }



        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        logoLL.setVisibility(View.GONE);
                        finishBtn.setVisibility(View.VISIBLE);


                        Glide.with(StartAnimActivity.this).load(R.drawable.app).asGif().into(startAnim);
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                startActivity(new Intent(StartAnimActivity.this, MainActivity.class));
                                finish();
                            }
                        }, 4688);
                    }
                });

            }
        }, 2000);

        //存放本地心情初始化
        LocalMoods lm = diskLruCacheHelper.getAsSerializable(Constant.LOCAL_MOOD + userId);

        if(lm == null) {
            lm = new LocalMoods();
            ArrayList<Mood> moodList = new ArrayList<>();

            Mood moodNu = new Mood();
            moodNu.setMood_id(Constant.MOOD_NU);
            moodNu.setMood_name("怒");
            moodNu.setRes_id(R.mipmap.mood_nu);
            moodList.add(moodNu);

            Mood moodSi = new Mood();
            moodSi.setMood_name("思");
            moodNu.setMood_id(Constant.MOOD_SI);
            moodSi.setRes_id(R.mipmap.mood_si);
            moodList.add(moodSi);

            Mood moodKong = new Mood();
            moodKong.setMood_id(Constant.MOOD_KONG);
            moodKong.setMood_name("恐");
            moodKong.setRes_id(R.mipmap.mood_kong);
            moodList.add(moodKong);

            Mood moodJing = new Mood();
            moodJing.setMood_id(Constant.MOOD_JING);
            moodJing.setMood_name("惊");
            moodJing.setRes_id(R.mipmap.mood_jing);
            moodList.add(moodJing);

            Mood moodYou = new Mood();
            moodYou.setMood_id(Constant.MOOD_YOU);
            moodYou.setMood_name("忧");
            moodYou.setRes_id(R.mipmap.mood_you);
            moodList.add(moodYou);

            Mood moodXi = new Mood();
            moodXi.setMood_id(Constant.MOOD_XI);
            moodXi.setMood_name("喜");
            moodXi.setRes_id(R.mipmap.mood_xi);
            moodList.add(moodXi);

            Mood moodBei = new Mood();
            moodBei.setMood_id(Constant.MOOD_BEI);
            moodBei.setMood_name("悲");
            moodBei.setRes_id(R.mipmap.mood_bei);
            moodList.add(moodBei);

            Mood moodWu = new Mood();
            moodWu.setMood_id(Constant.MOOD_WU);
            moodWu.setMood_name("空");
            moodWu.setRes_id(R.mipmap.mood_wu);
            moodList.add(moodWu);

            lm.setMoods(moodList);

            diskLruCacheHelper.put(Constant.LOCAL_MOOD + userId, lm);

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer!= null)
            timer.cancel();
    }

    @Override
    protected void setListener() {
        super.setListener();

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                startActivity(new Intent(StartAnimActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
