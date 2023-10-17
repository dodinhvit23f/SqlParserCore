package com.sotatek.sql.parser.configuration;

import javax.xml.parsers.SAXParserFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

  @Bean
  SAXParserFactory getSaxParserFactory(){
    return SAXParserFactory.newInstance();
  }

}
