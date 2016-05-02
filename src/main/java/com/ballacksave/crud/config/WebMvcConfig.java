package com.ballacksave.crud.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configurable
public class WebMvcConfig extends WebMvcConfigurerAdapter {
}
