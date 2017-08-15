package com.news.chenhao.android.com.haonews.until;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片处理
 */

public class BitmapUtil {
    //图片路径
    String mFilePath;
    //旋转角度
    int mDegree;
    //宽高最大值
    int mMax;
    Handler mHandler;
    private volatile static BitmapUtil mBitmapUtil;

    private BitmapUtil() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static BitmapUtil getInstance(String filePath) {
        if (mBitmapUtil == null) {
            synchronized (BitmapUtil.class) {
                if (mBitmapUtil == null) {
                    mBitmapUtil = new BitmapUtil();
                }
            }
        }
        mBitmapUtil.init(filePath);
        return mBitmapUtil;
    }

    /**
     * 数据初始化
     */
    private void init(String filePath) {
        mFilePath = filePath;
        mDegree = 0;
        mMax = 0;
    }

    /**
     * 获取图片
     */
    private Bitmap getBitmap(String filePath, int scale) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        return bitmap;
    }

    /**
     * 按设置获取图片的bitmap
     */
    private Bitmap getBitmap() {
        Bitmap mBitmap;
        if (mMax != 0) {
            int[] values = getBitmapValue(mFilePath);
            int scaleFactor = (int) Math.max(Math.ceil((double) values[0] / mMax), Math.ceil(
                    (double) values[1] / mMax));
            mBitmap = getBitmap(mFilePath, scaleFactor);
        } else {
            mBitmap = getBitmap(mFilePath, 1);
        }
        if (mDegree > 0) {
            mBitmap = rotateBitmapByDegree(mBitmap, mDegree);
        }
        return mBitmap;
    }

    /**
     * 获得图片宽高信息 [0]:width [1]:height
     */
    private int[] getBitmapValue(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int[] value = new int[2];
        value[0] = options.outWidth;
        value[1] = options.outHeight;
        return value;
    }

    /**
     * 获得图片的旋转角度
     */
    private int getBitmapRotateDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface
                    .ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     */
    private Bitmap rotateBitmapByDegree(Bitmap sourceBitmap, int degree) {
        Bitmap retBitmap;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
        retBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap
                .getHeight(),
                matrix, true);
        sourceBitmap.recycle();
        sourceBitmap = null;
        System.gc();
        return retBitmap;
    }

    /**
     * 保存图片
     */
    private boolean saveBitmap(Bitmap sourceBitmap, String filePath) {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            sourceBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOut);
            fileOut.flush();
            fileOut.close();
            fileOut = null;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 公开方法
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 获取图片回调
     */
    public interface BitmapGetCallBack {
        void callback(Bitmap bitmap);
    }

    public interface BitmapBase64GetCallBack {
        void callback(String base64);
    }

    public interface BitmapByteGetCallBack {
        void callback(byte[] bytes);
    }

    /**
     * 旋转为正确的方向
     */
    public BitmapUtil rotation() {
        mDegree = getBitmapRotateDegree(mFilePath);
        return this;
    }

    /**
     * 图片是否有旋转角度
     */
    public boolean correct() {
        return getBitmapRotateDegree(mFilePath) == 0;
    }

    /**
     * 获取图片宽高
     */
    public int[] values() {
        return getBitmapValue(mFilePath);
    }

    /**
     * 宽高的最大值
     */
    public BitmapUtil limit(int max) {
        mMax = max;
        return this;
    }

    /**
     * 获取图片
     */
    public void get(final BitmapGetCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap mBitmap = getBitmap();
                if (callBack != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.callback(mBitmap);
                        }
                    });
                }
            }
        }).start();
    }

    public void getByte(final BitmapByteGetCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] bytes = null;
                Bitmap mBitmap = getBitmap();
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    baos.flush();
                    baos.close();
                    bytes = baos.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (callBack != null) {
                    final byte[] finalBytes = bytes;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.callback(finalBytes);
                        }
                    });
                }
            }
        }).start();
    }

    //将图片转换为Base64格式的数据流
    public void getBase64(final BitmapBase64GetCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String base64 = null;
                Bitmap mBitmap = getBitmap();
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    baos.flush();
                    baos.close();
                    base64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (callBack != null) {
                    final String finalBase6 = base64;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.callback(finalBase6);
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 保存图片回调
     */
    public interface BitmapSaveCallBack {
        void callback(String filePath);
    }

    /**
     * 保存图片
     */
    public void save(final String filePath, final BitmapSaveCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap mBitmap = getBitmap();
                if (mBitmap == null) {
                    if (callBack != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.callback("");
                            }
                        });
                    }
                } else {
                    final boolean isSave = saveBitmap(mBitmap, filePath);
                    mBitmap.recycle();
                    mBitmap = null;
                    System.gc();
                    if (callBack != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.callback(isSave ? filePath : "");
                            }
                        });
                    }
                }
            }
        }).start();
    }
