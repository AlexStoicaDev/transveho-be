package com.example.transvehobe.rest;

import com.example.transvehobe.common.dto.CreateTransferDataDto;
import com.example.transvehobe.common.dto.CreateTransferStepperDataDto;
import com.example.transvehobe.common.projection.CurrentTransfer;
import com.example.transvehobe.common.projection.CustomProjectionFactory;
import com.example.transvehobe.common.projection.TransferProjection;
import com.example.transvehobe.entity.transfer.Transfer;
import com.example.transvehobe.service.EmailService;
import com.example.transvehobe.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    private final EmailService emailService;
    private final TransferService transferService;
    private final CustomProjectionFactory factory;

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

    @GetMapping()
    // @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public List<TransferProjection> getAllTransfers() {
        return factory.createProjection(TransferProjection.class, transferService.getAllTransfers());
    }

    @GetMapping("/{driverId}")
    // @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
    public List<CurrentTransfer> getAllTransfersForDriver(@PathVariable(value = "driverId") Long driverId) {
        return factory.createProjection(CurrentTransfer.class, transferService.getAllTransfersForDriver(driverId));
    }

    @GetMapping("/current/{driverId}")
    public ResponseEntity<?> getCurrentTransferForDriver(@PathVariable(value = "driverId") Long driverId) {
        final Transfer currentTransferForDriver = transferService.getCurrentTransferForDriver(driverId);
        if (currentTransferForDriver != null) {

            return ResponseEntity.ok().body(factory.createProjection(CurrentTransfer.class, currentTransferForDriver));
        }
        return  ResponseEntity.ok().body("{}");
    }

    @PutMapping("/current/start/{passengerId}/{carId}/{driverId}")
    public ResponseEntity<?> startTransfer(@PathVariable(value = "passengerId") Long passengerId,
                                           @PathVariable(value = "carId") Long carId,
                                           @PathVariable(value = "driverId") Long driverId) {

        transferService.startTransfer(passengerId, carId, driverId);
        return ResponseEntity.ok().body("{}");
    }

    @PutMapping("/current/finish/{passengerId}/{carId}/{driverId}")
    public ResponseEntity<?> finishTransfer(@PathVariable(value = "passengerId") Long passengerId,
                                            @PathVariable(value = "carId") Long carId,
                                            @PathVariable(value = "driverId") Long driverId) {

        transferService.finishTransfer(passengerId, carId, driverId);
        return ResponseEntity.ok().body("{}");
    }

    @GetMapping("email")
    public void sendEmail() {
        emailService.sendNewAccountEmail("alexandrupetrutstoica@gmail.com", "Stoica", "Alex", "1234567");
    }
}
