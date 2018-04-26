package com.hp.augmentedprint.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hp.augmentedprint.pdfmetadata.R;
import com.hp.augmentedprint.schema.StoredQrCodeDetail;
import com.hp.augmentedprint.schema.StoredQrCodeDetails;

import java.util.ArrayList;

/**
 * Created on 25/4/18 by aparna .
 */
class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private ArrayList<StoredQrCodeDetail> mList;
    cardViewClickLister mCardViewClickLister;

    public GalleryAdapter(Context context, StoredQrCodeDetails storedData) {
        mCardViewClickLister = (cardViewClickLister) context;
        if (storedData != null) {
            mList = storedData.getStoredQrCodeDetails();
        } else
            mList = null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_gallery_item
                , parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StoredQrCodeDetail QrCodeDetail = mList.get(position);
//        holder.mQrCodeImage =;
        holder.mQrDeCode.setText(QrCodeDetail.getDecodeData());
        holder.mQrCodedDate.setText(QrCodeDetail.getScannedDate());
        holder.mContainer.setOnClickListener(v -> {
            if (mCardViewClickLister != null) {
                mCardViewClickLister.onCardViewClick(QrCodeDetail.getDecodeData());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mQrCodeImage;
        TextView mQrDeCode;
        TextView mQrCodedDate;
        LinearLayout mContainer;

        ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(View View) {
            mQrCodeImage = View.findViewById(R.id.qr_scanneg_image_image_view);
            mQrDeCode = View.findViewById(R.id.gr_scanned_code_text_view);
            mQrCodedDate = View.findViewById(R.id.gr_code_scanned_date_text_view);
            mContainer = View.findViewById(R.id.qr_scanned_data_container);
        }
    }

    interface cardViewClickLister {
        void onCardViewClick(String qrDecode);
    }
}
