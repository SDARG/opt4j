module org.opt4j.core {
    exports org.opt4j.core;
    exports org.opt4j.core.start;
    exports org.opt4j.core.config;
    exports org.opt4j.core.config.visualization;
    exports org.opt4j.core.optimizer;
    exports org.opt4j.core.config.annotations;
    exports org.opt4j.core.common.archive;
    exports org.opt4j.core.common.logger;
    exports org.opt4j.core.common.random;
    exports org.opt4j.core.problem;
    exports org.opt4j.core.genotype;
    
    requires com.google.guice;
    requires java.desktop;
}