//
//    /**
//     * 获取图片的BASE64
//     *
//     * @param filePath
//     * @param quality  图片质量
//     * @return
//     */
//
//    private String getBase64_p(String filePath, int quality) {
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        getBitmap(filePath).compress(Bitmap.CompressFormat.JPEG, quality, bos);//参数100表示不压缩
//        byte[] bytes = bos.toByteArray();
//        StringBuilder sbBase64 = new StringBuilder();
//        filePath.toLowerCase();
//        if (filePath.endsWith(".png")) {
//            sbBase64.append("data:image/png");
//        } else {
//            sbBase64.append("data:image/jpeg");
//        }
//        sbBase64.append(";base64,");
//        sbBase64.append(Base64.encodeToString(bytes, Base64.NO_WRAP));//无换行符
//        return sbBase64.toString();
//    }
//
//    /* —————————————————————————————————————————————————————————— */
//    public void setCallBack(BitMapCallBack bitMapCallBack) {
//        this.bitMapCallBack = bitMapCallBack;
//    }
//
//    public void setCallBack(BitMapBase64CallBack bitMapBase64CallBack) {
//        this.bitMapBase64CallBack = bitMapBase64CallBack;
//    }
//
//    /**
//     * 获得图片，图片宽高超过1280则进行缩小
//     *
//     * @param filePath
//     * @return
//     * @author yubin
//     * @date 2014-11-6
//     */
//    public Bitmap getBitmap(String filePath) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;//只加载宽高不加载具体图片
//        int width = 1280;
//        BitmapFactory.decodeFile(filePath, options);
//        int scale = 1;
//        int w = options.outWidth, h = options.outHeight;
//        while (w > width && h > width) {
//            w /= 2;
//            h /= 2;
//            scale *= 2;
//        }
//        options.inSampleSize = scale;
//        options.inJustDecodeBounds = false;
//
//        options.inPurgeable = true;
//        options.inInputShareable = true;
//        options.inPreferredConfig = Bitmap.Config.RGB_565;//降低图片质量去除透明度
//        return BitmapFactory.decodeFile(filePath, options);
//    }
//
//
//    public void saveThumbnail(int requestCode, String filePath, int minWidth) {
//        int[] value = getBitmapValue(filePath);
//        float scale = (float) (minWidth) / value[0];
//        saveThumbnail(requestCode, filePath, minWidth, (int) (value[1] * scale));
//    }
//
//    /**
//     * 保存缩略图
//     *
//     * @param filePath
//     * @param width
//     * @param height
//     * @return
//     * @author yubin
//     * @date 2014-11-6
//     */
//    public void saveThumbnail(final int requestCode, final String filePath, final int width,
// final int height) {
//        new Thread(new Runnable() {
//            public void run() {
//                Bitmap sourceBitmap = getBitmap(filePath);
//                Bitmap thumbanilBitmap = ThumbnailUtils.extractThumbnail(sourceBitmap, width,
// height);
////                String thumbnailFileName = fileUtil.getNewFileName(".jpg");
////                String absloutePath = saveBitmap_p(thumbanilBitmap, fileUtil.getSDcardPath()
// + LOCAL_THUMBNAIL +
////                        thumbnailFileName);
//                thumbanilBitmap.recycle();
//                thumbanilBitmap = null;
//                sourceBitmap.recycle();
//                sourceBitmap = null;
//                System.gc();
////                callback(requestCode, absloutePath);
//            }
//        }).start();
//    }
//
//    /**
//     * 获得缩略图，不保存
//     *
//     * @param filePath
//     * @param width
//     * @param height
//     * @return
//     * @author yubin
//     * @date 2015-8-18
//     */
//    public Bitmap getThumbnail(final String filePath, final int width, final int height) {
//        Bitmap sourceBitmap = getBitmap(filePath);
//        Bitmap thumbanilBitmap = ThumbnailUtils.extractThumbnail(sourceBitmap, width, height);
//        sourceBitmap.recycle();
//        sourceBitmap = null;
//        System.gc();
//        return thumbanilBitmap;
//    }
//
//    /**
//     * 在限制内返回图片
//     *
//     * @param requestCode
//     * @param path
//     * @author yubin
//     * @date 2015-8-19
//     */
//    public void getFileLimitSize(final int requestCode, final String path) {
//        new Thread(new Runnable() {
//            public void run() {
//                File f = new File(path);
//                long mSize = 500 * 1024;
//                if (f.length() > mSize) {
//                    // 超过限制大小
////                    callback(requestCode, saveBitmap_p(getBitmap(path), fileUtil
// .getSDcardPath() + LOCAL_PHOTO +
////                            fileUtil.getNewFileName(".jpg")));
//                } else {
//                    // 不超过限制大小
//                    callback(requestCode, path);
//                }
//            }
//        }).start();
//    }
//
//    /**
//     * 返回Base64
//     *
//     * @param filePath
//     */
//    public void getBase64(final String filePath) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                callback(getBase64_p(filePath, 100));
//            }
//        }).start();
//    }
//
//    /**
//     * 返回图片集合的base64并且加上了类型
//     *
//     * @param filePaths
//     * @return
//     */
//    public void getBase64(final ArrayList<String> filePaths) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                StringBuilder sb = new StringBuilder();
//                String filePath;
//                for (int i = 0, len = filePaths.size(); i < len; i++) {
//                    filePath = filePaths.get(i);
//                    sb.append(getBase64_p(filePath, 70));
//                    sb.append(",");
//                }
//                sb.deleteCharAt(sb.length() - 1);
//                callback(sb.toString());
//            }
//        }).start();
//    }
//
//    public interface BitMapBase64CallBack {
//        void callBack(String base64);
//    }
//
//    public interface BitMapCallBack {
//        void callBack(int requestCode, String path);
//    }
}