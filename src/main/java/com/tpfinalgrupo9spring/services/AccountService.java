package com.tpfinalgrupo9spring.services;

import com.tpfinalgrupo9spring.entities.dtos.AccountDTO;
import com.tpfinalgrupo9spring.entities.enums.AccountType;
import com.tpfinalgrupo9spring.entities.Accounts;
import com.tpfinalgrupo9spring.entities.UserEntity;
import com.tpfinalgrupo9spring.exceptions.*;
import com.tpfinalgrupo9spring.mappers.AccountMapper;
import com.tpfinalgrupo9spring.mappers.UserMapper;
import com.tpfinalgrupo9spring.repositories.AccountRepository;
import com.tpfinalgrupo9spring.repositories.UserRepository;
import com.tpfinalgrupo9spring.utils.AliasGen;
import com.tpfinalgrupo9spring.utils.CbuGen;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository repository;
    private final UserRepository userRepository;
    private final AliasGen aliasGen;
    private final CbuGen cebuGen;
    public AccountService(AccountRepository repository, UserRepository userRepository, AliasGen aliasGen, CbuGen cbuGen)
    {
        this.repository = repository;
        this.userRepository=userRepository;
        this.cebuGen = cbuGen;
        this.aliasGen = aliasGen;
    }
    public List<AccountDTO> getAccounts() {
        List<Accounts> accounts = repository.findAll();

        return accounts.stream()
                .filter(x->x.getIsActive())
                .map(AccountMapper::accountToDto)
                .collect(Collectors.toList());
    }

    public AccountDTO createAccount(AccountDTO dto) throws UserNotFoundException, SaveAccountException, CbuDuplicatedException, AliasDuplicatedException {
        UserEntity owner;
        try {
            owner = userRepository.findById(dto.getOwnerId()).get();
        } catch (Exception e) {
            throw new UserNotFoundException("Usuario no encontrado");
        }
        if (dto.getTipo()==null)
            dto.setTipo(AccountType.ARS_SAVINGS_BANK);
        if (dto.getAlias()==null) {
//            Integer random=LocalTime.now().getSecond();  // TODO implementar generacion de alias de Matias
//            dto.setAlias(owner.getFirstname().concat(".")
//                    .concat(owner.getLastname())
//                    .concat(random.toString()));
            try {
                dto.setAlias(aliasGen.generateUniqueAlias());
            } catch (AliasGenerationException e) {
                throw new RuntimeException(e);
            }
        }
        dto.setOwner(UserMapper.userToDto((owner)));
        dto.setOwnerId(UserMapper.userToDto((owner)).getId());
        if (dto.getCbu()==null){
//            dto.setCbu(create_cbu(owner,AccountMapper.dtoToAccount(dto),repository.countByOwner(owner)));
            try {
                dto.setCbu(cebuGen.create_cbu(owner, AccountMapper.dtoToAccount(dto), repository.countByOwner(owner)));
            } catch (CbuGenerationException e) {
                throw new RuntimeException(e);
            }
        }

        if (dto.getAmount()==null)
            dto.setAmount(BigDecimal.ZERO);
        dto.setIsActive(true);

        if (repository.existsByCbu(dto.getCbu()))
            throw new CbuDuplicatedException("CBU duplicado");

        if (repository.existsByAlias(dto.getAlias()))
            throw new AliasDuplicatedException("Alias duplicado");

        dto.setCreated_at(LocalDateTime.now());
        dto.setUpdated_at(LocalDateTime.now());
        Accounts newAccount = AccountMapper.dtoToAccount(dto);
        newAccount.setOwner(owner);
        try {
            newAccount = repository.saveAndFlush(newAccount);

            UserEntity saveUser=userRepository.saveAndFlush(owner);
        }catch (DataAccessException e){
            throw new SaveAccountException(e.getMessage());
        }


        return AccountMapper.accountToDto(newAccount);
    }

    public AccountDTO getAccountById(Long id) {
        Accounts entity = repository.findById(id).get();
        return AccountMapper.accountToDto(entity);
    }

    public String deleteAccount(Long id) throws AccountNotFoundException {
        if (repository.existsById(id) && repository.findById(id).get().getIsActive()){
            Accounts entity = repository.findById(id).get();
            entity.setIsActive(false);
            entity.setUpdated_at(LocalDateTime.now());
            repository.save(entity);
            return "La cuenta con id: " + id + " ha sido eliminada";
        } else {
            throw new AccountNotFoundException("La cuenta a eliminar no existe");
        }

    }

    public AccountDTO updateAccount(Long id, AccountDTO dto) throws AccountNotFoundException {
        if (repository.existsById(id)&& repository.findById(id).get().getIsActive()) {
            Accounts accountToModify = repository.findById(id).get();

            if (!accountToModify.getIsActive())
                throw new AccountNotFoundException("Cuenta no encontrada");

            if (dto.getAlias() != null) {
                accountToModify.setAlias(dto.getAlias());
            }

            if (dto.getTipo() != null) {
                accountToModify.setTipo(dto.getTipo());
            }

            if (dto.getCbu() != null) {
                accountToModify.setCbu(dto.getCbu());
            }

            if (dto.getAmount() != null) {
                accountToModify.setAmount(dto.getAmount());
            }
            if (dto.getSucursal() != null) {
                accountToModify.setSucursal(dto.getSucursal());
            }

            accountToModify.setUpdated_at(LocalDateTime.now());

            //No se puede cambiar el titular de la cuenta!!

            Accounts accountModified = repository.save(accountToModify);

            return AccountMapper.accountToDto(accountModified);
        }
        throw new AccountNotFoundException("Cuenta no encontrada");

    }

    // ------------  Metodos a revisar  --------------
    private String generarCBU(String sucursal, String tipo, String cuil, String numCuenta){
        String cbu= "";

        cbu=cbu.concat(sucursal).concat("0000").concat(cuil).concat(numCuenta);

        return cbu;
    }

    //Codigo de Matias adapatado a mi rama
    public String create_cbu(UserEntity usuario, Accounts cuenta, long numAccounts){

        String dniVal =usuario.getDni();
        if (dniVal.length()>10)
            dniVal=dniVal.substring(0,9);
        String branchVal=String.valueOf(cuenta.getSucursal());
        if (branchVal.length()>4)
            branchVal=branchVal.substring(0,3);

        String entity = completarConCeros("1", 4);
        String dni = completarConCeros(dniVal, 10);
        String branch = completarConCeros(branchVal, 4);
        String type = completarConCeros(String.valueOf(cuenta.getTipo().ordinal()), 3);
        // Cantidad de cuentas getAccountsByOwner +1
        String cbu = entity + branch + dni + type +numAccounts;
        cuenta.setCbu(Long.valueOf(cbu).toString()); // Asignar el CBU generado a la instancia de Accounts proporcionada

        return cbu;
    }

    private static String completarConCeros(String input, int longitudObjetivo) {
        StringBuilder sb = new StringBuilder(input);
        while (sb.length() < longitudObjetivo) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }
}


//  ORIGINAL
//    public final AccountRepository accountRepository;
//
//    public AccountService(AccountRepository accountRepository) {
//        this.accountRepository = accountRepository;
//    }
//    // public final UserRepository userRepository;
//
//    public String create(Long owner, AccountType tipo, String nombre){
//        Accounts account =new Accounts();
//
//        UserEntity user =new UserEntity();
//        // busqueda usuario x id en DB
//        account.setName(nombre);
////        account.setOwner(user.getId());
//        account.setTipo(tipo);
//        account.setSucursal("111");
//        //cambiar dni x cuil
//        String numCuenta="001"; //contar en DB cantidad de owners con id del usr
//        account.setCbu(generarCBU(account.getSucursal(), account.getName(),user.getDni(),numCuenta));
//
//        //accountRepository.save(account);
//
//        return "Cuenta creada satisfactoriamente";
//
//    }