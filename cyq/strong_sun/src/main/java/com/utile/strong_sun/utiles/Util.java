package com.utile.strong_sun.utiles;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.RequiresPermission;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;

public class Util {



    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 网络是否已连接
     *
     * @return true:已连接 false:未连接
     */
    @SuppressWarnings("deprecation")
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NetworkCapabilities networkCapabilities = manager.getNetworkCapabilities(manager.getActiveNetwork());
                if (networkCapabilities != null) {
                    return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
                }
            } else {
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnected();
            }
        }
        return false;

    }

    /**
     * 获取当前系统时间--标准格式yyyyMMddHHmmss
     *
     * @return 当前时间点
     */
    public static String getTimeNow() {
        //获取当前系统时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {

        Configuration configuration = context.getResources().getConfiguration();
        return (configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static Date getDateFromString(String str) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = formatter.parse(str);//获取当前时间
        return date;
    }

    /**
     * 日期转字符串
     *
     * @param curDate
     * @return
     */
    public static String getDateString(Date curDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取GUID
     *
     * @return GUID
     */
    public static String getGUID() {
        String str = java.util.UUID.randomUUID().toString();//GUID;
        return str;
    }

    /**
     * 这是使用adb shell命令来获取mac地址的方式
     *
     * @return
     */
    public static String getMac() {
        String macSerial = null;
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }

        if (macSerial == null) {
            macSerial = getLocalMacAddressFromIp();
        }

        return macSerial;
    }

    /**
     * 根据IP地址获取MAC地址
     *
     * @return
     */
    public static String getLocalMacAddressFromIp() {
        String strMacAddr = null;
        try {
            //获得IpD地址
            InetAddress ip = getLocalInetAddress();
            byte[] b = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toLowerCase();
        } catch (Exception e) {

        }
        return strMacAddr;
    }

    /**
     * 获取移动设备本地IP
     *
     * @return
     */
    private static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            //列举
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface.getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {//是否还有元素
                NetworkInterface ni = (NetworkInterface) en_netInterface.nextElement();//得到下一个元素
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();//得到一个ip地址的列举
                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1)
                        break;
                    else
                        ip = null;
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException e) {

            e.printStackTrace();
        }
        return ip;
    }

    /**
     * 文字转换BitMap
     *
     * @param text
     * @return
     */
    public static Drawable createMapBitMap(String text) {

        //默认字体大小个高度
        int size = 30;
        int height = 30;

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(size);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);//位置

        float textLength = paint.measureText(text);

        int width = (int) textLength;

        Bitmap newb = Bitmap.createBitmap(width * 2, height * 2, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newb);
        cv.drawColor(Color.parseColor("#00000000"));

        cv.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));

        cv.drawText(text, width, size, paint);
        //        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        cv.save();// 保存
        cv.restore();// 存储
        return new BitmapDrawable(newb);
    }

    // Sdcard辅助相关类
    public static class SDCardUtils {
        private SDCardUtils() {
            /* cannot be instantiated */
            throw new UnsupportedOperationException("cannot be instantiated");
        }

        /**
         * 判断SDCard是否可用
         *
         * @return
         */
        public static boolean isSDCardEnable() {
            return Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);

        }

   

        /**
         * 获取SD卡的剩余容量 单位byte
         *
         * @return
         */
        public static long getSDCardAllSize(String path) {
            if (isSDCardEnable()) {
                StatFs stat = new StatFs(path);
                // 获取空闲的数据块的数量
                long availableBlocks = (long) stat.getAvailableBlocks() - 4;
                // 获取单个数据块的大小（byte）
                long freeBlocks = stat.getAvailableBlocks();
                return freeBlocks * availableBlocks;
            }
            return 0;
        }
        /**
         * 获取系统存储路径
         *
         * @return
         */
        public static String getRootDirectoryPath() {
            return Environment.getRootDirectory().getAbsolutePath();
        }
    }
    // 弹窗工具组件
    /**
     * 系统弹窗提示信息
     */
    public static void showSystemDialog(Context context,
                                        String title,
                                        String msg,
                                        String negativeButtonName,
                                        DialogInterface.OnClickListener negativeButtonlistener,
                                        String positiveButtonName,
                                        DialogInterface.OnClickListener positiveButtonlistener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setTitle(title);
        //确定按钮
        if (negativeButtonName!=null){
            builder.setNegativeButton(negativeButtonName, negativeButtonlistener);
        }
        //取消按钮
        if (positiveButtonName!=null){
            builder.setPositiveButton(positiveButtonName, positiveButtonlistener);
        }

        builder.create().show();
    }
    /**
     * 通过反射调用获取内置存储和外置sd卡根路径(通用)
     *
     * @param mContext    上下文
     * @param is_removale 是否可移除，false返回内部存储路径，true返回外置SD卡路径
     * @return
     */
    public static String getStoragePath(Context mContext, boolean is_removale) {
        String path = null;
        //使用getSystemService(String)检索一个StorageManager用于访问系统存储功能。
        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);

            for (int i = 0; i < Array.getLength(result); i++) {
                Object storageVolumeElement = Array.get(result, i);
                path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removale == removable) {
                    return path;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
    /**
     * 判断是否安装目标应用
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    public static boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }
    // 道格拉斯-抽希算法
    public static List<Point> DouglasPeucker(List<Point> Points, double Tolerance) {
        if (Points.size() == 0 || (Points.size() < 3))
            return Points;

        int firstPoint = 0;
        int lastPoint = Points.size() - 1;
        List<Integer> pointIndexsToKeep = new ArrayList<Integer>();

        // Add the first and last index to the keepers
        pointIndexsToKeep.add(firstPoint);
        pointIndexsToKeep.add(lastPoint);

        // The first and the last point cannot be the same
        while (Points.get(firstPoint) == Points.get(lastPoint)) {
            lastPoint--;
        }

        DouglasPeuckerReduction(Points, firstPoint, lastPoint, Tolerance,
                pointIndexsToKeep);

        Vector<Point> returnPoints = new Vector<Point>();
        Integer ix[] = (Integer[]) pointIndexsToKeep.toArray(new Integer[] {});
        Arrays.sort(ix);
        for (Integer i : ix)
            returnPoints.add(Points.get(i));

        return returnPoints;
    }
    static void DouglasPeuckerReduction(List<Point> points, int firstPoint,
                                        int lastPoint, double tolerance, List<Integer> pointIndexsToKeep) {
        double maxDistance = 0;
        int indexFarthest = 0;

        for (int index = firstPoint; index < lastPoint; index++) {
            double distance = PerpendicularDistance(
                    points.get(firstPoint), points.get(lastPoint),
                    points.get(index));
            if (distance > maxDistance) {
                maxDistance = distance;
                indexFarthest = index;
            }
        }

        if (maxDistance > tolerance && indexFarthest != firstPoint)//&&indexFarthest!=points.size()-1)
        {
            // Add the largest point that exceeds the tolerance
            pointIndexsToKeep.add(indexFarthest);

            DouglasPeuckerReduction(points, firstPoint, indexFarthest,
                    tolerance, pointIndexsToKeep);

            DouglasPeuckerReduction(points, indexFarthest, lastPoint,
                    tolerance, pointIndexsToKeep);
        }
    }
    static double PerpendicularDistance(Point Point1, Point Point2, Point Point) {

        double area = Math
                .abs(0.5 * (Point1.x * Point2.y + Point2.x * Point.y + Point.x
                        * Point1.y - Point2.x * Point1.y - Point.x * Point2.y - Point1.x
                        * Point.y));
        double bottom = Math.sqrt(Math.pow(Point1.x - Point2.x, 2)
                + Math.pow(Point1.y - Point2.y, 2));
        double height = area / bottom * 2;

        return height;

    }
    // 判断字符串是否为数字
    public static boolean isNumeric(String str){
        if(TextUtils.isEmpty(str))
            return false;
        boolean isNumber = str.matches("-?[0-9]+.*[0-9]*");
        if(isNumber)
            return true;
        else
            return false;
    }
    // 十六进制字符串转为颜色的ARGB值
    public static String fromStrToARGB(int colorValue){
        int red = (colorValue>>16)&0xFF;
        int green = (colorValue>>8)&0xFF;
        int blue = (colorValue>>0)&0xFF;
        int alpha = (colorValue>>24)&0xFF;
        StringBuilder sb = new StringBuilder("[");
        sb.append(red).append(",").append(green).append(",").append(blue).append(",").append(alpha).append("]");
      String colorValue1 = sb.toString();
      return colorValue1;
    }
    /**
     * 检测是否有某个应用
     * */
    public static boolean hasApp(Context ctx, String packageName) {
        PackageManager manager = ctx.getPackageManager();
        List<PackageInfo> apps = manager.getInstalledPackages(0);
        if (apps != null) {
            for (int i = 0; i < apps.size(); i++) {
                if (apps.get(i).packageName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void hideIputKeyboard(final Context context) {
        final Activity activity = (Activity) context;
        if (activity==null) {
            return;
        }
        if (activity.isFinishing()) {
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InputMethodManager mInputKeyBoard = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (activity.getCurrentFocus() != null) {
                    mInputKeyBoard.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                }
            }
        });
    }
public static double doubleFormat(double num,int some){
    BigDecimal b = new BigDecimal(num);
   return b.setScale(some, BigDecimal.ROUND_HALF_UP).doubleValue();
}
    // 获取面积值
    public static String getAreaString(double aValue){
        String sArea;
        long area = Math.abs(Math.round(aValue));
        // 顺时针绘制多边形，面积为正；逆时针绘制，面积为负
//        if(area > 666.66666){
            double areaValue = area / 666.666;
            sArea = doubleFormat(areaValue,2)+"亩";
//        } else {
//            sArea = doubleFormat(area,2)+"平方米";
//        }
        return sArea;
    }

    // 获取长度值
    public static String getLengthString(double lValue) {
        long len = Math.abs(Math.round(lValue));
        String lenValue;
        if(len > 1000 ){
            double dLength = len / 1000.0;
            // 千米
            lenValue =  Util.doubleFormat(dLength,5)+"千米";
        } else {
            // 米
            lenValue = Util.doubleFormat(len,2)+"米";
        }
        return  lenValue;
    }
    public  static String toJSON(String path){

        String json = null;
        File file = new File(path);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            json = new String(bytes);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(json)){
            return null;
        }
        return json;
    }
}