package test.ssl.client.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@Configuration
@Slf4j
public class Beans {

    @Bean
    public RestTemplate getRestTemplate() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException, KeyManagementException {
        RestTemplate restTemplate = new RestTemplate();
        final char[] PASSWORD = "client".toCharArray();
        final String KEY_STORE = "client.jks";

        KeyStore keyStore;

        keyStore = KeyStore.getInstance("jks");
        keyStore.load(Beans.class.getResourceAsStream(KEY_STORE), PASSWORD);

        SSLContext sslContext = SSLContexts
                .custom()
                .loadTrustMaterial(keyStore, new TrustSelfSignedStrategy())
                .loadKeyMaterial(keyStore, PASSWORD)
                .build();


        HttpClient client = HttpClients.custom()
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .setSSLContext(sslContext)
                .build();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));

        return restTemplate;
    }

    private void debug(String message, Exception e) {
        log.error(message);
        log.debug("\n", e);
    }
}
