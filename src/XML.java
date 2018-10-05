import tester.Tester;

// Interface to represent an XMLFrag
interface IXMLFrag {

  // Get contents length (size in characters)
  int getContentLength();

  // returns tag (if there is one)
  Tag getHasTag();

  // returns list of attribute (if there is one)
  boolean getHasAttribute(String inAttribute);

  // returns tag's content
  ILoXMLFrag getContent();

  // converts Frag into string
  public String asString();

  // Creates a new XML Frag with updated Attributes
  public IXMLFrag updateAtt(String inAtt, String inVal);

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
   * Template: FIELDS: this.txt -- String
   * 
   * METHODS: this.getContentLength() -- int this.getHasTag() -- Tag
   * this.getHasAttribute(String) -- boolean this.getContent() -- ILoXMLFrag
   * this.asString -- String this.updateAtt(String, String)
   * 
   * METHODS OF FIELDS: this.text.length() -- int
   */

  // Returns length of text in characters
  public int getContentLength() {
    return this.txt.length();
  }

  // Returns empty Tag because plain text does not contain tag
  public Tag getHasTag() {
    return new Tag("", new MtLoAtt());
  }

  // Returns false because plain text does not contain a tag or attributes
  public boolean getHasAttribute(String inAttribute) {
    return false;
  }

  // returns empty content
  public ILoXMLFrag getContent() {
    return new MtLoXMLFrag();
  }

  // Returns txt
  public String asString() {
    return this.txt;
  }

  // Returns this, because plain text does not contain attributes
  public IXMLFrag updateAtt(String inAtt, String inVal) {
    return this;
  }

  @Override
  public boolean sameXMLFrag(IXMLFrag other) {
    if (other instanceof Plaintext) {
      return ((Plaintext) other).txt.equals(this.txt); 
    } 
    return false;
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
   * Template: FIELDS: this.tag -- Tag this.content -- ILoXMLFrag
   * 
   * METHODS: this.getContentLength() -- int this.getHasTag() -- Tag
   * this.getHasAttribute(String) -- boolean this.getContent() -- ILoXMLFrag
   * this.asString() -- String this.updateAtt(String, String) -- ILoXMLFrag
   * 
   * FIELD METHODS: tag.getHasTagName() -- String tag.getHasAttributeList(String
   * inAttrinute) -- boolean content.hasTag(String) -- boolean
   * content.hasAttribute(String) -- boolean content.renderAsString() -- String
   * content.updateAttribute(String, String) -- ILoXMLFrag
   */

  // Returns total length of content in list (characters)
  public int getContentLength() {
    return this.content.contentLength();
  }

  // Returns tag
  public Tag getHasTag() {
    return this.tag;
  }

  // Returns true if list of Attributes contains given name
  public boolean getHasAttribute(String inAttribute) {
    return this.tag.getHasAttributeList(inAttribute);
  }

  // Returns content (list of XMLFrags)
  public ILoXMLFrag getContent() {
    return this.content;
  }

  // Returns the content as String (Tag does not have string representation)
  public String asString() {
    return this.content.renderAsString();
  }

  // Returns the Tag with updated Attributes as well as its content with updated
  // content (as a list)
  public IXMLFrag updateAtt(String inAtt, String inVal) {
    if (this.tag.getHasAttributeList(inAtt)) {
      return new Tagged(this.tag.newTag(inAtt, inVal), this.content.updateAttribute(inAtt, inVal));
    }
    else {
      return new Tagged(this.tag, this.content.updateAttribute(inAtt, inVal));
    }
  }

  // returns true if this tag is the same as the given tag
  @Override
  public boolean sameXMLFrag(IXMLFrag other) {
    if (other instanceof Tagged) {
      return ((Tagged) other).tag.sameTag(this.tag) && ((Tagged) other).content.sameXMLDoc(this.content);
    }
    return false;
  }
}

/*
 * A list of XMLFrag is one of empty (cons XMLFrag list-of-XMLFrag)
 */
interface ILoXMLFrag {
  // Computes the length (number of characters) of the content in an XML document.

  int contentLength();
  // Determines if an XML document contains a Tag with the given name.

  boolean hasTag(String inName);
  // Determines if an XML document contains an attribute with the given nane

  boolean hasAttribute(String inAttribute);
  // Determines if an XML document contains an attribute with the given
  // name within a given tag.

  boolean hasAttributeInTag(String inName, String inAttribute);
  // Converts XML to a String without tags and attributes.

  String renderAsString();
  // Updates all attributes with the given name to the given value

