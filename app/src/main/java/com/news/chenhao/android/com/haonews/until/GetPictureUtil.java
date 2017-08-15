package com.news.chenhao.android.com.haonews.until;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.File;


/**
 * 图片选择 requestCode 789不可使用
 */
public class GetPictureUtil {
    //使用方法
//    用前必须实例化
//            mGetPictureUtil = new GetPictureUtil(this, GetPictureUtil.PIC_LIMIT_DISPLAY);


    public static final int PIC_LIMIT_NO = -1;
    public static final int PIC_LIMIT_DISPLAY = 0;

    Activity mAct;
    Fragment mFra;
    private int mRequestCode = 789;
    private boolean isCrop;// 是否需要裁剪
    private boolean isShoot;// 是否是拍摄的照片
    private Uri mShootUri;//拍摄的图片的保存路径
    private int mAspectX; // 裁剪框比例
    private int mAspectY; // 裁剪框比例
    private int mOutputX;// 输出图片大小
    private int mOutputY;// 输出图片大小
    private Uri mCropImg;//裁剪出来的图片
    private int mMaxValue;//图片宽高最大值 0表示把屏幕宽度当最大值，-1表示不限制
    private ProgressDialog mProgressDialog;//保存图片时的提示
    private GetCallBack mCallBack;

    public GetPictureUtil(Activity activity, int max) {
        mAct = activity;
        init(max);
    }

    public GetPictureUtil(Fragment fragment, int max) {
        mFra = fragment;
        mAct = mFra.getActivity();
        init(max);
    }

    private void init(int max) {
        switch (max) {
            case PIC_LIMIT_DISPLAY:
                mMaxValue = mAct.getResources().getDisplayMetrics().widthPixels;
                break;
            default:
                mMaxValue = max;
                break;
        }
        mProgressDialog = new ProgressDialog(mAct);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("数据加载中……");
    }

