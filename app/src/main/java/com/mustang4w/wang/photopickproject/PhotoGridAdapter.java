package com.mustang4w.wang.photopickproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 图片选择器列表适配器
 *
 * @author Wangym
 */
public class PhotoGridAdapter extends RecyclerView.Adapter<PhotoGridAdapter.InnerViewHolder> {

    private Context mContext;
    private List<PhotoItemBean> mList;
    private OnItemClickListener mOnItemClickListener;
    private OnDeleteClickListener mOnDeleteClickListener;

    public PhotoGridAdapter(Context context, List<PhotoItemBean> list) {
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
        if (mList.get(position).isPhoto()) {
            holder.imgDelete.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(mList.get(position).getBitmapResource())
                    .centerCrop()
                    .into(holder.imgPhoto);
        } else {
            holder.imgDelete.setVisibility(View.GONE);
            holder.imgPhoto.setImageResource(R.drawable.icon_photo_list_picker);
        }
        holder.itemView.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                if (mList.get(position).isPhoto()) {
                    mOnItemClickListener.onShowBigPhotoListener(mList.get(position));
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
        return mList.size();
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
         * @param bean 图片实体{@link PhotoItemBean}
         */
        void onShowBigPhotoListener(PhotoItemBean bean);
    }

    public interface OnDeleteClickListener {
        /**
         * 删除图片
         *
         * @param bean 图片实体{@link PhotoItemBean}
         */
        void onPhotoDeleteClickListener(PhotoItemBean bean);
    }
}
