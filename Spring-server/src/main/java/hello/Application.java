package hello;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
// import org.apache.catalina.Context;
// import org.apache.catalina.connector.Connector;
// import org.apache.tomcat.util.descriptor.web.SecurityCollection;
// import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import io.undertow.UndertowOptions;
import io.undertow.servlet.api.SecurityConstraint;
import io.undertow.servlet.api.SecurityInfo;
import io.undertow.servlet.api.TransportGuaranteeType;
import io.undertow.servlet.api.WebResourceCollection;

@SpringBootApplication
public class Application {

    @Value("${server.port.http}")
    int httpPort;

    @Value("${server.port}")
    int httpsPort;

    @Value("${server.address}")
    String address;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("BEANSNASNSN");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }
    
    @Bean
    public ServletWebServerFactory servletContainer() {
        UndertowServletWebServerFactory undertow = new UndertowServletWebServerFactory();
        undertow.addBuilderCustomizers(builder -> {
            builder.addHttpListener(httpPort, address);
            builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true);
        });
        undertow.addDeploymentInfoCustomizers(deploymentInfo -> {
            deploymentInfo.addSecurityConstraint(new SecurityConstraint()
                    .addWebResourceCollection(new WebResourceCollection()
                            .addUrlPattern("/*"))
                    .setTransportGuaranteeType(TransportGuaranteeType.CONFIDENTIAL)
                    .setEmptyRoleSemantic(SecurityInfo.EmptyRoleSemantic.PERMIT))
                    .setConfidentialPortManager(exchange -> httpsPort);
        });
        return undertow;
    }

    // @Bean
    // public ServletWebServerFactory servletContainer() {
    //     // Enable SSL Trafic
    //     TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
    //         @Override
    //         protected void postProcessContext(Context context) {
    //             SecurityConstraint securityConstraint = new SecurityConstraint();
    //             securityConstraint.setUserConstraint("CONFIDENTIAL");
    //             SecurityCollection collection = new SecurityCollection();
    //             collection.addPattern("/*");
    //             securityConstraint.addCollection(collection);
    //             context.addConstraint(securityConstraint);
    //         }
    //     };

    //     // Add HTTP to HTTPS redirect
    //     tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());

    //     return tomcat;
    // }

    // /*
    // We need to redirect from HTTP to HTTPS. Without SSL, this application used
    // port 8082. With SSL it will use port 8443. So, any request for 8082 needs to be
    // redirected to HTTPS on 8443.
    //  */
    // private Connector httpToHttpsRedirectConnector() {
    //     Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
    //     connector.setScheme("http");
    //     connector.setPort(8082);
    //     connector.setSecure(false);
    //     connector.setRedirectPort(8443);
    //     return connector;
    // }
}
