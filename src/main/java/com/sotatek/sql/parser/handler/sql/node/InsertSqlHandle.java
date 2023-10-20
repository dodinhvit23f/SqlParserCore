package com.sotatek.sql.parser.handler.sql.node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.sotatek.sql.parser.model.NodeQuery;

import static com.sotatek.sql.parser.constant.SqlKeyWord.INTO;
import static com.sotatek.sql.parser.constant.SqlKeyWord.SELECT;
import static com.sotatek.sql.parser.constant.SqlKeyWord.VALUES;
import static com.sotatek.sql.parser.constant.SqlKeyWord.getSqlTag;

@Component
public class InsertSqlHandle {

  public static final String AN_OBJECT = ",";
  public static final String COMMA = AN_OBJECT;

  /**
   * \ handle INSERT INTO #{table-name} (#{select-items}) VALUES ...  <br> handle INSERT INTO
   * #{table-name} (#{select-items}) SELECT
   *
   * @param sqlQuery
   * @param words
   */
  public void handleData(NodeQuery sqlQuery, List<String> words) {
    boolean passTableName = false;

    for (int index = 0; index < sqlQuery.getQuery().size(); index++) {
      // table name always behind INTO
      if (index > 1 && words.get(index - 1).equals(INTO)) {
        sqlQuery.getTables().add(words.get(index));
        passTableName = Boolean.TRUE;
        continue;
      }

      if (passTableName) {
        String currentWord = words.get(index);
        // get selected items to insert
        if (currentWord.equals("(")) {
          int endSelectItemPosition = IntStream.range(index + 1, words.size())
              .filter(position -> words.get(position).equals(")"))
              .findFirst().getAsInt();

          sqlQuery.setSelectItems(IntStream.range(index + 2, endSelectItemPosition)
              .mapToObj(position -> words.get(position).strip())
              .filter(word -> !word.equals(COMMA))
              .toList());
          index = endSelectItemPosition;
          continue;
        }

        if (currentWord.equals(VALUES)) {
          // TODO handle VALUES later
          return;
        }

        if (currentWord.equals(SELECT)) {
          List<String> subWords = words.subList(index, words.size() - 1);

          NodeQuery subQuery = NodeQuery.builder()
              .query(subWords)
              .tables(new HashSet<>())
              .subQueries(new ArrayList<>())
              .queryTag(getSqlTag(subWords))
              .build();

          sqlQuery.getSubQueries().add(subQuery);

          return;
        }
      }
    }
  }

}

