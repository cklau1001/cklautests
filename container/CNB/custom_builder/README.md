# Problem statement
Cloud native buildpack (CNB) has great potential in terms of compliance and image optimization. SpringBoot has default support on that with layered jars. 
However, there is also a learning curve when dealing with some common but tricky scenario. One of them is to add extra OS utilities to an image from CNB.

Generally, there are two approaches to tackle this problem.
1. Add the required OS utilities to the final image built by CNB as mentioned by [Stackover overflow link](https://stackoverflow.com/questions/62484649/how-to-add-extra-linux-dependencies-into-a-spring-boot-buildpack-image)
2. Create a custom builder from the existing one so that the run image can be customized. Another advantage of this approach is that the irrelevant buildpacks of non related languages such as node js / go etc can be removed. In this way, the same run image can be used to build the application without the need to add the missing OS utilities whenever the application image is built. 
In fact, this approach can be further refined by leveraging custom buildpack and extension that can further streamline the process. It will be explored later.

The creation of a custom builder is not very complicated as one may initially imagine. In this sample, the original 
builder-jammy-base builder is taken as a reference and only the native java buildpacks are 
included with the custom run image, which is configured in the builder.toml.

# Pre-requisite
- a docker registry to store the custom builder and run image, e.g. a repository in docker hub
- the pack cli to create the custom builder

# Integrating into pom.xml of maven
Assuming that it is a springboot application, the following will be needed for maven to pull and push from your private repository.

Reference: [How to package an OCI image from a Springboot application](https://docs.spring.io/spring-boot/maven-plugin/build-image.html#build-image.docker-registry)
```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
    <docker>
        <builderRegistry>
            <username>....</username>
            <password>.....</password>
        </builderRegistry>
        <publishRegistry>
            <username>....</username>
            <password>...</password>
        </publishRegistry>
    </docker>
    <image>
        <publish>true</publish>  <!-- Publish image to remote registry by default -->
        <name>${IMAGE_NAME}</name>
        <!-- <builder>paketobuildpacks/builder-jammy-base</builder> -->
        <builder>${CUSTOM_BUILDER}</builder>
        <env>
            <BP_OCI_AUTHORS>tester1</BP_OCI_AUTHORS>
            <BP_OCI_SOURCE>cklau1001</BP_OCI_SOURCE>
            <BP_IMAGE_LABELS>
                io.cklau1001.app=circuitbreaker io.cklau1001.jdk=24
            </BP_IMAGE_LABELS>
            <BPE_DEFAULT_SPRING_PROFILES_ACTIVE>${testprofiles}</BPE_DEFAULT_SPRING_PROFILES_ACTIVE>
            <BP_JVM_VERSION>24</BP_JVM_VERSION>
            <BPE_LANG>en_US.UTF-8</BPE_LANG>
        </env>
    </image>
    </configuration>
</plugin>

```


```shell
# To create a custom builder
pack builder create ${CUSTOM_BUILDER} --config ./builder.toml --target "linux/amd64" --publish

# To build and publish image
mvn spring-boot:build-image

```
