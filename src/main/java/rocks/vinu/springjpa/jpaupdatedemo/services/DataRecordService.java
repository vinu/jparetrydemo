package rocks.vinu.springjpa.jpaupdatedemo.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import rocks.vinu.springjpa.jpaupdatedemo.controllers.DataPendingException;
import rocks.vinu.springjpa.jpaupdatedemo.jpa.DataRecordRepo;
import rocks.vinu.springjpa.jpaupdatedemo.jpa.models.DataRecord;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DataRecordService {

    @Autowired
    private DataRecordRepo dataRecordRepo;


    public DataRecord create() {
        DataRecord dataRecord = new DataRecord();
        dataRecord.setCol1(RandomStringUtils.randomAlphabetic(10));
        dataRecord = dataRecordRepo.save(dataRecord);
        return dataRecord;
    }

    @Retryable(
            value = {DataPendingException.class},
            maxAttempts = 10,
            backoff = @Backoff(delay = 2000))
    public DataRecord getRecordWithWait(DataRecord dataRecord) {
        dataRecord = getRecord(dataRecord.getId());

        log.info("Status for id {} is {}", dataRecord.getId(), dataRecord);

        if (!dataRecord.isVerified()) {
            throw new DataPendingException("Data not verified for " + dataRecord.getId());
        }

        return dataRecord;

    }

    public DataRecord getRecord(long id) {
        DataRecord dataRecord = dataRecordRepo.findById(id).get();
        return dataRecord;
    }

    public DataRecord verify() {
        // BAD
        List<DataRecord> dataRecords = dataRecordRepo.findAll();

        List<DataRecord> updatedRecords = new ArrayList<>();

        for (DataRecord dataRecord : dataRecords) {
            if (!dataRecord.isVerified()) {
                dataRecord.setVerified(true);
                dataRecord.setCol2(RandomStringUtils.randomAlphabetic(10));
                dataRecord = dataRecordRepo.save(dataRecord);
                updatedRecords.add(dataRecord);
            }
        }
        return dataRecords.get(0);
    }

}