    /**
     * 拍摄照片
     */
    public void getShoot(GetCallBack callBack) {
        mCallBack = callBack;
        isCrop = false;
        isShoot = true;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mShootUri = Uri.fromFile(
                new File(
                        FileUtil.externalStoragePrivatePicPath(mAct),
                        FileUtil.createFileName(".jpg")));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mShootUri);
        startActivityForResult(intent, mRequestCode);
    }

    /**
     * 拍摄并裁剪
     */
    public void getShoot(int aspectX, int aspectY, int outputX, int outputY, GetCallBack callBack) {
        mCallBack = callBack;
        isCrop = true;
        isShoot = true;
        mCropImg = null;
        this.mAspectX = aspectX;
        this.mAspectY = aspectY;
        this.mOutputX = outputX;
        this.mOutputY = outputY;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mShootUri = Uri.fromFile(
                new File(
                        FileUtil.externalStoragePrivatePicPath(mAct),
                        FileUtil.createFileName(".jpg")));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mShootUri);
        startActivityForResult(intent, mRequestCode);
    }

    /**
     * 选择图片
     */
    public void getPic(GetCallBack callBack) {
        mCallBack = callBack;
        isCrop = false;
        isShoot = false;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, mRequestCode);
    }

    /**
     * 选择并裁剪
     */
    public void getPic(int aspectX, int aspectY, int outputX, int outputY, GetCallBack callBack) {
        mCallBack = callBack;
        isCrop = true;
        isShoot = false;
        mCropImg = null;
        this.mAspectX = aspectX;
        this.mAspectY = aspectY;
        this.mOutputX = outputX;
        this.mOutputY = outputY;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, mRequestCode);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == mRequestCode) {
                if (isCrop) {
                    if (mCropImg == null) {
                        //裁剪
                        if (isShoot) {
                            //拍摄裁剪
                            crop(mShootUri);
                        } else {
                            crop(data.getData());
                        }
                    } else {
                        //裁剪完成 图片
                        callBack(mCropImg.getPath());
                    }
                    return;
                } else {
                    if (isShoot) {
                        //拍摄
                        if (mMaxValue == PIC_LIMIT_NO) {
                            //图片不做缩小
                            if (BitmapUtil.getInstance(mShootUri.getPath()).correct()) {
                                callBack(mShootUri.getPath());
                            } else {
                                File rotationFile = new File(
                                        FileUtil.externalStoragePrivatePicPath(mAct),
                                        FileUtil.createFileName(".jpg"));
                                mProgressDialog.show();
                                BitmapUtil.getInstance(mShootUri.getPath())
                                        .rotation()
                                        .save(rotationFile.getAbsolutePath(),
                                                new BitmapUtil.BitmapSaveCallBack() {
                                                    @Override
                                                    public void callback(String filePath) {
                                                        mProgressDialog.dismiss();
                                                        callBack(filePath);
                                                    }
                                                });
                            }
                        } else {
                            //图片缩小
                            File rotationFile = new File(
                                    FileUtil.externalStoragePrivatePicPath(mAct),
                                    FileUtil.createFileName(".jpg"));
                            mProgressDialog.show();
                            BitmapUtil.getInstance(mShootUri.getPath())
                                    .rotation()
                                    .limit(mMaxValue)
                                    .save(rotationFile.getAbsolutePath(),
                                            new BitmapUtil.BitmapSaveCallBack() {
                                                @Override
                                                public void callback(String filePath) {
                                                    mProgressDialog.dismiss();
                                                    callBack(filePath);
                                                }
                                            });
                        }
                    } else {
                        //选择
                        String filePath = FileUtil.getFileAbsolutePath(mAct, data.getData());
                        int[] values = BitmapUtil.getInstance(filePath).values();
                        if (Math.min(values[0], values[1]) > 0) {
                            //没有宽高，表示为非图片文件
                            if (mMaxValue == PIC_LIMIT_NO) {
                                //图片不做缩小
                                if (BitmapUtil.getInstance(filePath).correct()) {
                                    callBack(filePath);
                                } else {
                                    File rotationFile = new File(
                                            FileUtil.externalStoragePrivatePicPath(mAct),
                                            FileUtil.createFileName(".jpg"));
                                    mProgressDialog.show();
                                    BitmapUtil.getInstance(filePath)
                                            .rotation()
                                            .save(rotationFile.getAbsolutePath(),
                                                    new BitmapUtil.BitmapSaveCallBack() {
                                                        @Override
                                                        public void callback(String filePath) {
                                                            mProgressDialog.dismiss();
                                                            callBack(filePath);
                                                        }
                                                    });
                                }
                            } else {
                                //图片缩小
                                File rotationFile = new File(
                                        FileUtil.externalStoragePrivatePicPath(mAct),
                                        FileUtil.createFileName(".jpg"));
                                mProgressDialog.show();
                                BitmapUtil.getInstance(filePath)
                                        .rotation()
                                        .limit(mMaxValue)
                                        .save(rotationFile.getAbsolutePath(),
                                                new BitmapUtil.BitmapSaveCallBack() {
                                                    @Override
                                                    public void callback(String filePath) {
                                                        mProgressDialog.dismiss();
                                                        callBack(filePath);
                                                    }
                                                });
                            }
                        } else {
                            callBack("");
                        }
                    }
                }
            }
        }
    }

    private void crop(Uri photoUri) {
        mCropImg = Uri.fromFile(new File(FileUtil.externalStoragePrivatePicPath(mAct), FileUtil.createFileName("" + ".jpg")));
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", mAspectX);
        intent.putExtra("aspectY", mAspectY);
        intent.putExtra("outputX", mOutputX);
        intent.putExtra("outputY", mOutputY);
        intent.putExtra("scale", true);// 如果选择的图小于裁剪大小则进行放大
        intent.putExtra("scaleUpIfNeeded", true);// 如果选择的图小于裁剪大小则进行放大
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCropImg);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, mRequestCode);
    }

    private void startActivityForResult(Intent intent, int requestCode) {
        if (intent.resolveActivity(mAct.getPackageManager()) != null) {
            if (mFra != null) {
                mFra.startActivityForResult(intent, requestCode);
            } else {
                mAct.startActivityForResult(intent, requestCode);
            }
        }
    }

    public interface GetCallBack {
        void callback(String filePath);
    }

    private void callBack(String file) {
        if (mCallBack != null) {
            mCallBack.callback(file);
        }
    }
}