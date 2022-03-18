package com.wasp.onlinestore.web.util;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;

public class PageGenerator {
    private static final String RESOURCES_PATH = PageGenerator.class.getResource(File.separator).getPath();
    private static final Configuration CONFIGURATION = new Configuration(Configuration.VERSION_2_3_30);

    public PageGenerator() {
        try {
            CONFIGURATION.setTemplateLoader(new FileTemplateLoader(new File(RESOURCES_PATH)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to set template loader for path: " + RESOURCES_PATH);
        }
    }

    public void writePage(String filename, PrintWriter writer, Map<String, ?> data) {
        try {
            Template template = CONFIGURATION.getTemplate(filename);
            template.process(data, writer);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException("Failed to write page: " + filename, e);
        }
    }

    public void writePage(String filename, PrintWriter writer) {
        writePage(filename, writer, Collections.emptyMap());
    }
}