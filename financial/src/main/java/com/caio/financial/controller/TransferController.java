package com.caio.financial.controller;

import com.caio.financial.dto.TransferCreateDTO;
import com.caio.financial.dto.TransferReponseDTO;
import com.caio.financial.entity.Transfer;
import com.caio.financial.mapper.TransferMapper;
import com.caio.financial.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("transfer")
public class TransferController {

    private final TransferMapper transferMapper;
    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<Void> createTransfer(@RequestBody TransferCreateDTO dto){

        Transfer transfer = transferMapper.toEntity(dto);
        transferService.saveTransfer(transfer,dto.originAccountId(),dto.destinyAccountId());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(transfer.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeTransfer(@PathVariable UUID id){
        return  transferService.findTransferById(id).map(transfer -> {

            transferService.deleteTransfer(transfer);
            return ResponseEntity.noContent().build();

        }).orElseGet(() -> ResponseEntity.noContent().build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<TransferReponseDTO> getTransfer(@PathVariable UUID id){
        return transferService.findTransferById(id).map(transfer -> {

            TransferReponseDTO dto = transferMapper.toDTO(transfer);
            return ResponseEntity.ok(dto);

        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
