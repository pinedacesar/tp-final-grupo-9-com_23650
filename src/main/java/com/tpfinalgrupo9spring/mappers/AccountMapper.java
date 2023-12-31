package com.tpfinalgrupo9spring.mappers;


import com.tpfinalgrupo9spring.entities.Accounts;
import com.tpfinalgrupo9spring.entities.dtos.AccountDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountMapper {


    public AccountDTO accountToDto(Accounts account){
        return AccountDTO.builder()
                .id(account.getId())
                .tipo(account.getTipo())
                .cbu(account.getCbu())
                .alias(account.getAlias())
                .amount(account.getAmount())
                .sucursal(account.getSucursal())
                .amount(account.getAmount())
                .ownerId(account.getOwner().getId())
                //.owner(UserMapper.userToDto(account.getOwner()))
                .owner(UserMapper.userToDtoShort(account.getOwner()))
                .isActive(account.getIsActive())
                .created_at(account.getCreated_at())
                .updated_at(account.getUpdated_at())
                .build();



    }

    public Accounts dtoToAccount(AccountDTO dto){
        return Accounts.builder()
                .id(dto.getId())
                .tipo(dto.getTipo())
                .cbu(dto.getCbu())
                .alias(dto.getAlias())
                .amount(dto.getAmount())
                .sucursal(dto.getSucursal())
                .amount(dto.getAmount())
                .owner(UserMapper.dtoToUser(dto.getOwner()))
                .isActive(dto.getIsActive())
                .created_at(dto.getCreated_at())
                .updated_at(dto.getUpdated_at())
                .build();

    }
}
