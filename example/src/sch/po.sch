<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron" 
	queryBinding="xslt">
	<sch:title>Example Purchase Order Validation Schema</sch:title>
	<sch:p>
		This schema provides some example assertions and reports to
		exercise and demonstrate the Schematron Eclipse Plugin.
	</sch:p>
	<sch:ns prefix="po" uri="http://schematron-ep.sf.net/example/po" />
	<sch:ns prefix="xs" uri="http://www.w3.org/2001/XMLSchema" /> 

	<sch:pattern id="Check Total">
		<sch:title>Checks the total is correct</sch:title>
		<sch:rule context="po:total">
			<sch:assert
				test="sum(current()/preceding-sibling::po:line/xs:decimal(@cost)) = current()/text()">
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
			<sch:assert
				test="(current()/@qty * document('products.xml')/products/product[@id=current()/@productId]/@price) = current()/@cost">
				Cost is incorrect
			</sch:assert>
		</sch:rule>
	</sch:pattern>
</sch:schema>