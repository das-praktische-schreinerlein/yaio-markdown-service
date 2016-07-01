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
package de.yaio.services.markdown.converter;

import de.yaio.commons.converter.YmfMarkdownProvider;
import org.pegdown.JshConfig;
import org.springframework.stereotype.Service;

import java.io.IOException;

/** 
 * services to convert markdown to html
 */
@Service
public class MarkdownProvider {

    protected YmfMarkdownProvider provider = new YmfMarkdownProvider();

    /**
     * generate and export html from markdown
     * @param descText               the src of the markdown
     * @throws IOException           possible Exception
     */
    public String convertMarkdownToHtml(String descText) throws IOException {
        String newDescText = provider.convertMarkdownToHtml(getConfig(), descText);
        // replace yaio-links
        newDescText = newDescText.replaceAll("href=\"yaio:",
                "href=\"" + "/yaio-explorerapp/yaio-explorerapp.html#/showByAllIds/");
        return newDescText;
    }

    protected JshConfig getConfig() {
        JshConfig config = new JshConfig();
        config.setStylePrefix("jsh-");
        config.setAppBaseVarName("ymfAppBase");
        return config;
    }
}
