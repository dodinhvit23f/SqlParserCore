package com.sotatek.sql.parser.xml.paser;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.sotatek.sql.parser.constant.QueryTag;
import com.sotatek.sql.parser.dto.NodeQuery;
import com.sotatek.sql.parser.xml.handler.SqlServerXMLHandler;
import org.xml.sax.SAXException;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class SqlServerXmlParser implements SqlXmlParser {

  final SAXParserFactory saxParserFactory;

  @Override
  public Map<String, List<String>> getTablesInFile(File xmlFile) {

    try {
      Set<NodeQuery> sqlQueries = extractSqlQueries(xmlFile);
      handleExtractQueries(sqlQueries);

    } catch (ParserConfigurationException | IOException | SAXException e) {
      log.error(e.getLocalizedMessage());
      log.error(e.getMessage());
      return Collections.emptyMap();
    }
    return Collections.emptyMap();
  }

  private void handleExtractQueries(Set<NodeQuery> sqlQueries){
    sqlQueries.forEach( sqlQuery -> {
      extractSqlSubQueries(sqlQuery);
    });
  }

  private void extractSqlSubQueries(NodeQuery sqlQuery) {
    String query = sqlQuery.getQuery();
    List<String> words = Arrays.stream(query.split(" "))
        .filter(string -> !ObjectUtils.isEmpty(string.trim()))
        .toList();


    if(sqlQuery.getQueryTag() == QueryTag.SELECT){

    }

    System.out.println();
  }

  private Set<NodeQuery> extractSqlQueries(File xmlFile)
      throws ParserConfigurationException, SAXException, IOException {
    SAXParser saxParser = saxParserFactory.newSAXParser();
    SqlServerXMLHandler sqlServerHandler = new SqlServerXMLHandler();
    saxParser.parse(xmlFile, sqlServerHandler);
    return sqlServerHandler.getSqlQueue();
  }
}
