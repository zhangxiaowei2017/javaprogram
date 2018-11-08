package com.greer.springbootdemo2.HttpUnit;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class HttpFastJson {
    @Bean
    public HttpMessageConverters httpMessageConverters() {

        //定义json对象转换的配置信息
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        SerializerFeature serializerFeature[] = new SerializerFeature[]{
                SerializerFeature.WriteNullStringAsEmpty,
        };

        fastJsonConfig.setSerializerFeatures(serializerFeature);
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));

        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

        HttpMessageConverters httpMessageConverters = new HttpMessageConverters(fastJsonHttpMessageConverter) ;
        return httpMessageConverters;
    }
}
