/**
 * markdown-converter
 *
 * @FeatureDomain                Converter
 * @author                       Michael Schreiner <michael.schreiner@your-it-fellow.de>
 * @category                     markdown-services
 * @copyright                    Copyright (c) 2014, Michael Schreiner
 * @license                      http://mozilla.org/MPL/2.0/ Mozilla Public License 2.0
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.yaio.services.markdown.server.controller;

import de.yaio.services.markdown.server.converter.MarkdownProvider;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;


/** 
 * controller with Download-Services to convert markdown-src to html
 */
@Controller
@RequestMapping("${yaio-markdown-service.baseurl}")
public class MarkdownController {

    private static final Logger LOGGER = Logger.getLogger(MarkdownController.class);

    @Autowired
    protected MarkdownProvider converterUtils;

    /** 
     * Request to generate html from markdown
     * @param src                    markdown-src
     * @param response               the response-Obj to set contenttype and headers
     * @throws IOException           possible
     */
    @RequestMapping(method = RequestMethod.POST,
                    value = "/markdown2html",
                    produces = "text/html")
    public @ResponseBody void convertToHtml(@RequestParam("src") final String src,
                                           HttpServletResponse response) throws IOException {
        response.getWriter().append(converterUtils.convertMarkdownToHtml(src));
    }

    @ExceptionHandler(value = {IOException.class})
    public void handleCustomException(final HttpServletRequest request, final Exception e,
                                   final HttpServletResponse response) {
        LOGGER.info("IOException while running request:" + createRequestLogMessage(request), e);
        response.setStatus(SC_BAD_REQUEST);
        try {
            response.getWriter().append("exception while converting markdown");
        } catch (IOException ex) {
            LOGGER.warn("exception while exceptionhandling", ex);
        }
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public void handleAllException(final HttpServletRequest request, final Exception e,
                                   final HttpServletResponse response) {
        LOGGER.info("Exception while running request:" + createRequestLogMessage(request), e);
        response.setStatus(SC_INTERNAL_SERVER_ERROR);
        try {
            response.getWriter().append("exception while converting markdown");
        } catch (IOException ex) {
            LOGGER.warn("exception while exceptionhandling", ex);
        }
    }

    protected String createRequestLogMessage(HttpServletRequest request) {
        return new StringBuilder("REST Request - ")
                .append("[HTTP METHOD:")
                .append(request.getMethod())
                .append("] [URL:")
                .append(request.getRequestURL())
                .append("] [REQUEST PARAMETERS:")
                .append(getRequestMap(request))
                .append("] [REMOTE ADDRESS:")
                .append(request.getRemoteAddr())
                .append("]").toString();
    }

    private Map<String, String> getRequestMap(HttpServletRequest request) {
        Map<String, String> typesafeRequestMap = new HashMap<>();
        Enumeration<?> requestParamNames = request.getParameterNames();
        while (requestParamNames.hasMoreElements()) {
            String requestParamName = (String)requestParamNames.nextElement();
            String requestParamValue = request.getParameter(requestParamName);
            typesafeRequestMap.put(requestParamName, requestParamValue);
        }
        return typesafeRequestMap;
    }
}