package com.sotatek.sql.parser.model;

import java.util.List;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import com.sotatek.sql.parser.constant.QueryTag;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NodeQuery {
  String id;
  String query;
  QueryTag queryTag;
  List<NodeQuery> subQueries;
  Set<String> tables;
  Set<String> selectItems;

}
