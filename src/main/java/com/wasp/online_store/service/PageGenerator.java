package com.wasp.online_store.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;


public class PageGenerator {
    private static final String RESOURCES_PATH = "src/main/resources";
    private static final Configuration cfg = new Configuration();

    public static String getPage(String filename, Map<String, Object> data) {
        Writer writer = new StringWriter();
        try {
            Template template = cfg.getTemplate(RESOURCES_PATH + File.separator + filename);
            template.process(data, writer);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return writer.toString();
    }
}