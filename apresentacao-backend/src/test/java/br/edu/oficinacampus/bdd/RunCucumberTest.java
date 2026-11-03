package br.edu.oficinacampus.bdd;

import org.junit.platform.suite.api.*;
import static io.cucumber.junit.platform.engine.Constants.*;
@Suite @IncludeEngines("cucumber") @SelectClasspathResource("features")
@ConfigurationParameter(key=GLUE_PROPERTY_NAME,value="br.edu.oficinacampus.bdd")
@ConfigurationParameter(key=PLUGIN_PROPERTY_NAME,value="pretty,html:target/cucumber.html,json:target/cucumber.json")
public class RunCucumberTest { }
