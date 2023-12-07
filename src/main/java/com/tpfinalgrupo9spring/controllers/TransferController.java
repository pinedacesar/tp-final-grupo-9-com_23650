package com.tpfinalgrupo9spring.controllers;


import com.tpfinalgrupo9spring.entities.dtos.TransferDTO;
import com.tpfinalgrupo9spring.exceptions.AccountNotFoundException;

import com.tpfinalgrupo9spring.exceptions.UserNotFoundException;
import com.tpfinalgrupo9spring.services.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService service;

    public TransferController(TransferService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getTransfers(){
        List<TransferDTO> transfers = service.getTransfers();
        return ResponseEntity.status(HttpStatus.OK).body(transfers);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TransferDTO> getTransferById(@PathVariable Long id){
        TransferDTO transfer = service.getTransferById(id);
        return ResponseEntity.status(HttpStatus.OK).body(transfer);
    }


    @PostMapping
    public ResponseEntity<?> performTransfer(@RequestBody TransferDTO dto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.performTransfer(dto));
       } catch (AccountNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateTransfer(@PathVariable Long id, @RequestBody TransferDTO transfer){
        try {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateTransfer(id, transfer));
        } catch ( UserNotFoundException | AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteTransfer(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteTransfer(id));
    }
}
