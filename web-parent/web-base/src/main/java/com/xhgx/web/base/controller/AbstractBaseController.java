package com.xhgx.web.base.controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xhgx.sdk.container.SpringContextUtil;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.xhgx.sdk.entity.Result;
import com.xhgx.web.base.entity.OnlineUser;
import com.xhgx.web.base.exection.LoginStateExcepton;
import com.xhgx.web.base.json.DateJsonValueProcessor;
import com.xhgx.web.base.property.BigDecimalEditor;
import com.xhgx.web.base.property.DateEditor;
import com.xhgx.web.base.property.DoubleEditor;
import com.xhgx.web.base.property.FloatEditor;
import com.xhgx.web.base.property.IntegerEditor;
import com.xhgx.web.base.property.LongEditor;
import com.xhgx.web.base.property.StringEscapeEditor;

import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * 
 * @ClassName AbstractBaseController
 * @Description 通用抽象controller层
 * @author zohan(inlw@sina.com)
 * @date 2017-03-24 9:18
 * @vresion 1.0
 */
@Controller("AbstractBaseController")
@Scope("prototype")
public abstract class AbstractBaseController implements ResultRender {


    static Logger log = LoggerFactory.getLogger(AbstractBaseController.class);


    protected static final String ENCODING_PREFIX = "encoding";

    protected static final String NOCACHE_PREFIX = "no-cache";

    /**
     * 默认编码格式
     */
    protected static final String ENCODING_DEFAULT = "utf-8";

    /**默认的日期时间格式*/
    protected static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    protected static final boolean NOCACHE_DEFAULT = true;

    protected HttpServletRequest request;

    protected HttpServletResponse response;


    /**
     * 默认json格式配置参数
     */
    final static JsonConfig DEFAULT_JSON_CONFIG = new JsonConfig();
                            
    /**
     * 是否使用默认的 jsonConfig
     */
    protected boolean isDefaultJsonConfig = true;

    /**
     * 生成json数据 默认配置参数
     */
    protected JsonConfig jsonConfig = new JsonConfig();

    /**
     * 普通的信息
     *
     * @param contentType 输出的文本格式 如："text/html"，“text/plain”，"text/xml"
     * @param content     对外输出的内容
     * @param headers     参数设置
     */
    public void render(String contentType, String content, String... headers) {
        HttpServletResponse response = this.getResponse();
        try {
            // 分析headers参数
            String encoding = ENCODING_DEFAULT;
            boolean noCache = NOCACHE_DEFAULT;
            for (String header : headers) {
                String headerName = StringUtils.substringBefore(header, ":");
                String headerValue = StringUtils.substringAfter(header, ":");

                if (StringUtils.equalsIgnoreCase(headerName, ENCODING_PREFIX)) {
                    encoding = headerValue;
                } else if (StringUtils.equalsIgnoreCase(headerName,
                        NOCACHE_PREFIX)) {
                    noCache = Boolean.parseBoolean(headerValue);
				} else {
					throw new IllegalArgumentException(headerName
							+ getMessage("common.text.not.valid.header.type",
									"不是一个合法的header类型"));
				}
			}
            // 设置headers参数
            String fullContentType = contentType + ";charset=" + encoding;
            response.setContentType(fullContentType);
            if (noCache) {
                response.setHeader("Pragma", "No-cache");
                response.setHeader("Cache-Control", "no-cache");
                response.setDateHeader("Expires", 0);
            }

            response.getWriter().write(content);
            response.getWriter().flush();

        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            // Response在不同应用服务器中实现不同。在WAS中需要关闭。
        }
    }

    /**
     * @param text    输出内容
     * @param headers
     */
    public void renderText(String text, String... headers) {
        render("text/plain", text, headers);
    }

    /**
     * @param html
     * @param headers
     */
    public void renderHtml(String html, String... headers) {
        render("text/html", html, headers);
    }

    /**
     * @param xml
     * @param headers
     */
    public void renderXml(String xml, String... headers) {
        render("text/xml", xml, headers);
    }

    /**
     * @param string
     * @param headers
     */
    public void renderJson(String string, String... headers) {
        render("application/json", string, headers);
    }

    /**
     * @param map
     * @param headers
     */
    public void renderJson(Map<?, ?> map, String... headers) {
        renderJson(map, headers);
    }

    /**
     * @param object
     * @param headers
     */
    public void renderJson(Object object, String... headers) {
        JsonConfig config;
        if (isDefaultJsonConfig) {
            config = DEFAULT_JSON_CONFIG;
        } else {
            config = this.getJsonConfig();
        }
        String jsonString;
        if (config != null) {
            jsonString = JSONObject.fromObject(object, config).toString();
        } else {
            jsonString = JSONObject.fromObject(object).toString();
        }
        renderJson(jsonString, headers);
    }

