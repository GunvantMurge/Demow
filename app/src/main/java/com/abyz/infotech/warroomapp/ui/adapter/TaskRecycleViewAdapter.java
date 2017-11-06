package com.abyz.infotech.warroomapp.ui.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abyz.infotech.warroomapp.R;
import com.abyz.infotech.warroomapp.ui.fragment.TaskDetailsFragment;
import com.abyz.infotech.warroomapp.ui.model.TaskModel;

import java.util.List;

public class TaskRecycleViewAdapter extends RecyclerView.Adapter<TaskRecycleViewAdapter.ItemViewHolder> {

    private Activity mActivity;
    private List<TaskModel> taskModels;

    public TaskRecycleViewAdapter(List<TaskModel> taskModel, Activity activity) {
        this.taskModels = taskModel;
        this.mActivity = activity;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_task_list, viewGroup, false);

        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int position) {

        itemViewHolder.mEvantName.setText(taskModels.get(position).getEt_evnt_name());
        itemViewHolder.mTaskName.setText(taskModels.get(position).getTskName());

        Log.d("position", "position-----" + position);
        switch (position)
        {
            case 0:
                itemViewHolder.srno.setText("F");
                break;
            case 1:
                itemViewHolder.srno.setText("S");
                break;
            case 2:
                itemViewHolder.srno.setText("T");
                break;
            case 3:
                itemViewHolder.srno.setText("F");
                break;
            case 4:
                itemViewHolder.srno.setText("F");
                break;
            case 5:
                itemViewHolder.srno.setText("S");
                break;
            case 6:
                itemViewHolder.srno.setText("S");
                break;
            case 7:
                itemViewHolder.srno.setText("E");
                break;
            case 8:
                itemViewHolder.srno.setText("N");
                break;
            case 9:
                itemViewHolder.srno.setText("T");
                break;
            case 10:
                itemViewHolder.srno.setText("E");
                break;
            case 11:
                itemViewHolder.srno.setText("T");
                break;
            case 12:
                itemViewHolder.srno.setText("T");
                break;
            case 13:
                itemViewHolder.srno.setText("F");
                break;
            case 14:
                itemViewHolder.srno.setText("F");
                break;
            case 15:
                itemViewHolder.srno.setText("S");
                break;
            case 16:
                itemViewHolder.srno.setText("T");
                break;
            case 17:
                itemViewHolder.srno.setText("E");
                break;
            case 18:
                itemViewHolder.srno.setText("N");
                break;
            case 19:
                itemViewHolder.srno.setText("T");
                break;
            case 20:
                itemViewHolder.srno.setText("T");
                break;
        }

    }

    @Override
    public int getItemCount() {
        return taskModels.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTaskName, mEvantName,srno;
        ImageView imageView_next;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTaskName = (TextView) itemView.findViewById(R.id.task_name);
            mEvantName = (TextView) itemView.findViewById(R.id.event_name);
            srno = (TextView) itemView.findViewById(R.id.srno);
            imageView_next = (ImageView) itemView.findViewById(R.id.imageview_next);
            imageView_next.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            android.support.v4.app.FragmentTransaction fragmentTransaction=((AppCompatActivity) mActivity).getSupportFragmentManager().beginTransaction();
            TaskDetailsFragment taskDetailsFragment = new TaskDetailsFragment();


            Bundle bundle = new Bundle();
            bundle.putInt("et_id", taskModels.get(getLayoutPosition()).getTaId());
            bundle.putString("task_name", taskModels.get(getLayoutPosition()).getTskName());

            bundle.putInt("layout_position",getPosition());
            //Log.d(TAG, "onClick " + getPosition() + " " + mItem);

            Log.d("taskModels", "getLayoutPosition <> " + taskModels.get(getLayoutPosition()).getTaId());

            taskDetailsFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, taskDetailsFragment);
            fragmentTransaction.addToBackStack("").commit();
        }
    }

}
