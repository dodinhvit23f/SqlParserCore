package com.sotatek.sql.parser.handler.xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.util.ObjectUtils;

import com.sotatek.sql.parser.constant.QueryTag;
import com.sotatek.sql.parser.model.NodeQuery;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import static com.sotatek.sql.parser.constant.SqlKeyWord.INSERT;
import static com.sotatek.sql.parser.constant.SqlKeyWord.SELECT;
import static com.sotatek.sql.parser.constant.SqlKeyWord.UPDATE;
import static com.sotatek.sql.parser.constant.SqlKeyWord.EXEC;
import static com.sotatek.sql.parser.constant.SqlKeyWord.DELETE;
import static com.sotatek.sql.parser.constant.SqlKeyWord.getSqlTag;

@Slf4j
public class SqlServerXMLHandler extends DefaultHandler {

  public static final String MAPPER = "mapper";

  final Set<String> sqlKeyWord = new HashSet<>(
      Arrays.asList(SELECT, EXEC, UPDATE, INSERT, DELETE));
  final Stack<String> stack = new Stack<>();
  final Map<String, StringBuilder> sqlQueue = new HashMap<>();

  Boolean isSubTag = Boolean.FALSE;

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException {
    super.startElement(uri, localName, qName, attributes);
    System.out.println(qName + " - " + attributes.getValue(0));

    if (qName.equals(MAPPER)) {
      return;
    }

    if (!stack.isEmpty()) {
      isSubTag = Boolean.TRUE;
    } else {
      isSubTag = Boolean.FALSE;
    }

    stack.push(attributes.getValue(0));
    System.out.println();
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    super.characters(ch, start, length);
    String extractText = new String(ch, start, length);

    StringBuilder builder = new StringBuilder();
    if (!isSubTag && isContainSqlKeyWord(extractText)) {
      builder.append(extractText);
      sqlQueue.put(stack.firstElement(), builder);
      return;
    }

    if (sqlQueue.containsKey(stack.firstElement())) {
      builder = sqlQueue.get(stack.firstElement());
      builder.append(extractText);
    }

    System.out.println(extractText);
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    super.endElement(uri, localName, qName);

    if (qName.equals(MAPPER)) {
      return;
    }

    stack.pop();
  }

  public Set<NodeQuery> getSqlQueue() {
    return sqlQueue.entrySet()
        .stream()
        .map(entry -> {
          List<String> query = Arrays.stream(entry.getValue().toString()
              .replaceAll("\t" ," ")
              .replaceAll("," ," , ").split(" "))
              .filter(string -> !ObjectUtils.isEmpty(string.trim()))
              .toList();

          return NodeQuery.builder()
              .id(entry.getKey())
              .queryTag(getSqlTag(query))
              .query(query)
              .tables(new HashSet<>())
              .subQueries(new ArrayList<>())
              .build();
        }).collect(Collectors.toSet());
  }

  Boolean isContainSqlKeyWord(String rawText) {
    for (String sqlKey : sqlKeyWord) {
      if (rawText.contains(sqlKey)) {
        return Boolean.TRUE;
      }
    }
    return Boolean.FALSE;
  }


}
