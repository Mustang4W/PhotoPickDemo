package com.mustang4w.wang.photopickproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wangym
 */
public class MainActivity extends AppCompatActivity {

    private List<PhotoItemBean> photoItemBeanList = new ArrayList<>();
    private PhotoGridAdapter photoGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        initData();
    }

    private void initRecyclerView() {
        photoGridAdapter = new PhotoGridAdapter(this, photoItemBeanList);
        photoGridAdapter.setOnItemClickListener(onPhotoGridClickListener);
        photoGridAdapter.setOnDeleteClickListener(onPhotoDeleteClickListener);
        RecyclerView recyclerPhoto = findViewById(R.id.recycler_photo);
        recyclerPhoto.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        recyclerPhoto.setAdapter(photoGridAdapter);
    }

    /**
     * 初始化数据，为列表的图片选择器增加数据
     */
    private void initData() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_photo_list_picker);
        photoItemBeanList.add(new PhotoItemBean("", "", bitmap, false));
    }

    /**
     * 图片列表点击监听
     */
    PhotoGridAdapter.OnItemClickListener onPhotoGridClickListener = new PhotoGridAdapter.OnItemClickListener() {
        @Override
        public void onAddPhotoListener() {
            Log.d("wymmmmmmmmmmm", "onAddPhotoListener()");
            showPictureSelector();
        }

        @Override
        public void onShowBigPhotoListener(PhotoItemBean bean) {
            Log.d("wymmmmmmmmmmm", "onShowBigPhotoListener(PhotoItemBean bean)");
        }
    };

    PhotoGridAdapter.OnDeleteClickListener onPhotoDeleteClickListener = new PhotoGridAdapter.OnDeleteClickListener() {
        @Override
        public void onPhotoDeleteClickListener(PhotoItemBean bean) {
            deletePhotoDialog(bean);
        }
    };

    /**
     * PictureSelector 照片选择器组件
     * {@link com.luck.picture.lib.PictureSelector}
     */
    private void showPictureSelector() {
        PictureSelector.create(this)
                //所有类型
                .openGallery(PictureMimeType.ofAll())
                .loadImageEngine(GlideEngine.createGlideEngine())
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        Log.d("wymmmmmmmmm", "result");
                        for (LocalMedia media : result) {
                            File photoFile = new File(media.getPath());
                            int preSize = photoItemBeanList.size() - 1;
                            photoItemBeanList.add(preSize, new PhotoItemBean(photoFile.getName(), photoFile.getPath(),
                                    BitmapFactory.decodeFile(photoFile.getPath()), true));
                            photoGridAdapter.notifyItemRangeChanged(preSize, result.size());
                        }
                    }

                    @Override
                    public void onCancel() {
                        Log.d("wymmmmmmmmm", "onCancel");
                    }
                });
    }

    /**
     * 确认删除当前图片弹窗
     *
     * @param bean {@link PhotoItemBean}
     */
    private void deletePhotoDialog(PhotoItemBean bean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否确认删除当前图片");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                photoGridAdapter.notifyItemRemoved(photoItemBeanList.indexOf(bean));
                photoItemBeanList.remove(bean);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

}
