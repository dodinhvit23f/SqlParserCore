package com.sotatek.sql.parser.sql.server;

import java.io.FileNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import com.sotatek.sql.parser.xml.paser.SqlXmlParser;

import org.junit.jupiter.api.Test;

@SpringBootTest
@RequiredArgsConstructor
class SqlServerParserApplicationTests {

  @Autowired
  SqlXmlParser sqlServerParse;

  @Test
  void regularCase() throws FileNotFoundException {
    sqlServerParse.getTablesInFile(ResourceUtils.getFile("classpath:sql/server/PrcsRegMapper.xml"));

  }

}
