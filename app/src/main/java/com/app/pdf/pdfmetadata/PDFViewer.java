package com.app.pdf.pdfmetadata;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.github.barteksc.pdfviewer.PDFView;


/**
 * Created by sarath on 1/3/18.
 */

public class PDFViewer extends PDFView {
    private static final String TAG = "PDFViewer";
    private Paint mPaint = new Paint();
    private Bitmap mBitmap;

    /**
     * Construct the initial view
     *
     * @param context
     * @param set
     */
    public PDFViewer(Context context, AttributeSet set) {
        super(context, set);
    }

    @Override
    public boolean performClick() {
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
// Moves the canvas before drawing any element
        float currentXOffset = getCurrentXOffset();
        float currentYOffset = getCurrentYOffset();
        canvas.translate(currentXOffset, currentYOffset);

/*
        mPaint.setColor(Color.BLACK);
        if (mBitmap == null)
            mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_info_outline_black_24dp);
        canvas.drawBitmap(mBitmap, canvas.getWidth() / 2, canvas.getHeight() / 2, mPaint);
*/

        // Restores the canvas position
        canvas.translate(-currentXOffset, -currentYOffset);
    }
}
