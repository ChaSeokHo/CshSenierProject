package com.csh.application.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.csh.application.model.PrefManager;
import com.csh.application.R;
import com.csh.application.contract.MainContract;
import com.csh.application.presenter.MainPresenter;
import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.TimetableView;

import java.util.ArrayList;

public class TimetableActivity extends AppCompatActivity implements MainContract.View {
    private static final int REQUEST_ADD = 1;
    public static final int REQUEST_EDIT = 2;
    private LinearLayout addBtn;
    private MainContract.UserActions mainPresenter;
    private Context context;

    private TimetableView timetable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        context = this;
        mainPresenter = new MainPresenter(this);
        mainPresenter.setPrefManager(PrefManager.getInstance());

        timetable = findViewById(R.id.timetable);
        timetable.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
                mainPresenter.selectSticker(idx,schedules);
            }
        });
        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.addMenuClick();
            }
        });

        mainPresenter.prepare();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ADD:
                if (resultCode == EditActivity.RESULT_OK_ADD) {
                    ArrayList<Schedule> item = (ArrayList<Schedule>) data.getSerializableExtra("schedules");
                    timetable.add(item);
                }
                break;
            case REQUEST_EDIT:
                if (resultCode == EditActivity.RESULT_OK_EDIT) {
                    int idx = data.getIntExtra("idx", -1);
                    ArrayList<Schedule> item = (ArrayList<Schedule>) data.getSerializableExtra("schedules");
                    timetable.edit(idx, item);
                } else if (resultCode == EditActivity.RESULT_OK_DELETE) {
                    int idx = data.getIntExtra("idx", -1);
                    timetable.remove(idx);
                }
                break;
        }
        mainPresenter.save(timetable.createSaveData());
    }

    @Override
    public void startEditActivityForAdd() {
        Intent i = new Intent(context, EditActivity.class);
        i.putExtra("allSchedules",timetable.getAllSchedulesInStickers());
        startActivityForResult(i, REQUEST_ADD);
    }

    @Override
    public void startEditActivityForEdit(int idx, ArrayList<Schedule> schedules) {
        Intent i = new Intent(this, EditActivity.class);
        i.putExtra("idx",idx);
        i.putExtra("mode", REQUEST_EDIT);
        i.putExtra("allSchedules", timetable.getAllSchedulesInStickersExceptIdx(idx));
        i.putExtra("schedules", schedules);
        startActivityForResult(i, REQUEST_EDIT);
    }

    @Override
    public void restoreTimetable(String data) {
        timetable.load(data);
    }

    @Override
    public void setDayHighlight(int day) {
        if(day > 0) timetable.setHeaderHighlight(day);
    }
}