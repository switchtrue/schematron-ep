Introduction - UNDER CONSTRUCTION
=================================

What is Schematron?
-------------------

I assume that, if you are looking at this, you have at least a vague understanding of what XML and its relelated technologies are. If not, a great place to start (or to refresh) would be w3schools which covers all of the technologies here with the exception of Schematron itself.
If you are in the business of processing XML, whether it be a web service, configuration files, etc., you will undoubtedly, at some point, want to validate that the XML is "valid". But what does "valid" mean?
XML in its basic form must abide by various basic rules in order to be valid. For example, the document have a single root element, child elements must have an end element to match the start element (or use the abbreviated <xxx> syntax), elements can be nested but not cross over (i.e. all child elements must have had their end element before their parents end element, attribute values must be quoted, etc. etc.
XML can then be validated against a specification that verifies that the structure of the document is correct, for example XML Schema, DTD, etc. These specifications define what the document should look like, e.g. what elements are valid child elements, what are the valid values that a text node or attribute can have.

Why has this been developed?
----------------------------
*TODO*

Download and Installation
=========================

Prerequisites
-------------
The plugin requires JRE 1.4+, <a href="http://www.eclipse.org/downloads/">32-bit Eclipse 3.2+</a> and <a href="http://download.eclipse.org/webtools/downloads/">Eclipse Web Tools Platform v 1.5.4</a>

Update Site Details
-------------------
The update site for the Schematron Plugin is <a href="http://www.castledesigns.co.uk/schematron-ep">http://www.castledesigns.co.uk/schematron-ep</a>

Installation Instructions
-------------------------
For instructions on how to install the plugin via the Eclipse Update Manager, please follow the instructions on the Installing new features with the update manager pages from eclipse.org.
To enable the plugin, right mouse button on the project that you want to enable Schematron validation on and select "Add/Remove Schematron Validation". I am sure that this mechanism will improve in the future but, for now, this is the "one-time" step you take on each project that you wish to use the plugin on.


Examples
========

Examples
--------
Here is the XML schema for the purchase order:

    <?xml version="1.0" encoding="UTF-8"?>
    <schema targetNamespace="http://schematron-ep.sf.net/example/po" elementFormDefault="qualified" xmlns="http://www.w3.org/2001/XMLSchema" xmlns:po="http://schematron-ep.sf.net/example/po">
      <complexType name="lineType">
        <attribute name="productId" type="string" use="required"/>
        <attribute name="qty" type="int" use="required"/>
        <attribute name="cost" type="float" use="required"/>
      </complexType>
      <simpleType name="totalType"></simpleType>
      <complexType name="poType">
        <sequence>
          <element name="line" type="po:lineType" maxOccurs="unbounded" minOccurs="1"/>
          <element name="total" type="po:totalType"/>
        </sequence>
      </complexType>
      <element name="po" type="po:poType"/>
    </schema>

Here is the purchase order po.xml. Note how it refers to the po.sch file for validation:

    <?xml version="1.0" encoding="UTF-8"?>
    <?schematron-schema href="po.sch"?>
    <po:po xmlns:po="http://schematron-ep.sf.net/example/po" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://schematron-ep.sf.net/example/po ../xsd/po.xsd ">
      <po:line productId="apple" qty="2" cost="0.68"/> 
      <po:line productId="apple" qty="3" cost="1.02"/>
      <po:total>1.70</po:total>
    </po:po>

Here is the catalog.xml:

    <?xml version="1.0" encoding="UTF-8"?>
    <products>
      <product id="milk" price="0.89"/>
      <product id="rice" price="1.23"/>
      <product id="apple" price="0.34"/>
    </products>

Here is the Schematron schema po.sch:
    
    <?xml version="1.0" encoding="UTF-8"?>
    <sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt">
      <sch:title>Example Purchase Order Validation Schema</sch:title>
      <sch:p>
        This schema provides some example assertions and reports to exercise and demonstrate the Schematron Eclipse Plugin.
      </sch:p>
      <sch:ns prefix="po" uri="http://schematron-ep.sf.net/example/po" />
      <sch:ns prefix="xs" uri="http://www.w3.org/2001/XMLSchema" /> 
      <sch:pattern id="Check Total">
        <sch:title>Checks the total is correct</sch:title>
        <sch:rule context="po:total">
          <sch:assert test="sum(current()/preceding-sibling::po:line/xs:decimal(@cost)) = current()/text()">
            Invalid total
          </sch:assert>
          <sch:report test="number(current()/text()) > 10.00">
            Give the customer a free jar of honey for spending over 10.00
          </sch:report>
        </sch:rule>
      </sch:pattern>
      <sch:pattern id="Check Lines">
        <sch:title>Checks the lines are correct</sch:title>
        <sch:rule context="po:line">
        <!-- Check that the qty * unit price = cost -->
          <sch:assert test="(current()/@qty * document('catalog.xml')/products/product[@id=current()/@productId]/@price) = current()/@cost">
            Cost is incorrect
          </sch:assert>
        </sch:rule>
      </sch:pattern>
    </sch:schema>