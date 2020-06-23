package com.example.transvehobe.entity.transfer;

import com.example.transvehobe.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    List<Transfer> getTransfersByDriver(User driver);
}
