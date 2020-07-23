package com.mustang4w.wang.photopickproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.mustang4w.wang.photopickproject.adapter.PhotoGridAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wangym
 */
public class MainActivity extends AppCompatActivity {

    private List<LocalMedia> localMediaList = new ArrayList<>();
    private PhotoGridAdapter photoGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        initData();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        photoGridAdapter = new PhotoGridAdapter(this, localMediaList);
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
        public void onShowBigPhotoListener(int position) {
            Log.d("wymmmmmmmmmmm", "onShowBigPhotoListener(PhotoItemBean bean)");
            showBigPhoto(position);
        }
    };

    PhotoGridAdapter.OnDeleteClickListener onPhotoDeleteClickListener = this::deletePhotoDialog;

    /**
     * PictureSelector 照片选择器组件
     * {@link com.luck.picture.lib.PictureSelector}
     */
    private void showPictureSelector() {
        PictureSelector.create(this)
                //所有类型
                .openGallery(PictureMimeType.ofAll())
                .imageEngine(GlideEngine.createGlideEngine())
                //是否压缩
//                .isCompress(true)
                //最小压缩尺寸
//                .minimumCompressSize(0)
                //压缩质量 0 - 100
//                .compressQuality(50)
                //压缩是否保持透明通道
//                .compressFocusAlpha(true)
                //压缩文件保存地址
//                .compressSavePath(getCacheDir().getPath())
                //开启原图选项
//                .isOriginalImageControl(true)
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        Log.d("wymmmmmmmmm", "result");
                        int preSize = localMediaList.size() - 1;
                        localMediaList.addAll(result);
                        photoGridAdapter.notifyItemRangeChanged(preSize, result.size());
                    }

                    @Override
                    public void onCancel() {
                        Log.d("wymmmmmmmmm", "onCancel");
                    }
                });
    }

    /**
     * 展示大图
     *
     * @param position 选中位置
     */
    private void showBigPhoto(int position) {
        PictureSelector.create(this)
                .themeStyle(R.style.picture_default_style)
                .isNotPreviewDownload(true)
                .imageEngine(GlideEngine.createGlideEngine())
                .openExternalPreview(position, localMediaList);
    }

    /**
     * 确认删除当前图片弹窗
     *
     * @param bean {@link LocalMedia}
     */
    private void deletePhotoDialog(LocalMedia bean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否确认删除当前图片");
        builder.setPositiveButton("确认", (dialog, which) -> {
            int currentPosition = localMediaList.indexOf(bean);
            localMediaList.remove(bean);
            photoGridAdapter.notifyItemRemoved(currentPosition);
            photoGridAdapter.notifyItemRangeChanged(currentPosition, localMediaList.size() - currentPosition);
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

}
