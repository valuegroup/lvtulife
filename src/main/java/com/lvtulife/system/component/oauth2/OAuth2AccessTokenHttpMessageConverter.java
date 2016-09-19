package com.lvtulife.system.component.oauth2;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class OAuth2AccessTokenHttpMessageConverter extends AbstractHttpMessageConverter<DefaultOAuth2AccessToken> {

    public final static Charset UTF8 = Charset.forName("UTF-8");

    public OAuth2AccessTokenHttpMessageConverter() {
        super(new MediaType("application", "json", UTF8), new MediaType("application", "*+json", UTF8), new MediaType("text", "html", UTF8), new MediaType("application", "x-javascript"));
    }

    //从request里获得构造DefaultOAuth2AccessToken实例的数据
    protected DefaultOAuth2AccessToken readInternal(Class<? extends DefaultOAuth2AccessToken> clazz,
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
        return DefaultOAuth2AccessToken.class.isAssignableFrom(clazz);
    }

    //将DefaultOAuth2AccessToken实例转换成你想要的字符串格式
    protected void writeInternal(DefaultOAuth2AccessToken person, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonGenerator generator = objectMapper.getFactory().createGenerator(outputMessage.getBody(), JsonEncoding.UTF8);
        generator.writeObject(person);
    }

}