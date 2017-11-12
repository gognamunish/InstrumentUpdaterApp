package com.gognamunish.referencedata.at;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/referencedata.feature"
        ,glue={"com.gognamunish.referencedata.at.steps"}
)
public class CucumberAT {

}
