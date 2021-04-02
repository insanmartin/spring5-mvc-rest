package guru.springframework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guru.springframework.domain.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> 
{
}
