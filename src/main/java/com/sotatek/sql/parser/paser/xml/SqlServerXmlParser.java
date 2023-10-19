package com.sotatek.sql.parser.paser.xml;

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

import com.sotatek.sql.parser.model.NodeQuery;
import com.sotatek.sql.parser.handler.xml.SqlServerXMLHandler;
import org.xml.sax.SAXException;

import static com.sotatek.sql.parser.constant.SqlKeyWord.INTO;

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

  private void handleExtractQueries(Set<NodeQuery> sqlQueries) {
    sqlQueries.forEach(sqlQuery -> {
      extractSqlSubQueries(sqlQuery, 0);
    });
  }

  private void extractSqlSubQueries(NodeQuery sqlQuery, int index) {
    String query = sqlQuery.getQuery();
    List<String> words = Arrays.stream(query.split(" "))
        .filter(string -> !ObjectUtils.isEmpty(string.trim()))
        .toList();

    switch (sqlQuery.getQueryTag()) {
      case SELECT:
        break;
      case UPDATE:
        break;
      case INSERT:

        break;
      case EXEC:
        break;
      case DELETE:
        break;
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
