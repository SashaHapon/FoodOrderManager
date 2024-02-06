package org.food.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MvcWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected String[] getServletMappings() {

        return new String[] {"/"};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {

        return new Class[] {PersistenceContext.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {WebBeansConfig.class};
    }
}
