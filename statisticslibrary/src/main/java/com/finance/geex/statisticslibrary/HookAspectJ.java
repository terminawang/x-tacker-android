package com.finance.geex.statisticslibrary;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.finance.geex.statisticslibrary.db.GeexNetworkRequestBean;
import com.finance.geex.statisticslibrary.http.DefinitionHttpRequest;
import com.finance.geex.statisticslibrary.mananger.GeexPackage;
import com.finance.geex.statisticslibrary.util.NoDoubleClickUtils;
import com.finance.geex.statisticslibrary.util.TimeUtil;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created on 2019/7/3 13:45.
 * 通过AspectJ hook埋点事件
 *
 * @author Geex302
 */
@Aspect
public class HookAspectJ {

    //切入点 普通点击事件
    @Pointcut("execution(* android.view.View.OnClickListener.onClick(..))")
    public void onViewClick() {
    }

    //ButterKnife
    @Pointcut("execution(@butterknife.OnClick * *(android.view.View,..))")
    public void onButterKnifeAnnotationViewClick() {
    }



    //切入代码
    @Around("onViewClick()")
    public void aroundOnViewClickMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        try {


//            if (!NoDoubleClickUtils.isDoubleClick()) {

            //使用了butterknife的页面，不要在实现View.OnClickListener,否则会走两边该方法!!!!
            Signature signature = proceedingJoinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            String methodName = method.getName();
            if (methodName == null) {
                methodName = "";
            }

            String eventName = "";

            //参数
            Object[] args = proceedingJoinPoint.getArgs();
            if (args.length >= 1 && args[0] instanceof View) {
                View view = (View) args[0];
                int id = view.getId();
                String viewTitle = ""; //控件上的名称
                if (view instanceof TextView) { //button textview
                    TextView textView = (TextView) view;
                    viewTitle = textView.getText().toString(); //文本名
                }

                //获取控件的id名称
                Resources resources = view.getResources();
                String entryName = resources.getResourceEntryName(id); //控件id名
                if (entryName == null) {
                    entryName = "";
                }
//                    String resourcePackageName = resources.getResourcePackageName(id);
                String activityName = getActivityName(view); //当前页面的activity名称
                //拼接eventName
                eventName = activityName + "." + methodName + "." + viewTitle + "." + entryName;

            }


//            Log.d("wang", "aroundOnViewClickMethod: " + eventName);


            //点击记录埋入数据库 MainActivity.onClick.go_to_btn.text_click1
            GeexPackage.onEvent(eventName, "点击事件");

//            proceedingJoinPoint.proceed();
//            }


        } catch (Exception e) {

            //程序若
//            proceedingJoinPoint.proceed();

        }

