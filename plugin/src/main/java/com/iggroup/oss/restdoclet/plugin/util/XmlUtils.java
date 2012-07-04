/*
 * #%L
 * restdoc-plugin
 * %%
 * Copyright (C) 2012 IG Group
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.iggroup.oss.restdoclet.plugin.util;

import static org.apache.commons.lang.StringUtils.trimToNull;
import static org.w3c.dom.Node.ELEMENT_NODE;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Contains utility-methods for reading XML.
 */
public final class XmlUtils {

   /**
    * No-argument constructor to "silence" PMD.
    */
   private XmlUtils() {
      super();
   }

   /**
    * Returns an attribute of a XML node. Leading and trailing whitespaces are
    * removed in the attribute.
    * 
    * @param node the XML node.
    * @param name the name of the attribute.
    * @return the attribute or <code>null</code> if the attribute is not
    *         present.
    */
   public static String attribute(final Node node, final String name) {
      String value;
      if (node.getAttributes() == null) {
         value = null;
      } else {
         final Node attribute = node.getAttributes().getNamedItem(name);
         if (attribute == null) {
            value = null;
         } else {
            value = trimToNull(attribute.getNodeValue());
         }
      }
      return value;
   }

   /**
    * Returns the <i>first</i> child-element of a XML node with a particular
    * name.
    * 
    * @param node the XML node.
    * @param name the name of the child-element.
    * @return the child-element or <code>null</code> if no child with the name
    *         was found.
    */
   public static Element child(final Node node, final String name) {
      Element element = null;

      for (int i = 0; i < node.getChildNodes().getLength(); i++) {
         if (node.getChildNodes().item(i).getNodeType() == ELEMENT_NODE) {
            final Element child = (Element) node.getChildNodes().item(i);

            if (StringUtils.equals(name, child.getNodeName())) {
               element = child;
               break;
            }
         }
      }
      return element;
   }

   /**
    * Returns the child-elements of a XML node with a particular name.
    * 
    * @param node the XML node.
    * @param name the name of the child-elements.
    * @return the collection of child-elements or an empty collection if no
    *         children with the name were found.
    */
   public static Collection<Element> children(final Node node,
                                              final String name) {
      final Collection<Element> elements = new ArrayList<Element>();

      for (int i = 0; i < node.getChildNodes().getLength(); i++) {
         if (node.getChildNodes().item(i).getNodeType() == ELEMENT_NODE) {
            final Element child = (Element) node.getChildNodes().item(i);

            if (StringUtils.equals(name, child.getNodeName())) {
               elements.add(child);
            }
         }
      }
      return elements;
   }

   /**
    * Returns the test-content of a XML node. Leading and trailing whitespaces
    * are removed in the text.
    * 
    * @param node the XML node.
    * @return the text-content or <code>null</code> if the node does not
    *         contain any text.
    */
   public static String textContext(final Node node) {
      String result;
      if (node.getFirstChild() == null) {
         result = null;
      } else {
         result = trimToNull(node.getFirstChild().getNodeValue());
      }
      return result;
   }

}