  ILoXMLFrag updateAttribute(String inAtt, String inVal);

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
   * Template: FIELDS: this.first -- IXMLFrag this.rest -- ILoXMLFrag
   * 
   * METHODS: this.contentLenth() -- int this.hasTag(String) -- boolean
   * this.hasAttribute(String) -- boolean this.hasAttributeInTag(String, String)
   * -- boolean this.renderAsString() -- String this.undateAttribute(String,
   * String) -- ILoXMLFrag
   */

  // Content Length of empty list = 0
  public int contentLength() {
    return 0;
  }

  // Empty list does not have tags --> returns false
  public boolean hasTag(String inName) {
    return false;
  }

  // Empty list does not have attributes --> returns false
  public boolean hasAttribute(String inAttribute) {
    return false;
  }

  public boolean hasAttributeInTag(String inName, String inAttribute) {
    return false;
  }

  // Returns an empty string because an MtFrag does not have any content to
  // convert
  public String renderAsString() {
    return "";
  }

  // Returns an empty LoFrags, because there are no Attribute to change in this
  // class
  public ILoXMLFrag updateAttribute(String inAtt, String inVal) {
    return new MtLoXMLFrag();
  }
  
  // returns true if both lists are empty
  @Override
  public boolean sameXMLDoc(ILoXMLFrag other) {
    return this.equals(other);
  }
  
  // always false because this is an empty list
  @Override
  public boolean compareFirst(IXMLFrag frag) {
    return false;
  }
  
  // always false because this is an empty list
  @Override
  public boolean compareRest(ILoXMLFrag frags) {
    return false;
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
   * Template: FIELDS: this.first -- IXMLFrag this.rest -- ILoXMLFrag
   * 
   * METHODS: * this.contentLenth() -- int this.hasTag(String) -- boolean
   * this.hasAttribute(String) -- boolean this.hasAttributeInTag(String, String)
   * -- boolean this.renderAsString() -- String this.undateAttribute(String,
   * String) -- ILoXMLFrag
   * 
   * METHODS OF FIELDS: this.frist.getContentLength() -- int
   * this.first.getHasTag() -- Tag this.first.getHasAttribute(String) -- boolean
   * this.first.getContent() -- ILoXMLFrag this.first.asString -- String
   * this.first.updateAtt(String, String) this.rest.contentLenth() -- int
   * this.rest.hasTag(String) -- boolean this.rest.hasAttribute(String) -- boolean
   * this.rest.hasAttributeInTag(String, String) -- boolean
   * this.rest.renderAsString() -- String this.rest.undateAttribute(String,
   * String) -- ILoXMLFrag
   */

  // Gets content length of
  public int contentLength() {
    return first.getContentLength() + rest.contentLength();
  }

  // Returns true if input name equals name of tag in ILoXMLFrag
  public boolean hasTag(String inName) {
    if (inName.equals(this.first.getHasTag().name)) {
      return true;
    }
    if (this.first.getContent().hasTag(inName)) {
      return true;
    }
    else {
      return this.rest.hasTag(inName);
    }
  }

  // Returns true if an input name equals name of an attribute in ILoXMLFrag
  public boolean hasAttribute(String inAttribute) {
    if (this.first.getHasAttribute(inAttribute)) {
      return true;
    }
    if (this.first.getContent().hasAttribute(inAttribute)) {
      return true;
    }
    else {
      return this.rest.hasAttribute(inAttribute);
    }
  }

  // Returns true if an a given tag has a given attribute
  public boolean hasAttributeInTag(String inTag, String inAttribute) {
    if (inTag.equals(this.first.getHasTag().name)) {
      return this.first.getHasTag().atts.getHasAttributeName(inAttribute);
    }

    else {
      return this.rest.hasAttributeInTag(inTag, inAttribute);
    }
  }

  // Converts ILoXMLFrags into String without Attributes or Tags
  public String renderAsString() {
    return first.asString() + rest.renderAsString();
  }

  // Returns ILoFrag with updated Attributes
  public ILoXMLFrag updateAttribute(String inAtt, String inVal) {
    return new ConsLoXMLFrag(this.first.updateAtt(inAtt, inVal),
        this.rest.updateAttribute(inAtt, inVal));
  }

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
   * Template: FIELDS: this.name -- String this.atts -- ILoAtt
   * 
   * METHODS: this.getHasTagName() -- String this.getHasAttributeList() -- boolean
   * this.newTag --() Tag
   * 
   * METHODS ON FIELDS: this.atts.getHasAttributeName(String) -- boolean
   * this.atts.newAtts(String, String) -- Att
   */
  // returns name of tag
  public String getHasTagName() {
    return this.name;
  }

  // returns true if attribute list contains the given name
  public boolean getHasAttributeList(String inAttribute) {
    return this.atts.getHasAttributeName(inAttribute);
  }

  // returns new Atts list
  public Tag newTag(String inAtts, String inVal) {
    return new Tag(this.name, this.atts.newAtts(inAtts, inVal));
  }

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
   * Template: FIELDS: this.name -- String this.value -- String
   * 
   * METHODS: this.attName() -- String this.newAttValue() -- Att
   */
  // returns name
  public String attName() {
    return this.name;
  }

  // returns new att with same name and new value
  public Att newAttValue(String inVal) {
    return new Att(this.name, inVal);
  }

  // checks to see if the names and values are equal
  public boolean sameAttribute(Att a) {
    return this.name.equals(a.name) && this.value == a.value;
  }
}

/*
 * A list of Att is one of empty (cons Att list-of-Att)
 */
interface ILoAtt {

