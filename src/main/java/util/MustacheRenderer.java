/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import lombok.NonNull;
import java.io.IOException;
import java.io.StringWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Iuri
 */
public class MustacheRenderer {
    


    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(MustacheRenderer.class);

    //sets the domain object
    private static final String TEMPLATE_ROOT = "templates";

    private MustacheFactory mustacheFactory;

    public MustacheRenderer() {
        this(TEMPLATE_ROOT);
    }

    public MustacheRenderer(String templateRoot) {

        mustacheFactory = new DefaultMustacheFactory(templateRoot);
    }



    
        public String render(@NonNull String templateName, @NonNull Object model) {
        Mustache mustache = mustacheFactory.compile(templateName);

        try (StringWriter stringWriter = new StringWriter()) {
            mustache.execute(stringWriter, model).close();
            return stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        }
        
}
    

