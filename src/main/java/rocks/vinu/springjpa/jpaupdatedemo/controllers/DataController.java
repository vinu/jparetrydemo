package rocks.vinu.springjpa.jpaupdatedemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rocks.vinu.springjpa.jpaupdatedemo.jpa.models.DataRecord;
import rocks.vinu.springjpa.jpaupdatedemo.services.DataRecordService;

@RestController
public class DataController {

    @Autowired
    private DataRecordService dataRecordService;

    @GetMapping("/create")
    public DataRecord create() {
        return dataRecordService.create();
    }

    @GetMapping("/createandwait")
    public DataRecord createAndWait() {
        DataRecord dataRecord = dataRecordService.create();
        return dataRecordService.getRecordWithWait(dataRecord);
    }

    @GetMapping("/verify")
    public DataRecord verify() {
        return dataRecordService.verify();
    }


}
