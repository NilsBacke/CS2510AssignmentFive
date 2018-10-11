import tester.Tester;

/* template for IXMLFrag
 * Fields: 
 * Methods: 
 * sameXMLFrag ... boolean
 * Methods for fields: 
 */

// Interface to represent an XMLFrag
interface IXMLFrag {

  // returns whether the given IXMLFrag is the same as this IXMLFrag
  boolean sameXMLFrag(IXMLFrag other);

}

// Class to represent plain text
class Plaintext implements IXMLFrag {

  String txt;

  // Constructor
  Plaintext(String txt) {
    this.txt = txt;
  }

  /*
   * template for Plaintext Fields: this.txt ... String
   * 
   * Methods: sameXMLFrag ... boolean
   * 
   * Methods for fields:
   */

  @Override
  public boolean sameXMLFrag(IXMLFrag other) {
    if (other instanceof Plaintext) {
      return ((Plaintext) other).txt.equals(this.txt);
    }
    else {
      return false;
    }
  }
}

// Class to represent a tagged XML Frag
class Tagged implements IXMLFrag {
  Tag tag;
  ILoXMLFrag content;

  // Constructor
  Tagged(Tag tag, ILoXMLFrag content) {
    this.tag = tag;
    this.content = content;
  }

  /*
   * template for Tagged Fields: this.tag ... Tag this.content ... ILoXMLFrag
   * 
   * Methods: sameXMLFrag ... boolean
   * 
   * Methods for fields:
   */

  // returns true if this tag is the same as the given tag
  @Override
  public boolean sameXMLFrag(IXMLFrag other) {
    if (other instanceof Tagged) {
      return ((Tagged) other).tag.sameTag(this.tag)
          && ((Tagged) other).content.sameXMLDoc(this.content);
    }
    else {
      return false;
    }
  }
}

/*
 * template for ILoXMLFrag Fields: Methods: sameXMLDoc ... boolean compareFirst
 * ... boolean compareRest ... boolean
 * 
 * Methods for fields:
 */

/*
 * A list of XMLFrag is one of empty (cons XMLFrag list-of-XMLFrag)
 */
interface ILoXMLFrag {
  // Computes the length (number of characters) of the content in an XML document.

  // returns true if the two lists are equal and in the same order
  boolean sameXMLDoc(ILoXMLFrag other);

  // returns true if the first element of both lists are equal
  boolean compareFirst(IXMLFrag frag);

  // returns true if the rest of boths lists are equal
  boolean compareRest(ILoXMLFrag frags);
}

// Class to represent a empty LoXMLFrag
class MtLoXMLFrag implements ILoXMLFrag {
  MtLoXMLFrag() {
    // Nothing to do
  }

  /*
   * template for MtLoXMLFrag Fields:
   * 
   * Methods: sameXMLDoc ... boolean compareFirst ... boolean compareRest ...
   * boolean
   * 
   * Methods for fields:
   */

  // returns true if both lists are empty
  @Override
  public boolean sameXMLDoc(ILoXMLFrag other) {
    return this.getClass() == other.getClass();
  }

  // always false because this is an empty list
  @Override
  public boolean compareFirst(IXMLFrag frag) {
    return false;
  }

  // always false because this is an empty list
  @Override
  public boolean compareRest(ILoXMLFrag frags) {
    return this.getClass() == frags.getClass();
  }
}

// Class to represent a cons LoXMLFrag
class ConsLoXMLFrag implements ILoXMLFrag {
  IXMLFrag first;
  ILoXMLFrag rest;

  // Constructor
  ConsLoXMLFrag(IXMLFrag first, ILoXMLFrag rest) {
    this.first = first;
    this.rest = rest;

  }

  /*
   * template for ConsLoXMLFrag Fields: this.first ... IXMLFrag this.rest ...
   * ILoXMLFrag
   * 
   * Methods: sameXMLDoc ... boolean compareFirst ... boolean compareRest ...
   * boolean
   * 
   * Methods for fields:
   */

  @Override
  public boolean sameXMLDoc(ILoXMLFrag other) {
    return other.compareFirst(this.first) && other.compareRest(this.rest);
  }

  @Override
  public boolean compareFirst(IXMLFrag frag) {
    return this.first.sameXMLFrag(frag);
  }

