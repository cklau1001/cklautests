package io.cklau1001.circuitbreaker1.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
  Under field injection, the variable cannot be made final, which is not immutable.
  A better approach is to use constructor injection

  easier to mock, no need to use ReflectionUtils to mock private property
 */
@Configuration
public class MainConfig {


}
