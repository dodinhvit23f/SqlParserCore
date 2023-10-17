package com.sotatek.sql.parser.xml.paser;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface SqlXmlParser {

  Map<String, List<String>> getTablesInFile(File xmlFile);
}
