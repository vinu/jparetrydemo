package rocks.vinu.springjpa.jpaupdatedemo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import rocks.vinu.springjpa.jpaupdatedemo.jpa.models.DataRecord;
import rocks.vinu.springjpa.jpaupdatedemo.services.DataRecordService;

import java.util.concurrent.ForkJoinPool;

@RestController
@Slf4j
public class DataController {

    @Autowired
    private DataRecordService dataRecordService;

    @GetMapping("/create")
    public DataRecord create() {
        return dataRecordService.create();
    }

    @GetMapping("/createandwait")
    public DeferredResult<DataRecord> createAndWait() {
        DeferredResult<DataRecord> dataRecordDeferredResult = new DeferredResult<>();
        DataRecord dataRecord = dataRecordService.create();
        ForkJoinPool.commonPool().submit(() -> {
            try {
                DataRecord dataRecordResult = dataRecordService.getRecordWithWait(dataRecord);
                dataRecordDeferredResult.setResult(dataRecordResult);

            } catch (DataPendingException dpe) {
                log.warn("Didn't get response", dpe);
                dataRecordDeferredResult.setErrorResult(dpe);
            }

        });
        return dataRecordDeferredResult;
    }

    @GetMapping("/verify")
    public DataRecord verify() {
        return dataRecordService.verify();
    }


}