        proceedingJoinPoint.proceed();

    }


    /**
     * 列表点击
     * @param proceedingJoinPoint
     * @throws Throwable
     */
    @Around("execution(* *.OnItemClick(..))")
    public void onListItemClick(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        try {

            Signature signature = proceedingJoinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            String declaringTypeName = methodSignature.getDeclaringTypeName();
            String methodName = method.getName();
            if (methodName == null) {
                methodName = "";
            }

            String eventName = "";

            //参数
            Object[] args = proceedingJoinPoint.getArgs();
            int position = 0;
            View parent = null;
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if(arg instanceof Integer){
                    position = (int) arg;
                }else if(arg instanceof View){
                    View view = (View) arg;
                    parent = (View) view.getParent();
                }
            }

            if(parent != null){
                int id = parent.getId();
                String viewTitle = ""; //控件上的名称

                //获取控件的id名称
                Resources resources = parent.getResources();
                String entryName = resources.getResourceEntryName(id); //控件id名
                if (entryName == null) {
                    entryName = "";
                }
                String activityName = getActivityName(parent); //当前页面的activity名称
                //拼接eventName
                eventName = activityName + "." + methodName + "." + viewTitle + "." + entryName + "[" + position + "]";
                //埋点
                GeexPackage.onEvent(eventName, "点击事件");

            }else {

                eventName = declaringTypeName + "." + methodName + "[" + position + "]";
                GeexPackage.onEvent(eventName, "点击事件");
            }


        }catch (Exception e){

//            proceedingJoinPoint.proceed();

        }

        proceedingJoinPoint.proceed();


    }



    @Around("execution(* *.staticsHook(..))")
    public void httpRequestStatistics(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        try {

            Object[] args =  proceedingJoinPoint.getArgs();
            if(args.length >= 1){

                GeexNetworkRequestBean geexNetworkRequestBean = new GeexNetworkRequestBean();

                Object arg = args[0];
                DefinitionHttpRequest definitionHttpRequest = (DefinitionHttpRequest) arg;
                //请求方式
                String requestType = definitionHttpRequest.getRequestType();
                if(TextUtils.isEmpty(requestType)){
                    requestType = "";
                }
                geexNetworkRequestBean.setRequestType(requestType);
                //请求链接
                String requestUrl = definitionHttpRequest.getRequestUrl();
                if(TextUtils.isEmpty(requestUrl)){
                    requestUrl = "";
                }
                geexNetworkRequestBean.setRequestUrl(requestUrl);
                //请求体
                String responseBody = definitionHttpRequest.getResponseBody();
                if(TextUtils.isEmpty(responseBody)){
                    responseBody = "";
                }
                geexNetworkRequestBean.setResponseBody(responseBody);
                //请求开始时间
                long startTime = definitionHttpRequest.getStartTime();
                geexNetworkRequestBean.setStartTime(TimeUtil.longToString(startTime, "yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
                //响应时间
                String responseTime = definitionHttpRequest.getResponseTime();
                if(TextUtils.isEmpty(responseTime)){
                    responseTime = "";
                }
                geexNetworkRequestBean.setResponseTime(responseTime);
                //请求字段
                String requestParams = definitionHttpRequest.getRequestParams();
                if(TextUtils.isEmpty(requestParams)){
                    requestParams = "";
                }
                geexNetworkRequestBean.setRequestParams(requestParams);

                //数据上传
                GeexPackage.onNetworkRequest(geexNetworkRequestBean);

            }

        }catch (Exception e){

//            proceedingJoinPoint.proceed();

        }

        proceedingJoinPoint.proceed();


    }

    /**
     * 兼容volley框架
     * @param proceedingJoinPoint
     * @throws Throwable
     */
    @Around("execution(* *.staticsVolleyHook(..))")
    public void httpVolleyRequestStatistics(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        try {

            Object[] args =  proceedingJoinPoint.getArgs();
            if(args.length >= 1){

                GeexNetworkRequestBean geexNetworkRequestBean = new GeexNetworkRequestBean();

                Object arg = args[0];
                Map<String,Object>  hookData = (Map<String,Object>) arg;
                //请求方式
                String requestType = "";
                if(hookData.containsKey("requestType")){
                    requestType = (String) hookData.get("requestType");
                }
                geexNetworkRequestBean.setRequestType(requestType);
                //请求链接
                String requestUrl = "";
                if(hookData.containsKey("requestUrl")){
                    requestUrl = (String) hookData.get("requestUrl");
                }
                geexNetworkRequestBean.setRequestUrl(requestUrl);
                //请求体
                String responseBody = "";
                if(hookData.containsKey("responseBody")){
                    responseBody = (String) hookData.get("responseBody");
                }
                geexNetworkRequestBean.setResponseBody(responseBody);
                //请求开始时间
                long startTime = 0;
                if(hookData.containsKey("startTime")){
                    startTime = (long) hookData.get("startTime");
                }
                geexNetworkRequestBean.setStartTime(TimeUtil.longToString(startTime, "yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
                //响应时间
                String responseTime = "";
                if(hookData.containsKey("responseTime")){
                    responseTime = (String) hookData.get("responseTime");
                }
                geexNetworkRequestBean.setResponseTime(responseTime);
                //请求字段
                String requestParams = "";
                if(hookData.containsKey("requestParams")){
                    requestParams = (String) hookData.get("requestParams");
                }
                geexNetworkRequestBean.setRequestParams(requestParams);

                //数据上传
                GeexPackage.onNetworkRequest(geexNetworkRequestBean);

            }

        }catch (Exception e){

//            proceedingJoinPoint.proceed();

        }

        proceedingJoinPoint.proceed();


    }


    //从View中利用context获取所属Activity的名字
    public static String getActivityName(View view) {
        Context context = view.getContext();
        if (context == null) {
            return "";
        }
        //获取当前activity
        Activity activity = findActivity(context);
        if (activity == null) {
            return "";
        }
        //判断是否有fragment


        return activity.getClass().getSimpleName();
    }

    @Nullable
    private static Activity findActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            ContextWrapper wrapper = (ContextWrapper) context;
            return findActivity(wrapper.getBaseContext());
        } else {
            return null;
        }
    }


}
