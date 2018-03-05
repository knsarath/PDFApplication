package com.app.pdf.pdfmetadata;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.github.barteksc.pdfviewer.PDFView;

public class MainActivity extends AppCompatActivity {
    private PDFView mPdfView;
    private View mView;
    private float mIconPointLeft = 0.62f;
    private float mIconPointTop = 0.17f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPdfView = findViewById(R.id.pdfView);
        loadPDF();
        drawIcon();
    }

    private void drawIcon() {
        final LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        mView = layoutInflater.inflate(R.layout.marker_view, null);
        mView.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
        mView.setVisibility(View.GONE);
        mView.setOnClickListener(view -> new AlertDialog.Builder(MainActivity.this)
                .setTitle("Info")
                .setMessage("You clicked on info button!!")
                .setPositiveButton("OK", this::dismiss)
                .setNegativeButton("CANCEL", this::dismiss)
                .show());
        mPdfView.addView(mView);
    }

    private void dismiss(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
    }

    private void loadPDF() {
        mPdfView.fromAsset("map.pdf")
                .onDraw((canvas, pageWidth, pageHeight, displayedPage) -> {
                    float scale = mPdfView.getZoom();
                    Rect clipBounds = canvas.getClipBounds();
                    float newHeight = canvas.getHeight() * scale;
                    float newWidth = canvas.getWidth() * scale;
                    float pointXinNewCanvas = (newWidth * mIconPointLeft) - clipBounds.left - (mView.getWidth() * 0.5f);
                    float pointYInNewCanvas = (newHeight * mIconPointTop) - clipBounds.top - (mView.getHeight() * 0.5f);
                    mView.animate().x(pointXinNewCanvas).y(pointYInNewCanvas).setDuration(0).start();
                })
                .onLoad(nbPages -> mView.setVisibility(View.VISIBLE))
                .load();
    }
}
