package com.lvtulife.system.component.oauth2;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvtulife.base.component.message.Code;
import com.lvtulife.base.component.message.Message;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 自定义异常输出
 */
public class Oauth2ExceptionMessageConverter extends AbstractHttpMessageConverter<OAuth2Exception> {

    public final static Charset UTF8 = Charset.forName("UTF-8");

    public Oauth2ExceptionMessageConverter() {
        super(new MediaType("application", "json", UTF8), new MediaType("application", "*+json", UTF8), new MediaType("text", "html", UTF8), new MediaType("application", "x-javascript"));
    }

    //从request里获得构造DefaultOAuth2AccessToken实例的数据
    protected OAuth2Exception readInternal(Class<? extends OAuth2Exception> clazz,
                                           HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream in = inputMessage.getBody();
        byte[] buf = new byte[1024];
        for (; ; ) {
            int len = in.read(buf);
            if (len == -1) {
                break;
            }
            if (len > 0) {
                baos.write(buf, 0, len);
            }
        }
        byte[] bytes = baos.toByteArray();
        return JSON.parseObject(bytes, 0, bytes.length, UTF8.newDecoder(), clazz);
    }

    //只支持DefaultOAuth2AccessToken类
    protected boolean supports(Class<?> clazz) {
        return OAuth2Exception.class.isAssignableFrom(clazz);
    }

    //将DefaultOAuth2AccessToken实例转换成你想要的字符串格式
    protected void writeInternal(OAuth2Exception person, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        ObjectMapper objectMapper = new ObjectMapper();
        //{"error":"invalid_grant","error_description":"坏的凭证"}
        /*JsonGenerator generator = objectMapper.getFactory().createGenerator(outputMessage.getBody(), JsonEncoding.UTF8);
        generator.writeObject(person);*/

        // 自定义错误内容输出 {"number":"3000","flag":false,"msg":"认证失败！","detailMsg":"坏的凭证","data":null}
        Message<Object> msg = new Message<Object>(Code.C300);
        msg.setDetail(person.getMessage());
        JsonGenerator generator = objectMapper.getFactory().createGenerator(outputMessage.getBody(), JsonEncoding.UTF8);
        generator.writeObject(msg);
    }

}
