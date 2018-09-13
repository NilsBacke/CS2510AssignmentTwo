interface ILoXMLFrag {
  int count();
}

class MtLoXMLFrag implements ILoXMLFrag {

  MtLoXMLFrag() {
  }

  @Override
  public int count() {
    return 0;
  }

}

class ConsLoXMLFrag implements ILoXMLFrag {
  XMLFrag first;
  ILoXMLFrag rest;

  ConsLoXMLFrag(XMLFrag first, ILoXMLFrag rest) {
    this.first = first;
    this.rest = rest;
  }
  
  /*
   * TEMPLATE:
   * Fields:
   * ... this.first ...           - XMLFrag
   * ... this.rest ...            - ILoXMLFrag
   * Methods:
   * Methods on Fields:
   */

  @Override
  public int count() {
    return 1 + this.rest.count();
  }
}

interface XMLFrag {

}

class PlainText implements XMLFrag {

  String text;

  PlainText(String text) {
    this.text = text;
  }
  
  /*
   * TEMPLATE:
   * Fields:
   * ... this.text ...           - String
   * Methods:
   * Methods on Fields:
   */
}

class Tagged implements XMLFrag {

  Tag tag;
  ILoXMLFrag content;

  Tagged(Tag tag, ILoXMLFrag content) {
    this.tag = tag;
    this.content = content;
  }
  
  /*
   * TEMPLATE:
   * Fields:
   * ... this.tag ...           - Tag
   * ... this.content ...     - ILoXMLFrag
   * Methods:
   * Methods on Fields:
   */
}

class Tag {

  String name;
  ILoAtt atts;

  Tag(String name, ILoAtt atts) {
    this.name = name;
    this.atts = atts;
  }
  
  /*
   * TEMPLATE:
   * Fields:
   * ... this.name ...           - String
   * ... this.atts ...     - ILoAtt
   * Methods:
   * Methods on Fields:
   */
}

interface ILoAtt {
  int count();
}

class MtLoAtt implements ILoAtt {

  MtLoAtt() {
  }

  @Override
  public int count() {
    return 0;
  }
}

class ConsLoAtt implements ILoAtt {
  Att first;
  ILoAtt rest;

  ConsLoAtt(Att first, ILoAtt rest) {
    this.first = first;
    this.rest = rest;
  }
  
  /*
   * TEMPLATE:
   * Fields:
   * ... this.first ...           - Att
   * ... this.rest ...            - ILoAtt
   * Methods:
   * Methods on Fields:
   */

  @Override
  public int count() {
    return 1 + this.rest.count();
  }
}


class Att {

  String name;
  String value;

  Att(String name, String value) {
    this.name = name;
    this.value = value;
  }
  
  /*
   * TEMPLATE:
   * Fields:
   * ... this.name ...             - String
   * ... this.value ...            - String
   * Methods:
   * Methods on Fields:
   */
}

class ExamplesXML {

}
