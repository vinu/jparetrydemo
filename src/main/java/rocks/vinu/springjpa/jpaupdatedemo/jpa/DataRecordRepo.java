package rocks.vinu.springjpa.jpaupdatedemo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import rocks.vinu.springjpa.jpaupdatedemo.jpa.models.DataRecord;

public interface DataRecordRepo extends JpaRepository<DataRecord, Long> {
}
