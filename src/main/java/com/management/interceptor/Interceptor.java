package com.management.interceptor;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWTVerifyException;

import com.management.constant.Constants;
import com.management.dto.ResultCode;
import com.management.dto.ResultDto;
import com.management.dto.ResultDtoFactory;
import com.management.util.RedisUtilsTemplate;
import com.management.util.TokenUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author lyf
 *
 */
public class Interceptor implements HandlerInterceptor {
	
	private static final String TWO = "2";
	
	private static final String THREE = "3";

    private RedisUtilsTemplate redisUtilsTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException
            {

        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, x-access-token, login-type, key");
        OutputStream os = response.getOutputStream();

        Enumeration<String> en = request.getHeaderNames();
        Map<String, String> map = new HashMap<String, String>();
        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        //获取请求头中的token
        String httpToken = map.get("x-access-token");
        //获取请求头中的登陆用户类型
        String loginType = map.get("login-type");
        //获取请求头中toke的Key
        String httpKey = map.get("key");
        
        try {
        	String redisToken = null;
        	//判断，如果类型为2，表示为教师发出的请求，直接从redis中取出token，省略解析token的步骤
        	if(loginType.equals(TWO)){
        		if(redisUtilsTemplate == null) {
                	WebApplicationContext factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
                    redisUtilsTemplate = (RedisUtilsTemplate) factory.getBean("redisUtil");
                }
        		redisToken = redisUtilsTemplate.get(httpKey);
        	} else if(loginType.equals(THREE)){
        		//如果类型为3，表示为管理员登陆，需要解析后验证token的有效性
        		Long id = TokenUtil.parseToken(httpToken).getId();
                String key = Constants.USER_lOGIN_KEY + id;
                if(redisUtilsTemplate == null) {
                	WebApplicationContext factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
                    redisUtilsTemplate = (RedisUtilsTemplate) factory.getBean("redisUtil");
                }
                redisToken = redisUtilsTemplate.get(key);
        	}

        	//验证用户发出请求是带来的token与数据库中的token做对比
            if (redisToken.equals(httpToken)) {
                return true;
            } else {
                ResultDto dto = ResultDtoFactory.toError(ResultCode.INVALIDE_TOKEN_ERROR);
                String resultDto = JSON.toJSONString(dto);
                os.write(resultDto.getBytes("utf-8"));
                os.close();
                return false;
            }
        } catch (InvalidKeyException e1) {
            ResultDto dto = ResultDtoFactory.toError(ResultCode.INVALID_KEY_ERROR);
            String resultDto = JSON.toJSONString(dto);
            os.write(resultDto.getBytes("utf-8"));
            os.close();
            return false;
        } catch (NoSuchAlgorithmException e2) {
            ResultDto dto = ResultDtoFactory.toError(ResultCode.NO_SUCH_ALGORITHM_ERROR);
            String resultDto = JSON.toJSONString(dto);
            os.write(resultDto.getBytes("utf-8"));
            os.close();
            return false;
        } catch (IllegalStateException e3) {
            ResultDto dto = ResultDtoFactory.toError(ResultCode.ILLEGAL_STATE_ERROR);
            String resultDto = JSON.toJSONString(dto);
            os.write(resultDto.getBytes("utf-8"));
            os.close();
            return false;
        } catch (SignatureException e4) {
            ResultDto dto = ResultDtoFactory.toError(ResultCode.SIGNATURE_ERROR);
            String resultDto = JSON.toJSONString(dto);
            os.write(resultDto.getBytes("utf-8"));
            os.close();
            return false;
        } catch (IOException e5) {
            ResultDto dto = ResultDtoFactory.toError(ResultCode.IO_ERROR);
            String resultDto = JSON.toJSONString(dto);
            os.write(resultDto.getBytes("utf-8"));
            os.close();
            return false;
        } catch (JWTVerifyException e6) {
            ResultDto dto = ResultDtoFactory.toError(ResultCode.SESSION_TIME_OUT);
            String resultDto = JSON.toJSONString(dto);
            os.write(resultDto.getBytes("utf-8"));
            os.close();
            return false;
        } catch (NullPointerException e) {
        	ResultDto dto = ResultDtoFactory.toError(ResultCode.MISSING_FIELD);
            String resultDto = JSON.toJSONString(dto);
            os.write(resultDto.getBytes("utf-8"));
            os.close();
            return false;
		}
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}
