package com.sfd.expense_management.auth.otp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<OTP, Long> {
    OTP findByUsernameAndPurpose(String userName, OtpPurpose otpPurpose);
}
