interface ILoXMLFrag {
  int count();
  int contentLength();
  boolean hasTag(String tagName);
}

class MtLoXMLFrag implements ILoXMLFrag {

  MtLoXMLFrag() {
  }

  @Override
  public int count() {
    return 0;
  }

  @Override
  public int contentLength() {
    return 0;
  }

  @Override
  public boolean hasTag(String tagName) {
    return false;
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
   * TEMPLATE: Fields: ... this.first ... - XMLFrag ... this.rest ... - ILoXMLFrag
   * Methods: Methods on Fields:
   */

  @Override
  public int count() {
    return 1 + this.rest.count();
  }

  @Override
  public int contentLength() {
    return first.contentLength() + this.rest.contentLength();
  }

  @Override
  public boolean hasTag(String tagName) {
    return this.first.hasTag(tagName) || this.rest.hasTag(tagName);
  }
}

interface XMLFrag {
  int contentLength();
  boolean hasTag(String tagName);
}

class PlainText implements XMLFrag {

  String text;

  PlainText(String text) {
    this.text = text;
  }
  
  /*
   * TEMPLATE: Fields: ... this.text ... - String Methods: Methods on Fields:
   */

  @Override
  public int contentLength() {
    return text.length();
  }

  @Override
  public boolean hasTag(String tagName) {
    return false;
  }
}

class Tagged implements XMLFrag {

  Tag tag;
  ILoXMLFrag content;

  Tagged(Tag tag, ILoXMLFrag content) {
    this.tag = tag;
    this.content = content;
  }
  
  /*
   * TEMPLATE: Fields: ... this.tag ... - Tag ... this.content ... - ILoXMLFrag
   * Methods: Methods on Fields:
   */

  @Override
  public int contentLength() {
    return content.contentLength();
  }

  @Override
  public boolean hasTag(String tagName) {
    return this.tag.getName().equals(tagName) || content.hasTag(tagName);
  }
 
}

class Tag {

  String name;
  ILoAtt atts;

  Tag(String name, ILoAtt atts) {
    this.name = name;
    this.atts = atts;
  }

  /*
   * TEMPLATE: Fields: ... this.name ... - String ... this.atts ... - ILoAtt
   * Methods: Methods on Fields:
   */
  
  String getName() {
    return this.name;
  }
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
   * TEMPLATE: Fields: ... this.first ... - Att ... this.rest ... - ILoAtt
   * Methods: Methods on Fields:
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
   * TEMPLATE: Fields: ... this.name ... - String ... this.value ... - String
   * Methods: Methods on Fields:
   */
}

class ExamplesXML {
  ILoXMLFrag xml1 = new ConsLoXMLFrag(new PlainText("I am XML!"), new MtLoXMLFrag());

  ILoXMLFrag xml2 = new ConsLoXMLFrag(new PlainText("I am "),
      new ConsLoXMLFrag(
          new Tagged(new Tag("yell", new MtLoAtt()),
              new ConsLoXMLFrag(new PlainText("XML"), new MtLoXMLFrag())),
          new ConsLoXMLFrag(new PlainText("!"), new MtLoXMLFrag())));

  ILoXMLFrag xml3 = new ConsLoXMLFrag(new PlainText("I am "), new ConsLoXMLFrag(
      new Tagged(new Tag("yell", new MtLoAtt()),
          new ConsLoXMLFrag(new Tagged(new Tag("italic", new MtLoAtt()),
              new ConsLoXMLFrag(new PlainText("X"), new MtLoXMLFrag())), new MtLoXMLFrag())),
      new ConsLoXMLFrag(new PlainText("!"), new MtLoXMLFrag())));

  // need more examples
}
