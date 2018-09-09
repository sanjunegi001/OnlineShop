package com.abr.aua.utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLStreamException;

import com.abr.asa.request.data.AsaRequest;

public class XMLUtils {
	public static <T> String serialize(T object) throws JAXBException {
		StringWriter stringWriter = new StringWriter();
		Marshaller marshaller;
		(marshaller = JAXBContext.newInstance(object.getClass()).createMarshaller()).setProperty("jaxb.formatted.output", Boolean.TRUE);
		marshaller.marshal(object, stringWriter);

		return stringWriter.toString();
	}

	public static <T> String serializeWithoutHeader(T object) throws JAXBException {
		StringWriter stringWriter = new StringWriter();
		Marshaller marshaller;
		(marshaller = JAXBContext.newInstance(object.getClass()).createMarshaller()).setProperty("jaxb.formatted.output", Boolean.TRUE);
		marshaller.setProperty("jaxb.fragment", Boolean.TRUE);
		marshaller.marshal(object, stringWriter);
		return stringWriter.toString();
	}

	public static <T> T deserialize(Class<T> clazz, String xml) throws JAXBException, XMLStreamException {
		return (T) JAXBContext.newInstance(clazz).createUnmarshaller().unmarshal(new StringReader(xml));
	}

	public static String objectToXML(AsaRequest asr) throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(AsaRequest.class);

		StringWriter stringWriter = new StringWriter();
		Marshaller m = context.createMarshaller();
		// for pretty-print XML in JAXB
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// Write to System.out for debugging
		m.marshal(asr, stringWriter);
		return stringWriter.toString();
		// Write to File
		// m.marshal(emp, new File(FILE_NAME));

	}

}
