package com.tpfinalgrupo9spring.mappers;


import com.tpfinalgrupo9spring.entities.Transfers;
import com.tpfinalgrupo9spring.entities.dtos.TransferDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransferMapper {

    public Transfers dtoToTransfer(TransferDTO dto){
        return Transfers.builder()
                .id(dto.getId())
                .amount(dto.getAmount())
                .target(dto.getTarget())
                .origin(dto.getOrigin())
                .owner(dto.getOwner())
                .created_at(dto.getCreated_at())
                .updated_at(dto.getUpdated_at())
                .completed(dto.getCompleted())
                .build();
    }

    public TransferDTO transferToDto(Transfers transfer){
        return TransferDTO.builder()
                .id(transfer.getId())
                .amount(transfer.getAmount())
                .target(transfer.getTarget())
                .origin(transfer.getOrigin())
                .ownerId(transfer.getOwner().getId())
                .created_at(transfer.getCreated_at())
                .updated_at(transfer.getUpdated_at())
                .completed(transfer.getCompleted())
                .build();
    }
}
