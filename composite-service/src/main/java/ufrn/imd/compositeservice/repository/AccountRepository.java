package ufrn.imd.compositeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufrn.imd.compositeservice.model.Account;

public interface AccountRepository extends JpaRepository<Account, String> {
}