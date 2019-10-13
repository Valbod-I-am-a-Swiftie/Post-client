package hello;

import io.undertow.UndertowOptions;
import io.undertow.servlet.api.SecurityConstraint;
import io.undertow.servlet.api.SecurityInfo;
import io.undertow.servlet.api.TransportGuaranteeType;
import io.undertow.servlet.api.WebResourceCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostClientSecurityConfig {
    @Bean
    public ServletWebServerFactory servletContainer() {
        UndertowServletWebServerFactory undertow = new UndertowServletWebServerFactory();
        undertow.addBuilderCustomizers(builder -> {
            builder.addHttpListener(httpPort, "0.0.0.0");
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

    @Value("${server.port.http}")
    int httpPort;

    @Value("${server.port}")
    int httpsPort;
}