    /**
     * 文件流输出，可以用于文件下载
     *
     * @param contentType
     * @param inputStream
     * @param headers
     */
    public void renderStream(String contentType, InputStream inputStream, String... headers) {
        HttpServletResponse response = this.getResponse();
        try {
            response.setContentType(contentType);
            int count = IOUtils.copy(inputStream, response.getOutputStream());
            //文件大于2g的使用缓存流操作
            if (count == -1) {
                IOUtils.copyLarge(inputStream, response.getOutputStream());
            }
            response.getOutputStream().flush();
        } catch (IOException e) {
            log.error(getMessage("common.text.outputstream.error","输出流错误：") + e.getMessage());
            e.printStackTrace();

        }
    }


    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }


    public boolean isDefaultJsonConfig() {
        return isDefaultJsonConfig;
    }

    public void setDefaultJsonConfig(boolean defaultJsonConfig) {
        isDefaultJsonConfig = defaultJsonConfig;
    }

    public JsonConfig getJsonConfig() {
        return jsonConfig;
    }

    public void setJsonConfig(JsonConfig jsonConfig) {
        this.jsonConfig = jsonConfig;
    }


    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request,
                             HttpServletResponse response) {

        this.request = request;
        this.response = response;
        //上下文路径
        request.setAttribute("context", request.getContextPath());
        DEFAULT_JSON_CONFIG.registerJsonValueProcessor(Date.class,
                new DateJsonValueProcessor(DEFAULT_DATETIME_FORMAT));
        DEFAULT_JSON_CONFIG.registerJsonValueProcessor(java.sql.Date.class,
                new DateJsonValueProcessor(DEFAULT_DATETIME_FORMAT));
        DEFAULT_JSON_CONFIG.registerJsonValueProcessor(Timestamp.class,
                new DateJsonValueProcessor(DEFAULT_DATETIME_FORMAT));


        DEFAULT_JSON_CONFIG.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

    }


    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
        // 时间类型转换
        binder.registerCustomEditor(Date.class, new DateEditor());
        binder.registerCustomEditor(String.class, new StringEscapeEditor(false, false, false, false));
        binder.registerCustomEditor(Date.class, new DateEditor());
        binder.registerCustomEditor(BigDecimal.class, new BigDecimalEditor());
        binder.registerCustomEditor(int.class, new IntegerEditor());
        binder.registerCustomEditor(long.class, new LongEditor());
        binder.registerCustomEditor(double.class, new DoubleEditor());
        binder.registerCustomEditor(float.class, new FloatEditor());
    }

    /**
     * 返回成功状态到页面的json数据
     *
     * @param data
     */
    protected void successJson(Object data) {
        renderJson(Result.buildSuccess(data));
    }

    /**
     * 返回错误状态到页面的json数据
     *
     * @param msg
     */
    protected void errorJson(String msg) {
        renderJson(Result.buildError(msg));
    }


    @ExceptionHandler
    public void exception(HttpServletRequest request,
                          HttpServletResponse response, Exception e) throws Exception {

        String requestType = request.getHeader("X-Requested-With");
        //ajax提交的通过json格式返回
        if (StringUtils.equalsIgnoreCase("XMLHttpRequest", requestType) || isMoblie()) {
            // 输出未知异常
            e.printStackTrace();
            log.warn(getMessage("common.text.ajax.request.exception.address","ajax请求异常，地址{}"), request.getRequestURI());
            // 异常信息
            String exceptionMsg = e.getMessage();
            if (e instanceof LoginStateExcepton) {
                errorJson("nologin");
                return;
            }
            // 其他未知类型错误
            if (StringUtils.isEmpty(exceptionMsg)) {
                errorJson(getMessage("common.text.operation.failed","操作失败！"));
                return;
            }
            errorJson(getMessage("common.text.operation.failed.try.again","操作失败，请重试！"));

        } else {
            e.printStackTrace();
            throw e;
        }

    }


    /**
     * 判断是否移动端访问
     *
     * @return
     */
    protected boolean isMoblie() {
        //默认非手机浏览器
        boolean isMobile = false;
        //据说改成spring 的判断方式比较准
        Device currentDevice = DeviceUtils.getCurrentDevice(getRequest());
        if (currentDevice != null) {
            isMobile = currentDevice.isMobile();
        }
        return isMobile;
    }


    /**
     * 验证表单
     *
     * @param bindingResult
     * @return 验证通过返回true；验证失败返回false
     */
    protected boolean validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //默认返回第一个错误信息
            errorJson(bindingResult.getAllErrors().get(0).getDefaultMessage());
            return false;
        }
        return true;
    }


    /***
     *h 获取当前session中的用户信息
     * @return
     */
    protected OnlineUser getCurrentUser() {
    	OnlineUser user = (OnlineUser) this.getRequest().getSession().getAttribute(OnlineUser.ONLINE_USER_TAG);
        if (user == null) {
            throw new LoginStateExcepton();
        }

        return user;
    }

    /**
     * 获取 国际化的信息
     *
     * @param code 对应的key值
     * @return
     */
    public String getMessage(String code) {
        return getMessage(code, null);
    }


    /**
     * Retrieve the message for the given code, using the "defaultHtmlEscape" setting.
     *
     * @param code           code of the message
     * @param defaultMessage String to return if the lookup fails
     * @return the message
     */
    public String getMessage(String code, String defaultMessage) {
        return getMessage(code, null, defaultMessage);
    }

    /**
     * Retrieve the message for the given code, using the "defaultHtmlEscape" setting.
     *
     * @param code           code of the message
     * @param args           arguments for the message, or {@code null} if none
     * @param defaultMessage String to return if the lookup fails
     * @return the message
     */
    public String getMessage(String code, Object[] args, String defaultMessage) {
        Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
        String message = SpringContextUtil.getApplicationContext().getMessage(code, args, defaultMessage, locale);
        return message;
    }


}
