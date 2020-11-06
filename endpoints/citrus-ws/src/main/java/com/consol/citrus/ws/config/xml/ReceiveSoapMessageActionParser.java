/*
 * Copyright 2006-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.consol.citrus.ws.config.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.consol.citrus.config.util.BeanDefinitionParserUtils;
import com.consol.citrus.config.xml.AbstractReceiveMessageActionFactoryBean;
import com.consol.citrus.config.xml.ReceiveMessageActionParser;
import com.consol.citrus.message.builder.DefaultHeaderBuilder;
import com.consol.citrus.validation.builder.DefaultMessageContentBuilder;
import com.consol.citrus.validation.context.ValidationContext;
import com.consol.citrus.ws.actions.ReceiveSoapMessageAction;
import com.consol.citrus.ws.message.SoapAttachment;
import com.consol.citrus.ws.message.SoapMessageHeaders;
import com.consol.citrus.ws.validation.SoapAttachmentValidator;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

/**
 * Parser for SOAP message receiver component in Citrus ws namespace.
 *
 * @author Christoph Deppisch
 */
public class ReceiveSoapMessageActionParser extends ReceiveMessageActionParser {

    @Override
    protected BeanDefinitionBuilder parseComponent(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ReceiveSoapMessageActionFactoryBean.class);

        List<Element> attachmentElements = DomUtils.getChildElementsByTagName(element, "attachment");
        List<SoapAttachment> attachments = new ArrayList<SoapAttachment>();
        for (Element attachment : attachmentElements) {
            attachments.add(SoapAttachmentParser.parseAttachment(attachment));
        }

        builder.addPropertyValue("attachments", attachments);

        if (!attachments.isEmpty()) {
            BeanDefinitionParserUtils.setPropertyReference(builder, attachmentElements.get(0).getAttribute("validator"),
                    "attachmentValidator", "soapAttachmentValidator");
        }

        return builder;
    }

    @Override
    protected void parseHeaderElements(Element actionElement, DefaultMessageContentBuilder messageBuilder, List<ValidationContext> validationContexts) {
        super.parseHeaderElements(actionElement, messageBuilder, validationContexts);

        Map<String, Object> headers = new HashMap<>();
        if (actionElement.hasAttribute("soap-action")) {
            headers.put(SoapMessageHeaders.SOAP_ACTION, actionElement.getAttribute("soap-action"));
        }

        if (actionElement.hasAttribute("content-type")) {
            headers.put(SoapMessageHeaders.HTTP_CONTENT_TYPE, actionElement.getAttribute("content-type"));
        }

        if (actionElement.hasAttribute("accept")) {
            headers.put(SoapMessageHeaders.HTTP_ACCEPT, actionElement.getAttribute("accept"));
        }

        if (!headers.isEmpty()) {
            messageBuilder.addHeaderBuilder(new DefaultHeaderBuilder(headers));
        }
    }

    /**
     * Test action factory bean.
     */
    public static class ReceiveSoapMessageActionFactoryBean extends AbstractReceiveMessageActionFactoryBean<ReceiveSoapMessageAction, ReceiveSoapMessageAction.Builder> {

        private final ReceiveSoapMessageAction.Builder builder = new ReceiveSoapMessageAction.Builder();

        /**
         * Sets the control attachments.
         * @param attachments the control attachments
         */
        public void setAttachments(List<SoapAttachment> attachments) {
            attachments.forEach(builder::attachment);
        }

        /**
         * Set the attachment validator.
         * @param attachmentValidator the attachmentValidator to set
         */
        public void setAttachmentValidator(SoapAttachmentValidator attachmentValidator) {
            builder.attachmentValidator(attachmentValidator);
        }

        @Override
        public ReceiveSoapMessageAction getObject() throws Exception {
            return builder.build();
        }

        @Override
        public Class<?> getObjectType() {
            return ReceiveSoapMessageAction.class;
        }

        /**
         * Obtains the builder.
         * @return the builder implementation.
         */
        @Override
        public ReceiveSoapMessageAction.Builder getBuilder() {
            return builder;
        }
    }
}
