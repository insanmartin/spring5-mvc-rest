package guru.springfamework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guru.springfamework.domain.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> 
{
}
