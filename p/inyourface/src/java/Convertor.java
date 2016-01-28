/// SYNACKTIV: convertor class
package com.synacktiv;

import java.lang.*;
import java.net.*;
import java.io.*;

public class Convertor {
        public static Object convert(String object, String type) throws Exception {
                Object convertedObject = null;
    
                if(type.equals(org.unsynchronized.stringobj.class.getName())) {
                        convertedObject = createStringFromString(object);
                } else if(type.equals("java.lang.Integer")) {
                        convertedObject = createIntegerFromString(object);
                } else if(type.equals("java.lang.Long")) {
                        convertedObject = createLongFromString(object);
                } else if(type.equals("java.lang.Boolean")) {
                        convertedObject = createBooleanFromString(object); 
                } else if(type.equals("java.lang.Character")) {
                        convertedObject = createCharacterFromString(object); 
                } else if(type.equals("java.lang.Byte")) {
                        convertedObject = createByteFromString(object); 
                } else if(type.equals("java.lang.Double")) {
                        convertedObject = createDoubleFromString(object); 
                } else if(type.equals("java.lang.Short")) {
                        convertedObject = createShortFromString(object); 
                } else if(type.equals("java.lang.Float")) {
                        convertedObject = createFloatFromString(object); 
                } else {
			throw new Exception("type: " + type + " not implemented yet.");
		} 
    
                return convertedObject;
        }   

        public static Object[] convert(String[] objects, String[] types) throws Exception {
                Object[] convertedObjects = new Object[objects.length];

                for (int i = 0; i<objects.length; i++) {
                        convertedObjects[i] = convert(objects[i], types[i]);
                }

                return convertedObjects;
        }


        private static Object createStringFromString(String string) {
                return (Object) (new String(string));
        }

        private static Object createIntegerFromString(String string) throws NumberFormatException {
                return (Object) (new Integer(string));
        }

        private static Object createLongFromString(String string) throws NumberFormatException {
                return (Object) (new Long(string));
        }

        private static Object createBooleanFromString(String string) {
                return (Object) (new Boolean(string));
        }

        private static Object createCharacterFromString(String string) {
                return (Object) (new Character(string.charAt(0)));
        }

        private static Object createByteFromString(String string) {
                return (Object) (new Byte(string));
        }

        private static Object createDoubleFromString(String string) {
                return (Object) (new Double(string));
        }

        private static Object createShortFromString(String string) {
                return (Object) (new Short(string));
        }

        private static Object createFloatFromString(String string) {
                return (Object) (new Float(string));
        }
}
