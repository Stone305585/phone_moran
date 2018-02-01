package com.phone.moran.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.phone.moran.R;

public class FoldingView extends FrameLayout{
	
	private static final String TAG = "com.example.foldingdemo.FoldingView";
	
	private static final int POLY_POINTS = 8;
	
	private static final int DEPTH_CONSTANT = 1500;
	
	private static final float SHADOW_APLHA = 0.8f;
	private static final float SHADOW_FACTOR = 0.5f;
	
	private int folds;
	
	private Paint paint;
	private Paint paintSolid,paintGradientShadow;
	private LinearGradient linearGradientShadow;
	private Matrix matrixShadowGradient;
	
	private int bitmapWidth;
	private int bitmapHeight;
	
	private int widthPerFold;
	private int heightPerFold;
			
	private float scaleFactor;
	private float foldFactor;
	private float translateFactor;
		
	private float translateWidth = 0f;
	private float translateWidthPerFold = 0f;
	private float foldDrawWidth;
	private float foldDrawHeight;
	
	private Bitmap bitmap;
	
	private boolean shouldDraw = true;	
	
	private Rect[] rects;
	private Matrix[] matrices;
	
	private float[] srcPoly;
	private float[] dstPoly;
	
	public FoldingView(Context context) {
		super(context);
		init();
	}
	
	public FoldingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init(){
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(1);	
		
		linearGradientShadow = new LinearGradient(0, 0,  SHADOW_FACTOR,0, Color.BLACK, Color.TRANSPARENT, TileMode.CLAMP);
		
		paintSolid = new Paint(Paint.ANTI_ALIAS_FLAG);
		paintGradientShadow = new Paint(Paint.ANTI_ALIAS_FLAG);
		paintGradientShadow.setStyle(Style.FILL);
		paintGradientShadow.setShader(linearGradientShadow);
		matrixShadowGradient = new Matrix();
		
	}
	
	private void prepare(){	
		
		rects = new Rect[folds];
		matrices = new Matrix[folds];
		
		bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.recyclerview_item);
		
		bitmapWidth = bitmap.getWidth();		
		bitmapHeight  = bitmap.getHeight();
		
		widthPerFold = Math.round((float)bitmapWidth /(float)folds);
		heightPerFold = bitmapHeight;				
		for (int i = 0; i < folds; i++) {
			rects[i] = new Rect(i * widthPerFold, 0, i* widthPerFold + widthPerFold, heightPerFold);			
		}		
		
		for(int i=0;i<folds;i++){
			matrices[i] = new Matrix();
		}
		
		srcPoly = new float[POLY_POINTS];
		dstPoly = new float[POLY_POINTS];
	}
	
	private void calculateMetrices(){	
		
		shouldDraw = true;
		
		if(foldFactor == 1){
			shouldDraw = false;
			return;
		}
		
		translateFactor  = 1 - foldFactor;
		
		translateWidth = bitmapWidth  * translateFactor;
		
		translateWidthPerFold = Math.round(translateWidth / folds);
		
		foldDrawWidth = widthPerFold < translateWidthPerFold ? translateWidthPerFold : widthPerFold;
		foldDrawHeight = heightPerFold;
		
		float translateWidthPerfoldsquare = translateWidthPerFold * translateWidthPerFold;
		float deepth = (float)Math.sqrt(foldDrawWidth * foldDrawWidth - translateWidthPerfoldsquare);
		
		scaleFactor = DEPTH_CONSTANT / (DEPTH_CONSTANT + deepth);
		
		float scaleWidth = foldDrawWidth * translateFactor; // from 1 to 0, means width becomes small				
		float scaleHeight = foldDrawHeight * scaleFactor;
		float topScaleHeightPoint = (foldDrawHeight - scaleHeight) / 2.f;		
		float bottomScaleHeightPoint = topScaleHeightPoint + scaleHeight;

		srcPoly[0] = 0;
		srcPoly[1] = 0;		
		srcPoly[2] = 0;
		srcPoly[3] = foldDrawHeight;
		srcPoly[4] = foldDrawWidth;
		srcPoly[5] = 0;
		srcPoly[6] = foldDrawWidth;
		srcPoly[7] = foldDrawHeight;
				
		for (int i = 0; i < folds; i++) {
			matrices[i].reset();
			boolean isEven = (i % 2 == 0);						
			dstPoly[0] = i * scaleWidth;
			dstPoly[1] = isEven ? 0 : topScaleHeightPoint;
			dstPoly[2] = dstPoly[0];
			dstPoly[3] = isEven ? foldDrawHeight : bottomScaleHeightPoint;
			dstPoly[4] = (i + 1) * scaleWidth;
			dstPoly[5] = isEven ? topScaleHeightPoint : 0;
			dstPoly[6] = dstPoly[4];
			dstPoly[7] = isEven ? bottomScaleHeightPoint : foldDrawHeight;
			
			for(int k=0; k< POLY_POINTS;k++){
				dstPoly[k] = Math.round(dstPoly[k]);
			}
			
			if(dstPoly[4] <= dstPoly[0] || dstPoly[6] <= dstPoly[2]){
				shouldDraw = false;
				return;
			}
			
			matrices[i].setPolyToPoly(srcPoly, 0, dstPoly, 0, POLY_POINTS / 2);
		}
		
		int alpha = (int) (foldFactor * 255 * SHADOW_APLHA);
		paintSolid.setColor(Color.argb(alpha, 0, 0, 0));
		matrixShadowGradient.setScale(foldDrawWidth, 1);
		linearGradientShadow.setLocalMatrix(matrixShadowGradient);
		
		paintGradientShadow.setAlpha(alpha);
	}

	public void onDraw(Canvas canvas) {
		
		calculateMetrices();
		if(!shouldDraw){
			return;
		}
		Rect src;
		for (int i = 0; i < folds; i++) {
			src = rects[i];
			canvas.save();		
			canvas.concat(matrices[i]);
			canvas.clipRect(0 , 0 , src.right - src.left, src.bottom - src.top);			
			canvas.translate(-rects[i].left, 0);
			canvas.drawBitmap(bitmap, 0, 0, null);
			canvas.translate(rects[i].left, 0);

			if (i % 2 == 0) {
				canvas.drawRect(0, 0, foldDrawWidth, foldDrawHeight, paintSolid);
			} else {
				canvas.drawRect(0, 0, foldDrawWidth, foldDrawHeight, paintGradientShadow);
			}
			canvas.restore();
		}
		canvas.save();
		canvas.drawLine(0, 431, 400, 431, paint);
		canvas.restore();
	}


	public void setFoldFactor(float pFoldFactor) {
		foldFactor = pFoldFactor;
		postInvalidate();		
	}
	
	public void setFolds(int pFolds){
		folds = pFolds;
		prepare();
	}
	
}