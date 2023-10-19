package com.sotatek.sql.parser.handler.sql.node;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sotatek.sql.parser.model.NodeQuery;

import static com.sotatek.sql.parser.constant.SqlKeyWord.INTO;

@Component
public class InsertSqlHandle {

  /**\
   *  handle INSERT INTO #{table-name} (#{select-items}) VALUES ...
   *  handle INSERT INTO #{table-name} (#{select-items}) SELECT
   * @param sqlQuery
   * @param words
   * @param index
   */
  public void handleData(NodeQuery sqlQuery, List<String> words, int index){


    for (; index < sqlQuery.getQuery().length(); index++) {
      // table name always behind INTO
      if (index > 1 && words.get(index - 1).equals(INTO)) {
        sqlQuery.getTables().add(words.get(index));
      }

      // get selected items to insert


    }
  }

}

