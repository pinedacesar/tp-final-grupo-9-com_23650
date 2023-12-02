package com.tpfinalgrupo9spring.services;

import com.tpfinalgrupo9spring.entities.dtos.AccountDTO;
import com.tpfinalgrupo9spring.entities.enums.AccountType;
import com.tpfinalgrupo9spring.entities.Accounts;
import com.tpfinalgrupo9spring.entities.UserEntity;
import com.tpfinalgrupo9spring.exceptions.AccountNotFoundException;
import com.tpfinalgrupo9spring.exceptions.UserNotFoundException;
import com.tpfinalgrupo9spring.mappers.AccountMapper;
import com.tpfinalgrupo9spring.repositories.AccountRepository;
import com.tpfinalgrupo9spring.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository repository;
    public final UserRepository userRepository;

    public AccountService(AccountRepository repository, UserRepository userRepository)
    {
        this.repository = repository;
        this.userRepository=userRepository;
    }
    public List<AccountDTO> getAccounts() {
        List<Accounts> accounts = repository.findAll();

        return accounts.stream()
                .filter(x->x.getIsActive())
                .map(AccountMapper::accountToDto)
                .collect(Collectors.toList());
    }

    public AccountDTO createAccount(AccountDTO dto) throws UserNotFoundException {
        UserEntity owner =userRepository.findById(dto.getOwnerId()).get();
        if (owner==null)
            throw new UserNotFoundException("Usuario no encontrado");

        if (dto.getTipo()==null)
            dto.setTipo(AccountType.ARS_SAVINGS_BANK);
        if (dto.getAlias()==null) {
            Integer random=LocalTime.now().getSecond();  // TODO implementar generacion de alias de Matias
            dto.setAlias(owner.getFirstname().concat(".")
                    .concat(owner.getLastname())
                    .concat(random.toString()));
        }
        if (dto.getCbu()==null){
            create_cbu(owner,AccountMapper.dtoToAccount(dto),repository.countByOwner(owner));
        }
        dto.setOwner(owner);
        dto.setAmount(BigDecimal.ZERO);
        dto.setIsActive(true);
        Accounts newAccount = repository.save(AccountMapper.dtoToAccount(dto));
        return AccountMapper.accountToDto(newAccount);
    }

    public AccountDTO getAccountById(Long id) {
        Accounts entity = repository.findById(id).get();
        return AccountMapper.accountToDto(entity);
    }

    public String deleteAccount(Long id) throws AccountNotFoundException {
        if (repository.existsById(id)){
            Accounts entity = repository.findById(id).get();
            entity.setIsActive(false);
            repository.save(entity);
            return "La cuenta con id: " + id + " ha sido eliminada";
        } else {
            throw new AccountNotFoundException("La cuenta a eliminar no existe");
        }

    }

    public AccountDTO updateAccount(Long id, AccountDTO dto) throws AccountNotFoundException {
        if (repository.existsById(id)) {
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

            //No se puede cambiar el titular de la cuenta!

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
        String entity = completarConCeros("1", 4);
        String dni = completarConCeros(usuario.getDni(), 10);
        String branch = completarConCeros(String.valueOf(cuenta.getSucursal()), 3);
        String type = completarConCeros(String.valueOf(cuenta.getTipo()), 2);
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