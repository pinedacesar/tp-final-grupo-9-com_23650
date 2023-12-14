package com.tpfinalgrupo9spring.services;


import com.tpfinalgrupo9spring.entities.Accounts;
import com.tpfinalgrupo9spring.entities.Transfers;
import com.tpfinalgrupo9spring.entities.UserEntity;
import com.tpfinalgrupo9spring.entities.dtos.TransferDTO;
import com.tpfinalgrupo9spring.exceptions.AccountNotFoundException;
import com.tpfinalgrupo9spring.exceptions.InsufficientFoundsException;
import com.tpfinalgrupo9spring.exceptions.TransferNotFoundException;
import com.tpfinalgrupo9spring.exceptions.UserNotFoundException;
import com.tpfinalgrupo9spring.mappers.TransferMapper;
import com.tpfinalgrupo9spring.repositories.AccountRepository;
import com.tpfinalgrupo9spring.repositories.TransferRepository;
import com.tpfinalgrupo9spring.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferService {

    private final TransferRepository repository;

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public TransferService(TransferRepository repository, AccountRepository accountRepository, UserRepository userRepository){
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.userRepository=userRepository;
    }

    public List<TransferDTO> getTransfers(){
        List<Transfers> transfers = repository.findAll();
        return transfers.stream()
                .map(TransferMapper::transferToDto)
                .collect(Collectors.toList());
    }

    public TransferDTO getTransferById(Long id){
        Transfers transfer = repository.findById(id).orElseThrow(() ->
                new TransferNotFoundException("Transfer not found with id: " + id));
        return TransferMapper.transferToDto(transfer);
    }

    public TransferDTO updateTransfer(Long id, TransferDTO transferDto) throws UserNotFoundException, AccountNotFoundException {
        Transfers updatedTransfer = repository.findById(id).orElseThrow(() -> new TransferNotFoundException("Transfer not found with id: " + id));

        if (transferDto.getOwnerId()!=null) {
            UserEntity newOwner= userRepository.findById(transferDto.getOwnerId()).orElseThrow(() ->
                    new UserNotFoundException("New owner not found with id: " + transferDto.getOwnerId()));
            updatedTransfer.setOwner(newOwner);
        }
        if (transferDto.getOrigin()!=null) {
            Accounts newOriginAccount = accountRepository.findById(transferDto.getOrigin())
                    .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + transferDto.getOrigin()));
            if (newOriginAccount.getOwner().getId()!= updatedTransfer.getOwner().getId())
                throw new AccountNotFoundException("Account owner " + newOriginAccount.getOwner().getId()
                        +"Transfer owner "+updatedTransfer.getOwner().getId()+"missmatch");
            updatedTransfer.setOrigin(transferDto.getOrigin());
        }

        if (transferDto.getTarget()!=null)
            updatedTransfer.setTarget(transferDto.getTarget());

        if (transferDto.getAmount()!=null)
            updatedTransfer.setAmount(transferDto.getAmount());

        if (transferDto.getCompleted()!=null)
            updatedTransfer.setCompleted(transferDto.getCompleted());

        updatedTransfer.setUpdated_at(LocalDateTime.now());

            return TransferMapper.transferToDto(repository.save(updatedTransfer));
    }

    public String deleteTransfer(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return "Se ha eliminado la transferencia";
        } else {
            return "No se ha eliminado la transferencia";
        }
    }

    @Transactional
    public TransferDTO performTransfer(TransferDTO dto) throws AccountNotFoundException, UserNotFoundException {
        // Comprobar si las cuentas de origen y destino existen
        Accounts originAccount = accountRepository.findById(dto.getOrigin())
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + dto.getOrigin()));
        Accounts destinationAccount = accountRepository.findById(dto.getTarget())
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + dto.getTarget()));

        // Comprobar si la cuenta de origen tiene fondos suficientes
        if (originAccount.getAmount().compareTo(dto.getAmount()) < 0) {
            throw new InsufficientFoundsException("Insufficient funds in the account with id: " + dto.getOrigin());
        }

        if (!originAccount.getOwner().getId().equals(userRepository.findById(dto.getOwnerId()).get().getId()))
            throw new UserNotFoundException("Missmatch user:_ "+ dto.getOwnerId() +" account owner:_"+ originAccount.getOwner().getId());

        // Realizar la transferencia
        originAccount.setAmount(originAccount.getAmount().subtract(dto.getAmount()));
        destinationAccount.setAmount(destinationAccount.getAmount().add(dto.getAmount()));

        // Guardar las cuentas actualizadas
        accountRepository.save(originAccount);
        accountRepository.save(destinationAccount);

        dto.setCompleted(true);
        // Crear la transferencia y guardarla en la base de datos
        Transfers transfer = new Transfers();
        // Creamos un objeto del tipo Date para obtener la fecha actual
        Date date = new Date();
        // Seteamos el objeto fecha actual en el transferDto
        transfer.setDate(date);
        transfer.setOrigin(originAccount.getId());
        transfer.setTarget(destinationAccount.getId());
        transfer.setAmount(dto.getAmount());
        transfer.setCreated_at(LocalDateTime.now());
        transfer.setUpdated_at(LocalDateTime.now());
        transfer.setCompleted(dto.getCompleted());
        transfer.setOwner(userRepository.findById(dto.getOwnerId()).get());
        transfer = repository.save(transfer);

        // Devolver el DTO de la transferencia realizada
        return TransferMapper.transferToDto(transfer);
    }


}