  // Checks if list of Atts contains given att
  public boolean getHasAttributeName(String inAttibute);

  // Returns new list of Atts but with altered values
  public ILoAtt newAtts(String inAtt, String inVal);

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
   * Template: FIELDS: this.first -- Att this.rest -- ILoAtt
   * 
   * METHODS: this.getHasAttributeBane(String) -- boolean this.newAtts(String,
   * String) -- ILoAtt
   * 
   * METHODS ON FIELDS: this.rest.getHasAttributeBane(String) -- boolean
   * this.rest.newAtts(String, String) -- ILoAtt this.first.attName() -- String
   * this.first.newAttValue() -- Att
   */

  // Checks if list of Atts contains given att
  public boolean getHasAttributeName(String inAttribute) {
    if (inAttribute.equals(this.first.attName())) {
      return true;
    }
    else {
      return this.rest.getHasAttributeName(inAttribute);
    }
  }

  // Changes Att value to inVal if Att name = inAtt
  public ILoAtt newAtts(String inAtt, String inVal) {
    if (inAtt.equals(this.first.attName())) {
      return new ConsLoAtt(this.first.newAttValue(inVal), this.rest.newAtts(inAtt, inVal));
    }
    else {
      return new ConsLoAtt(this.first, this.rest);
    }
  }

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
   * Template: METHODS: this.getHasAttributeBane(String) -- boolean
   * this.newAtts(String, String) -- ILoAtt
   */
  // returns false because no atts in list
  public boolean getHasAttributeName(String inAttibute) {
    return false;
  }

  // Returns an empty list
  public ILoAtt newAtts(String inAtt, String inVal) {
    return new MtLoAtt();
  }

  // returns false because the lists must not be equal
  @Override
  public boolean sameAtts(ILoAtt other) {
    return this.equals(other);
  }

  // returns false because the lists must not be equal
  @Override
  public boolean compareFirst(Att a) {
    return false;
  }

  // returns false because the lists must not be equal
  @Override
  public boolean compareRest(ILoAtt atts) {
    // TODO Auto-generated method stub
    return false;
  }
}

// Examples of XML
class ExamplesXML {

  // Example of plain text
  Plaintext pt = new Plaintext("I am XML!");

  // Example of attributes
  Att AttVolume = new Att("volume", "30db");
  Att AttDuration = new Att("duration", "5sec");
  Att AttSize = new Att("size", "2inches");
  Att AttVolume2 = new Att("volume", "50db");
  Att AttDuration2 = new Att("duration", "10sec");
  Att AttSize2 = new Att("size", "10inches");

  // ILoAtt
  ILoAtt loatt1 = new ConsLoAtt(AttVolume, new ConsLoAtt(AttDuration, new MtLoAtt()));
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
          new Tagged(new Tag("yell", new ConsLoAtt(AttVolume, new MtLoAtt())),
              new ConsLoXMLFrag(
                  new Tagged(new Tag("italic", new MtLoAtt()),
                      new ConsLoXMLFrag(new Plaintext("X"), new MtLoXMLFrag())),
                  new ConsLoXMLFrag(new Plaintext("ML"), new MtLoXMLFrag()))),
          new ConsLoXMLFrag(new Plaintext("!"), new MtLoXMLFrag())));