  @Override
  public boolean compareRest(ILoXMLFrag frags) {
    return this.rest.sameXMLDoc(frags);
  }
}

// Class to represent a Tag
class Tag {
  String name;
  ILoAtt atts;
  // Constructor

  Tag(String name, ILoAtt atts) {
    this.name = name;
    this.atts = atts;
  }

  /*
   * template for Tag Fields: this.name ... String this.atts ... ILoAtt
   * 
   * Methods: sameTag ... boolean Methods for fields:
   */

  // determines if the tags are the same
  boolean sameTag(Tag t) {
    return this.name.equals(t.name) && this.atts.sameAtts(t.atts);
  }

}

// Class to represent an Att
class Att {
  String name;
  String value;

  // Constructor
  Att(String name, String value) {
    this.name = name;
    this.value = value;
  }

  /*
   * template for Att Fields: this.name ... String this.value ... String
   * 
   * Methods: sameAttribute ... boolean
   * 
   * Methods for fields:
   */

  // checks to see if the names and values are equal
  public boolean sameAttribute(Att a) {
    return this.name.equals(a.name) && this.value.equals(a.value);
  }
}

/*
 * A list of Att is one of empty (cons Att list-of-Att)
 */

// represents an ILoAtt
interface ILoAtt {

  // returns true if this has the same attributes and same order of attributes as
  // the given ILoAtt
  boolean sameAtts(ILoAtt other);

  // returns true if the first element of both lists are equal
  boolean compareFirst(Att a);

  // returns true if the rest of boths lists are equal
  boolean compareRest(ILoAtt atts);
}

// Class to represent a cons LoAtt
class ConsLoAtt implements ILoAtt {
  Att first;
  ILoAtt rest;

  // Constructor
  ConsLoAtt(Att first, ILoAtt rest) {
    this.first = first;
    this.rest = rest;

  }

  /*
   * template for ConsLoAtt Fields: this.first ... Att this.rest ... ILoAtt
   * 
   * Methods: sameAtts ... boolean compareFirst ... boolean compareRest ...
   * boolean
   * 
   * Methods for fields:
   */

  // returns true if the two lists of atts are the same and in the same order
  @Override
  public boolean sameAtts(ILoAtt other) {
    return other.compareFirst(this.first) && other.compareRest(this.rest);
  }

  // returns true if the first of this is the same as the given Att
  @Override
  public boolean compareFirst(Att a) {
    return this.first.sameAttribute(a);
  }

  // returns true if the rest of this is the same as the given ILoAtt
  @Override
  public boolean compareRest(ILoAtt atts) {
    return this.rest.sameAtts(atts);
  }
}

// Class to represent an empty LoAtt
class MtLoAtt implements ILoAtt {
  MtLoAtt() {
  }

  /*
   * template for MtLoAtt Fields: Methods: sameAtts ... boolean compareFirst ...
   * boolean compareRest ... boolean
   * 
   * Methods for fields:
   */

  // checks if the given ILoAtt is an empty list
  @Override
  public boolean sameAtts(ILoAtt other) {
    return this.getClass() == other.getClass();
  }

  // returns false because the lists must not be equal
  @Override
  public boolean compareFirst(Att a) {
    return false;
  }

  // checks if the given ILoAtt is an empty list
  @Override
  public boolean compareRest(ILoAtt atts) {
    return this.getClass() == atts.getClass();
  }
}

// Examples of XML
class ExamplesXML {

  // Example of plain text
  Plaintext pt = new Plaintext("I am XML!");

  // Example of attributes
  Att attVolume = new Att("volume", "30db");
  Att attVolumeDup = new Att("volume", "30db");
  Att attDuration = new Att("duration", "5sec");
  Att attSize = new Att("size", "2inches");
  Att attSizeDup = new Att("size", "2inches");
  Att attVolume2 = new Att("volume", "50db");
  Att attDuration2 = new Att("duration", "10sec");
  Att attSize2 = new Att("size", "10inches");

  // ILoAtt
  ILoAtt loatt1 = new ConsLoAtt(this.attVolume, new ConsLoAtt(this.attDuration, new MtLoAtt()));
  ILoAtt loattEm = new MtLoAtt();

