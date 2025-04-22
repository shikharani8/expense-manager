package com.sfd.expense_management.auth.otp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpRepository otpRepository;

    public OTP create(OTP otp){
        return otpRepository.save(otp);
    }

    public OTP getOtpByUserNameAndByPurpose(String userName, OtpPurpose otpPurpose){
        return otpRepository.findByUsernameAndPurpose(userName, otpPurpose);
    }

    public void deleteByOtpId(Long id){
        otpRepository.deleteById(id);
    }
}
