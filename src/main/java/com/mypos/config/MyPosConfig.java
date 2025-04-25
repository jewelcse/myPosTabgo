package com.mypos.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mypos.myposcheckout.ipc.Config;

import com.mypos.myposcheckout.ipc.IPCException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.PrivateKey;
import java.security.PublicKey;

@Component
public class MyPosConfig extends Config {


    @Value("${mypos.api.url}")
    private String apiUrl;

    @Value("${mypos.lang}")
    private String lang;

    @Value("${mypos.key-index}")
    private int keyIndex;

    @Value("${mypos.sid}")
    private String sid;

    @Value("${mypos.wallet-number}")
    private String walletNumber;

    @Value("${mypos.version}")
    private String version;

    @Value("${mypos.keys.publicKey}")
    private String publicKey;

    @Value("${mypos.keys.privateKey}")
    private String privateKey;

    public MyPosConfig() throws IPCException {
        super();
    }

    @Override
    @JsonIgnore
    public PrivateKey getPrivateKey() {
        return super.getPrivateKey();
    }

    @Override
    @JsonIgnore
    public PublicKey getAPIPublicKey() {
        return super.getAPIPublicKey();
    }


    @PostConstruct
    private void overrideDefaults() throws IPCException {
        try {

            this.setIpcUrl(new URL(apiUrl));
            this.setSid(sid);
            this.setWalletNumber(walletNumber);
            this.setKeyIndex(keyIndex);
            this.setLang(lang);
            this.setVersion(version);

            String cleanedPrivateKey = cleanPemKey(privateKey);
            String cleanedPublicKey = cleanPemKey(publicKey);

            this.loadPrivateKeyFromString(cleanedPrivateKey.trim());
            this.loadPublicKeyFromString(cleanedPublicKey.trim());

        } catch (MalformedURLException e) {
            throw new IPCException("Failed to override default URL", e);
        }
    }

    private String cleanPemKey(String pemKey) {
        String base64Key = pemKey
                .replaceAll("-----BEGIN (.*)-----", "")
                .replaceAll("-----END (.*)-----", "")
                .replaceAll("\\s", "");
        while (base64Key.length() % 4 != 0) {
            base64Key += "=";
        }
        return base64Key;
    }
}