  // Example 1
  ILoXMLFrag xml1 = new ConsLoXMLFrag(new Plaintext("I am XML!"), new MtLoXMLFrag());

  // Example 2
  ILoXMLFrag xml2 = new ConsLoXMLFrag(new Plaintext("I am "),
      new ConsLoXMLFrag(
          new Tagged(new Tag("yell", new MtLoAtt()),
              new ConsLoXMLFrag(new Plaintext("XML"), new MtLoXMLFrag())),
          new ConsLoXMLFrag(new Plaintext("!"), new MtLoXMLFrag())));

  // Example 3
  ILoXMLFrag xml3 = new ConsLoXMLFrag(new Plaintext("I am "),
      new ConsLoXMLFrag(
          new Tagged(new Tag("yell", new MtLoAtt()),
              new ConsLoXMLFrag(
                  new Tagged(new Tag("italic", new MtLoAtt()),
                      new ConsLoXMLFrag(new Plaintext("X"), new MtLoXMLFrag())),
                  new ConsLoXMLFrag(new Plaintext("ML"), new MtLoXMLFrag()))),
          new ConsLoXMLFrag(new Plaintext("!"), new MtLoXMLFrag())));

  // Example 4
  ILoXMLFrag xml4 = new ConsLoXMLFrag(new Plaintext("I am "),
      new ConsLoXMLFrag(
          new Tagged(new Tag("yell", new ConsLoAtt(this.attVolume, new MtLoAtt())),
              new ConsLoXMLFrag(
                  new Tagged(new Tag("italic", new MtLoAtt()),
                      new ConsLoXMLFrag(new Plaintext("X"), new MtLoXMLFrag())),
                  new ConsLoXMLFrag(new Plaintext("ML"), new MtLoXMLFrag()))),
          new ConsLoXMLFrag(new Plaintext("!"), new MtLoXMLFrag())));

  // Example 5
  ILoXMLFrag xml5 = new ConsLoXMLFrag(new Plaintext("I am "),
      new ConsLoXMLFrag(
          new Tagged(
              new Tag("yell",
                  new ConsLoAtt(this.attVolume, new ConsLoAtt(this.attDuration, new MtLoAtt()))),
              new ConsLoXMLFrag(
                  new Tagged(new Tag("italic", new MtLoAtt()),
                      new ConsLoXMLFrag(new Plaintext("X"), new MtLoXMLFrag())),
                  new ConsLoXMLFrag(new Plaintext("ML"), new MtLoXMLFrag()))),
          new ConsLoXMLFrag(new Plaintext("!"), new MtLoXMLFrag())));

  // Dup Example
  ILoXMLFrag xml5Dup = new ConsLoXMLFrag(new Plaintext("I am "),
      new ConsLoXMLFrag(
          new Tagged(
              new Tag("yell",
                  new ConsLoAtt(this.attVolume, new ConsLoAtt(this.attDuration, new MtLoAtt()))),
              new ConsLoXMLFrag(
                  new Tagged(new Tag("italic", new MtLoAtt()),
                      new ConsLoXMLFrag(new Plaintext("X"), new MtLoXMLFrag())),
                  new ConsLoXMLFrag(new Plaintext("ML"), new MtLoXMLFrag()))),
          new ConsLoXMLFrag(new Plaintext("!"), new MtLoXMLFrag())));

  // Example of plain text XML with yell and italic tags
  Tagged xmlItalic = new Tagged(new Tag("italic", new MtLoAtt()),
      new ConsLoXMLFrag(new Plaintext("X"), new MtLoXMLFrag()));

  // Example of a Tagged XMLFrag
  Tagged tagEx = new Tagged(
      new Tag("yell",
          new ConsLoAtt(this.attVolume, new ConsLoAtt(this.attDuration, new MtLoAtt()))),
      new ConsLoXMLFrag(new Plaintext("ML"), new ConsLoXMLFrag(xmlItalic, new MtLoXMLFrag())));

  // Example of a LoXMLFrag
  ILoXMLFrag ILo1 = new ConsLoXMLFrag(new Plaintext("ML"),
      new ConsLoXMLFrag(xmlItalic, new MtLoXMLFrag()));

  // Example of an LoXMLFrag with tag names "yell", "italic"
  ILoXMLFrag ILo2 = new ConsLoXMLFrag(tagEx, new ConsLoXMLFrag(xmlItalic, new MtLoXMLFrag()));