  // Example 5
  ILoXMLFrag xml5 = new ConsLoXMLFrag(new Plaintext("I am "),
      new ConsLoXMLFrag(
          new Tagged(
              new Tag("yell", new ConsLoAtt(AttVolume, new ConsLoAtt(AttDuration, new MtLoAtt()))),
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
      new Tag("yell", new ConsLoAtt(AttVolume, new ConsLoAtt(AttDuration, new MtLoAtt()))),
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
      new Tagged(new Tag("big", new ConsLoAtt(AttSize, new MtLoAtt())), ILo2),
      new ConsLoXMLFrag(
          new Tagged(new Tag("that", new ConsLoAtt(AttDuration, new MtLoAtt())), new MtLoXMLFrag()),
          new MtLoXMLFrag()));

  // Example of an empty ILoXMLFrag
  ILoXMLFrag emptyXML = new MtLoXMLFrag();

  // Example of an ILoXMLFrag
  ILoXMLFrag ILo5 = new ConsLoXMLFrag(
      new Tagged(new Tag("Yell", new ConsLoAtt(AttVolume, new MtLoAtt())), new MtLoXMLFrag()),
      new MtLoXMLFrag());

  // Example of an ILoXMLFrag
  ILoXMLFrag ILo6 = new ConsLoXMLFrag(new Plaintext("1234"),
      new ConsLoXMLFrag(xmlItalic, new ConsLoXMLFrag(new Plaintext("678"), new MtLoXMLFrag())));

  // Example of an XMLFrag
  IXMLFrag xmlEx1 = new Tagged(new Tag("big", new ConsLoAtt(AttSize, new MtLoAtt())), ILo2);

  // Examples of tags
  Tag tagEx1 = new Tag("Yell", new ConsLoAtt(AttVolume, new MtLoAtt()));
  Tag tagEx2 = new Tag("the", new ConsLoAtt(AttDuration, new MtLoAtt()));
  Tag tagEx3 = new Tag("it", new ConsLoAtt(AttVolume2, new MtLoAtt()));
  Tag tagEx4 = new Tag("Yell", new ConsLoAtt(AttVolume2, new MtLoAtt()));
  Tag tagEx5 = new Tag("the", new ConsLoAtt(AttDuration2, new MtLoAtt()));

  boolean testContentLength(Tester t) {
    // contentLength Tests
    return t.checkExpect(this.ILo1.contentLength(), 3)
        && t.checkExpect(this.emptyXML.contentLength(), 0)
        && t.checkExpect(this.ILo2.contentLength(), 4)
        && t.checkExpect(this.ILo3.contentLength(), 4)
        && t.checkExpect(this.ILo6.contentLength(), 8);
  }

  boolean testHasTag(Tester t) {
    // hasTag Tests
    return t.checkExpect(this.emptyXML.hasTag("Volume"), false)
        && t.checkExpect(this.ILo1.hasTag("Volume"), false)
        && t.checkExpect(this.ILo2.hasTag("yell"), true)
        && t.checkExpect(this.ILo2.hasTag("italic"), true)
        && t.checkExpect(this.ILo2.hasTag("the"), false)
        && t.checkExpect(this.ILo4.hasTag("yell"), true);
  }

  boolean testHasAttribute(Tester t) {
    // hasAttribute Tests
    return t.checkExpect(this.ILo2.hasAttribute("volume"), true)
        && t.checkExpect(this.ILo2.hasAttribute("duration"), true)
        && t.checkExpect(this.ILo3.hasAttribute("volume"), true)
        && t.checkExpect(this.ILo3.hasAttribute("duration"), true)
        && t.checkExpect(this.ILo2.hasAttribute("the"), false)
        && t.checkExpect(this.ILo3.hasAttribute("cat"), false)
        && t.checkExpect(this.ILo3.hasAttribute("volume"), true)
        && t.checkExpect(this.emptyXML.hasAttribute("volume"), false);
  }

  boolean testhasAttributeInTag(Tester t) {
    // hasAtrributeinTag Tests
    return t.checkExpect(this.ILo4.hasAttributeInTag("big", "volume"), false)
        && t.checkExpect(this.ILo4.hasAttributeInTag("big", "size"), true)
        && t.checkExpect(this.ILo4.hasAttributeInTag("big", "duration"), false)
        && t.checkExpect(this.ILo4.hasAttributeInTag("that", "duration"), true)
        && t.checkExpect(this.ILo1.hasAttributeInTag("italic", "duration"), false)
        && t.checkExpect(this.emptyXML.hasAttributeInTag("italic", "duration"), false);
  }

  boolean testRenderAsString(Tester t) {
    // renderAsString Tests
    return t.checkExpect(this.ILo1.renderAsString(), "MLX")
        && t.checkExpect(this.xml1.renderAsString(), "I am XML!")
        && t.checkExpect(this.xml2.renderAsString(), "I am XML!")
        && t.checkExpect(this.xml3.renderAsString(), "I am XML!")
        && t.checkExpect(this.xml4.renderAsString(), "I am XML!")
        && t.checkExpect(this.xml5.renderAsString(), "I am XML!")
        && t.checkExpect(this.emptyXML.renderAsString(), "");
  }

  boolean testUpdateAttribute(Tester t) {
    // updateAttribute tests
    return t.checkExpect(this.ILo5.updateAttribute("volume", "50db"),
        new ConsLoXMLFrag(new Tagged(new Tag("Yell", new ConsLoAtt(AttVolume2, new MtLoAtt())),
            new MtLoXMLFrag()), new MtLoXMLFrag()))
        && t.checkExpect(this.ILo4.updateAttribute("duration", "10sec"),
            new ConsLoXMLFrag(
                new Tagged(new Tag("big", new ConsLoAtt(AttSize, new MtLoAtt())), ILo2),
                new ConsLoXMLFrag(
                    new Tagged(new Tag("that", new ConsLoAtt(AttDuration2, new MtLoAtt())),
                        new MtLoXMLFrag()),
                    new MtLoXMLFrag())))
        && t.checkExpect(this.ILo5.updateAttribute("volume2", "50db"), ILo5)
        && t.checkExpect(this.emptyXML.updateAttribute("volume2", "50db"), emptyXML);
  }

  boolean testGetContentLength(Tester t) {
    // getContentsLength tests
    return t.checkExpect(this.pt.getContentLength(), 9)
        && t.checkExpect(this.xmlItalic.getContentLength(), 1);
  }

  boolean testGetHasTag(Tester t) {
    // getTag tests
    return t.checkExpect(this.pt.getHasTag(), new Tag("", new MtLoAtt()))
        && t.checkExpect(this.xmlItalic.getHasTag(), new Tag("italic", new MtLoAtt()))
        && t.checkExpect(this.xmlEx1.getHasTag(),
            new Tag("big", new ConsLoAtt(AttSize, new MtLoAtt())));
  }

  boolean testGetHasAttribute(Tester t) {
    // getHasAttribute(String inAttribute) tests
    return t.checkExpect(this.pt.getHasAttribute("the"), false)
        && t.checkExpect(this.xmlItalic.getHasAttribute("the"), false)
        && t.checkExpect(this.xmlEx1.getHasAttribute("size"), true);
  }

  boolean testGetContent(Tester t) {
    // getContent() tests
    return t.checkExpect(this.pt.getContent(), new MtLoXMLFrag())
        && t.checkExpect(this.xmlItalic.getContent(),
            new ConsLoXMLFrag(new Plaintext("X"), new MtLoXMLFrag()))
        && t.checkExpect(this.xmlEx1.getContent(), ILo2);
  }

  boolean testAsString(Tester t) {
    // asString() tests
    return t.checkExpect(this.pt.asString(), "I am XML!")
        && t.checkExpect(this.xmlItalic.asString(), "X")
        && t.checkExpect(this.xmlEx1.asString(), "MLXX");
  }

  boolean testGetHasTagName(Tester t) {
    // getHasTagName tests
    return t.checkExpect(this.tagEx1.getHasTagName(), "Yell")
        && t.checkExpect(this.tagEx2.getHasTagName(), "the")
        && t.checkExpect(this.tagEx3.getHasTagName(), "it");
  }

  boolean testGetHasAttributeList(Tester t) {
    // getHasAttributeList(String inAttribute)
    return t.checkExpect(this.tagEx1.getHasAttributeList("volume"), true)
        && t.checkExpect(this.tagEx2.getHasAttributeList("volume"), false)
        && t.checkExpect(this.tagEx3.getHasAttributeList("it"), false);
  }

  boolean testNewTag(Tester t) {
    // newTag(String inAtts, String inVal) tests
    return t.checkExpect(this.tagEx1.newTag("volume", "50db"), tagEx4)
        && t.checkExpect(this.tagEx2.newTag("duration", "10sec"), tagEx5);
  }

  boolean testAttName(Tester t) {
    // attName tests
    return t.checkExpect(this.AttVolume.attName(), "volume");
  }

  boolean testNewAttValue(Tester t) {
    // newAttValue tests
    return t.checkExpect(this.AttVolume.newAttValue("50db"), AttVolume2)
        && t.checkExpect(this.AttVolume.newAttValue("50db"), AttVolume2);
  }

  boolean testGetHasAttributeName(Tester t) {
    // getHasAttributeName(String inAttibute) tests
    return t.checkExpect(this.loatt1.getHasAttributeName("50db"), false)
        && t.checkExpect(this.loatt1.getHasAttributeName("volume"), true)
        && t.checkExpect(this.loattEm.getHasAttributeName("volume"), false);
  }

  ;
}