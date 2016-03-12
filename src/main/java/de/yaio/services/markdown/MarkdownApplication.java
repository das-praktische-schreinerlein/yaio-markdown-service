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
package de.yaio.services.markdown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/** 
 * the markdown-service as spring boot application
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("de.yaio.services.markdown")
public class MarkdownApplication {

    /** 
     * Main-method to start the application
     * @param args                   the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MarkdownApplication.class, args);
    }
}