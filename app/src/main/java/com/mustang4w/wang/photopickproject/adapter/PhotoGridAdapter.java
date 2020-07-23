package com.mustang4w.wang.photopickproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luck.picture.lib.entity.LocalMedia;
import com.mustang4w.wang.photopickproject.R;

import java.util.List;

/**
 * 图片选择器列表适配器
 *
 * @author Wangym
 */
public class PhotoGridAdapter extends RecyclerView.Adapter<PhotoGridAdapter.InnerViewHolder> {

    private Context mContext;
    private List<LocalMedia> mList;
    private OnItemClickListener mOnItemClickListener;
    private OnDeleteClickListener mOnDeleteClickListener;

    private static final int TYPE_ADD_PHOTO = 1;
    private static final int TYPE_PHOTO = 2;

    public PhotoGridAdapter(Context context, List<LocalMedia> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_photo_grid, parent, false);
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_PHOTO) {
            holder.imgDelete.setVisibility(View.VISIBLE);
            Glide.with(holder.itemView.getContext())
                    .load(mList.get(position).getPath())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imgPhoto);
        } else {
            holder.imgDelete.setVisibility(View.GONE);
            Glide.with(mContext)
                    .load(R.drawable.icon_photo_list_picker)
                    .into(holder.imgPhoto);
        }
        holder.itemView.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                if (holder.getItemViewType() == TYPE_PHOTO) {
                    mOnItemClickListener.onShowBigPhotoListener(position);
                } else {
                    mOnItemClickListener.onAddPhotoListener();
                }
            }
        });
        holder.imgDelete.setOnClickListener(v -> {
            if (mOnDeleteClickListener != null) {
                mOnDeleteClickListener.onPhotoDeleteClickListener(mList.get(position));
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        mOnDeleteClickListener = onDeleteClickListener;
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == mList.size() ? TYPE_ADD_PHOTO : TYPE_PHOTO;
    }

    class InnerViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhoto, imgDelete;

        InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            imgDelete = itemView.findViewById(R.id.img_delete);
        }
    }

    public interface OnItemClickListener {
        /**
         * 新增图片
         */
        void onAddPhotoListener();

        /**
         * 展示图片大图
         *
         * @param position 位置
         */
        void onShowBigPhotoListener(int position);
    }

    public interface OnDeleteClickListener {
        /**
         * 删除图片
         *
         * @param bean 图片实体{@link LocalMedia}
         */
        void onPhotoDeleteClickListener(LocalMedia bean);
    }
}
