package com.example.androidstudy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.androidstudy.R;
import com.example.androidstudy.activity.CourseDetailActivity;
import com.example.androidstudy.adapter.AdViewPaperAdapter;
import com.example.androidstudy.adapter.CourseAdapter;
import com.example.androidstudy.entity.AdImage;
import com.example.androidstudy.entity.Course;
import com.example.androidstudy.entity.Exer;
import com.example.androidstudy.utils.HttpsUtil;
import com.example.androidstudy.utils.NetworkUtils;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class MainFragment extends Fragment implements ViewPager.OnPageChangeListener {
    //广告轮播相关
    public static final int MSG_AD_ID = 1;  //广告自动滑动的id

    private ViewPager viewPager;
    private TextView tvDesc;                //图片的描述
    private LinearLayout llPoint;           //指示器的布局

    private List<AdImage> adImages;         //数据
    private List<ImageView> imageViews;     //图片的集合
    private int lastPos;                    //之前的位置

    //课程相关
    private RecyclerView rvCourse;
    private List<Course> courses;

    public MainFragment(){}

   private static MainFragment mainf;
   public static MainFragment getMainFragment(){
       if (mainf == null){
           mainf = new MainFragment();
       }
       return mainf;
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);

//        initTool(view);
        
        initAdData();

        initAdView(view);

        initIndicator(view);
        
        initCourses();

        rvCourse = view.findViewById(R.id.rv_course);

        initCourseView(courses);

        loadCourseByNet();

        loadCourseByOkHttp();

        lastPos = 0;
        llPoint.getChildAt(0).setEnabled(true);
        tvDesc.setText(adImages.get(0).getDesc());
        viewPager.setAdapter(new AdViewPaperAdapter(imageViews));

        adHandler = new AdHandler(viewPager);
        adHandler.sendEmptyMessageDelayed(MSG_AD_ID,5000);

        return view;
    }

    private void loadCourseByOkHttp() {
        // 1. 创建一个request对象，装载url、header等request头
        Request request = new Request.Builder()
                .url("https://www.fastmock.site/mock/b46332ceba020b46458f016deac2c275/course.json/chapter")
                .addHeader("Accept","application/json")
                .method("GET",null)
                .build();

        // 2. 采用okHttp的enqueue异步方式发送请求，使用callback回调处理响应
        HttpsUtil.handleSSLHandshakeByOkHttp().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //处理请求失败信息
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        String json = response.body().string();
                        final List<Course> courses = JSON.parseArray(json,Course.class);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initCourseView(courses);
                        }
                    });
                }
            }
        });
    }


    private static class CourseHandler extends Handler{
       private WeakReference<MainFragment> ref;

       public CourseHandler(MainFragment fragment){
           this.ref = new WeakReference<>(fragment);
       }

       @Override
       public void handleMessage(Message message){
           MainFragment target = ref.get();

           if (message.what == MSG_AD_ID){
               List<Course> courses = (List<Course>) message.obj;
               if (courses != null){
                   target.initCourseView(courses);
               }else {
                   Toast.makeText(target.getContext(),"null",Toast.LENGTH_SHORT).show();
               }
           }
       }
    }

    private Handler courseHandler = new CourseHandler(this);
    private void loadCourseByNet() {
       new Thread(new Runnable() {
           @Override
           public void run() {
               String json = NetworkUtils.get("https://www.fastmock.site/mock/b46332ceba020b46458f016deac2c275/course.json/chapter");
               List<Course> courses = JSON.parseArray(json,Course.class);
               if (courses != null){
                   Message msg = new Message();
                   msg.what = MSG_AD_ID;
                   msg.obj = courses;
                   adHandler.sendMessage(msg);
               }
           }
       }).start();
    }

    private void initTool(View view) {
        Toolbar toolbar = view.findViewById(R.id.title_toolbar);
        toolbar.setTitle("首页");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void initCourseView(final List<Course> courses) {
        final Course course = new Course();
        CourseAdapter adapter = new CourseAdapter(courses);
        rvCourse.setLayoutManager(new GridLayoutManager(getContext(),2));
        rvCourse.setAdapter(adapter);

        adapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Course course = courses.get(position);
                Intent intent = new Intent(getContext(), CourseDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("course",course);
                intent.putExtras(bundle);
//                intent.putExtra("id", course.getId());
                intent.putExtra("title", course.getTitle());
//                getContext().startActivity(intent);
                startActivity(intent);
            }
        });
    }

    private void initCourses() {
        courses = new ArrayList<>();
        try {
            InputStream input = getResources().getAssets().open("chapter_intro.json");
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            String str = result.toString(StandardCharsets.UTF_8.name());
            courses = JSON.parseArray(str, Course.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initIndicator(View view) {
        llPoint = view.findViewById(R.id.ll_point);

        View pointView;
        for (int i = 0; i < adImages.size(); i++){
            pointView = new View(getContext());
            pointView.setBackgroundResource(R.drawable.indicator_bg);
            pointView.setEnabled(false);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(16,16);
            if (i != 0){
                params.leftMargin = 10;
            }
            llPoint.addView(pointView,params);
        }
    }

    private void initAdView(View view) {
       tvDesc = view.findViewById(R.id.tv_desc);

       viewPager = view.findViewById(R.id.vp_banner);
       viewPager.addOnPageChangeListener(this);

       imageViews = new ArrayList<>();

       for (int i = 0;i < adImages.size(); i++){
           AdImage adImage = adImages.get(i);

           ImageView iv = new ImageView(getContext());
           if("banner_1".equals(adImage.getName())){
               iv.setBackgroundResource(R.drawable.banner_1);
           }else if ("banner_2".equals(adImage.getName())){
               iv.setBackgroundResource(R.drawable.banner_2);
           }else if ("banner_3".equals(adImage.getName())){
               iv.setBackgroundResource(R.drawable.banner_3);
           }
           imageViews.add(iv);
       }

    }

    private void initAdData() {
       adImages = new ArrayList<>();
       for (int i = 0;i < 3;i++){
           AdImage adImage = new AdImage();
           adImage.setId(i + 1);
           switch (i){
               case 0:
                   adImage.setName("banner_1");
                   adImage.setDesc("the one");
                   break;
               case 1:
                   adImage.setName("banner_2");
                   adImage.setDesc("the two");
                   break;
               case 2:
                   adImage.setName("banner_3");
                   adImage.setDesc("the three");
                   break;
               default:
                   break;
           }
           adImages.add(adImage);
       }
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
      int currentPos = i % adImages.size();
      tvDesc.setText(adImages.get(currentPos).getDesc());

      llPoint.getChildAt(lastPos).setEnabled(false);
      llPoint.getChildAt(currentPos).setEnabled(true);
      lastPos = currentPos;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private AdHandler adHandler;
   private static class AdHandler extends Handler {
       private WeakReference<ViewPager> reference;

       public AdHandler(ViewPager viewPager){
           reference = new WeakReference<>(viewPager);
       }

       @Override
       public void handleMessage(Message msg){
           super.handleMessage(msg);

           ViewPager viewPager = reference.get();
           if (viewPager == null){
               return;
           }
           if (msg.what == MSG_AD_ID){
               viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
               sendEmptyMessageDelayed(MSG_AD_ID,5000);
           }
       }
   }

}
