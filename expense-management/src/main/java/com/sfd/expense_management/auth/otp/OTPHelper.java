package com.sfd.expense_management.auth.otp;

import com.amdelamar.jotp.OTP;
import com.amdelamar.jotp.type.Type;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class OTPHelper {
    @Value("${security.secret.key}")
    private String signingKey;
    public String otpGenerator() throws NoSuchAlgorithmException, InvalidKeyException {
        String secret = OTP.randomBase32(32);
        return OTP.create(secret, "32", 8, Type.TOTP);
    }
}
