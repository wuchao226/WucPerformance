package com.wuc.performance.webview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.view.View;

import java.util.Arrays;

/**
 * WebView白屏检测
 *
 * 通用的方案都是截屏然后判断，可以优化的点在于把截取的图片给缩小，来提升效率。
 * 检测的时间最好是控制在60ms 左右，其中包含了截图的时间+分析白屏的时间
 * 检测白屏你在 onPageFinished 即可，做好去重
 * 上报用户相关的信息，设备信息等
 * 检测到之后提示用户去重新加载一次
 */
public class BlankDetect {

  /**
   * 判断Bitmap是否都是一个颜色
   *
   * @return
   */
  public static boolean isBlank(View view) {
    Bitmap bitmap = getBitmapFromView(view);

    if (bitmap == null) {
      return true;
    }
    int width = bitmap.getWidth();
    int height = bitmap.getHeight();
    if (width > 0 && height > 0) {
      int originPix = bitmap.getPixel(0, 0);
      int[] target = new int[width];
      Arrays.fill(target, originPix);
      int[] source = new int[width];
      boolean isWhiteScreen = true;
      for (int col = 0; col < height; col++) {
        bitmap.getPixels(source, 0, width, 0, col, width, 1);
        if (!Arrays.equals(target, source)) {
          isWhiteScreen = false;
          break;
        }
      }
      return isWhiteScreen;
    }
    return false;
  }

  /**
   * 从View获取转换到的Bitmap
   *
   * @param view
   * @return
   */
  private static Bitmap getBitmapFromView(View view) {
    Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    if (Build.VERSION.SDK_INT >= 11) {
      view.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
          View.MeasureSpec.makeMeasureSpec(view.getHeight(), View.MeasureSpec.EXACTLY));
      view.layout((int) view.getX(), (int) view.getY(), (int) view.getX() + view.getMeasuredWidth(),
          (int) view.getY() + view.getMeasuredHeight());
    } else {
      view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
          View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
      view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }
    view.draw(canvas);
    return bitmap;
  }

}
