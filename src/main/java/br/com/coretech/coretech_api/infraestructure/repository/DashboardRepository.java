package br.com.coretech.coretech_api.infraestructure.repository;

import br.com.coretech.coretech_api.infraestructure.entity.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, Long> {

}
