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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


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
        try {
            response.getWriter().append(converterUtils.convertMarkdownToHtml(src));
        } catch (Exception e) {
            LOGGER.warn("exception start for src:" + src, e);
            response.setStatus(404);
            response.getWriter().append("error while reading:" + e.getMessage());
        }
    }
}