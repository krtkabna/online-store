package com.wasp.onlinestore.web.util;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;


public class PageGenerator {
    private static final String RESOURCES_PATH = PageGenerator.class.getResource(File.separator).getPath();
    private static final Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);

    public static String getPage(String filename, Map<String, ?> data) {
        try {
            Writer writer = new StringWriter();
            cfg.setTemplateLoader(new FileTemplateLoader(new File(RESOURCES_PATH)));
            Template template = cfg.getTemplate(filename);
            template.process(data, writer);
            return writer.toString();
        } catch (IOException | TemplateException e) {
            throw new RuntimeException("Failed to generate page: " + filename, e);
        }
    }
}