  // Example of an LoXMLFrag with tag names "yell", "italic"
  ILoXMLFrag ILo3 = new ConsLoXMLFrag(xmlItalic, new ConsLoXMLFrag(tagEx, new MtLoXMLFrag()));

  // Example of an LoXMLFrag
  ILoXMLFrag ILo4 = new ConsLoXMLFrag(
      new Tagged(new Tag("big", new ConsLoAtt(this.attSize, new MtLoAtt())), ILo2),
      new ConsLoXMLFrag(new Tagged(new Tag("that", new ConsLoAtt(this.attDuration, new MtLoAtt())),
          new MtLoXMLFrag()), new MtLoXMLFrag()));

  // Duplicate Example of an LoXMLFrag
  ILoXMLFrag ILo4Dup = new ConsLoXMLFrag(
      new Tagged(new Tag("big", new ConsLoAtt(this.attSize, new MtLoAtt())), ILo2),
      new ConsLoXMLFrag(new Tagged(new Tag("that", new ConsLoAtt(this.attDuration, new MtLoAtt())),
          new MtLoXMLFrag()), new MtLoXMLFrag()));

  // Example of an empty ILoXMLFrag
  ILoXMLFrag emptyXML = new MtLoXMLFrag();

  // Example of an ILoXMLFrag
  ILoXMLFrag ILo5 = new ConsLoXMLFrag(
      new Tagged(new Tag("Yell", new ConsLoAtt(this.attVolume, new MtLoAtt())), new MtLoXMLFrag()),
      new MtLoXMLFrag());

  // Example of an ILoXMLFrag
  ILoXMLFrag ILo6 = new ConsLoXMLFrag(new Plaintext("1234"),
      new ConsLoXMLFrag(xmlItalic, new ConsLoXMLFrag(new Plaintext("678"), new MtLoXMLFrag())));

  // Example of an XMLFrag
  IXMLFrag xmlFragEx1 = new Tagged(new Tag("big", new ConsLoAtt(this.attSize, new MtLoAtt())),
      ILo2);
  IXMLFrag xmlFragEx1Dup = new Tagged(new Tag("big", new ConsLoAtt(this.attSize, new MtLoAtt())),
      ILo2);
  IXMLFrag xmlFragEx2 = new Tagged(new Tag("small", new ConsLoAtt(this.attSize, new MtLoAtt())),
      ILo3);

  // Examples of tags
  Tag tagEx1 = new Tag("Yell", new ConsLoAtt(this.attVolume, new MtLoAtt()));
  Tag tagEx2 = new Tag("the", new ConsLoAtt(this.attDuration, new MtLoAtt()));
  Tag tagEx3 = new Tag("it", new ConsLoAtt(this.attVolume2, new MtLoAtt()));
  Tag tagEx4 = new Tag("Yell", new ConsLoAtt(this.attVolume2, new MtLoAtt()));
  Tag tagEx5 = new Tag("the", new ConsLoAtt(this.attDuration2, new MtLoAtt()));

  boolean testSameAttribute(Tester t) {
    // tests for sameAttribute
    return t.checkExpect(this.attVolume.sameAttribute(this.attVolumeDup), true)
        && t.checkExpect(this.attVolume.sameAttribute(this.attSize), false)
        && t.checkExpect(this.attSize.sameAttribute(this.attSizeDup), true)
        && t.checkExpect(this.attDuration.sameAttribute(this.attDuration2), false);
  }

  boolean testSameXMLFrag(Tester t) {
    // tests for sameAttribute
    return t.checkExpect(this.xmlFragEx1.sameXMLFrag(xmlFragEx1Dup), true)
        && t.checkExpect(this.xmlFragEx1.sameXMLFrag(this.xmlFragEx2), false);
  }

  boolean testSameXMLDoc(Tester t) {
    // tests for sameXMLDoc
    return t.checkExpect(this.xml5.sameXMLDoc(xml3), false)
        && t.checkExpect(this.xml5.sameXMLDoc(xml5Dup), true)
        && t.checkExpect(this.xml2.sameXMLDoc(this.xml1), false)
        && t.checkExpect(this.ILo4.sameXMLDoc(this.ILo4Dup), true);
  }

}