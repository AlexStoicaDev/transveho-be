package com.example.transvehobe.rest;

import com.example.transvehobe.common.dto.CreateTransferDataDto;
import com.example.transvehobe.common.dto.CreateTransferStepperDataDto;
import com.example.transvehobe.entity.transfer.Transfer;
import com.example.transvehobe.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transveho/transfers")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TransferController {

    private final TransferService transferService;

    @GetMapping("stepper-data")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public CreateTransferStepperDataDto getTransferStepperData(
        @RequestParam(value = "routeId") long routeId,
        @RequestParam(value = "selectedPassengersIds") List<Long> selectedPassengers
    ) {
        return transferService.getCreateTransferStepperData(selectedPassengers, routeId);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public Transfer createTransfer(@RequestBody CreateTransferDataDto createTransferDataDto) {
        return transferService.createTransfer(createTransferDataDto);
    }
}
